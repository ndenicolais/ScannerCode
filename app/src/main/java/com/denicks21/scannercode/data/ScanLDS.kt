package com.denicks21.scannercode.data

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.denicks21.scannercode.R
import com.denicks21.scannercode.model.Scan
import com.denicks21.scannercode.model.ScanType
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ScanLDS @Inject constructor(
    @ApplicationContext private val context: Context
): ScanLocalDataSource, ImageAnalysis.Analyzer {

    // Camera X
    private val previewView = PreviewView(context)
    private val preview by lazy { Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) } }
    private lateinit var cameraLifecycle: LifecycleOwner
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraControl: CameraControl
    private val focusAction by lazy {
        val factory = previewView.meteringPointFactory
        val point = factory.createPoint(
            previewView.width.div(2f),
            previewView.height.div(2f)
        )
        FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
            .setAutoCancelDuration(3, TimeUnit.SECONDS)
            .build()
    }
    private var focusDone = true

    // ML Scan QR/Barcode
    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        )
        .build()
    private val scannerClient = BarcodeScanning.getClient(scannerOptions)
    private val imageAnalysis by lazy {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }
    private val scanStateFlow = MutableStateFlow<Scan?>(null)


    override fun analyze(proxy: ImageProxy) {
        proxy.image?.let {
            val image = InputImage.fromMediaImage(
                it,
                proxy.imageInfo.rotationDegrees,
                proxy.imageInfo.sensorToBufferTransformMatrix
            )
            scannerClient.process(image)
                .addOnSuccessListener { barCodes ->
                    if (barCodes.isNotEmpty()) {
                        scanStateFlow.value = barCodes.filterNotNull()
                            .distinct()
                            .first()
                            .toScan()
                    } else {
                        scanStateFlow.value = Scan(
                            displayValue = "",
                            scanFormatId = R.string.scan_format_unknown,
                            ScanType.Text
                        )
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener { proxy.close() }
        }
    }

    override fun getLatestScan(): Flow<Scan> = scanStateFlow
        .asStateFlow()
        .filterNotNull()
        .distinctUntilChanged()
        .catch { e ->
            e.printStackTrace()
        }
        .flowOn(Dispatchers.IO)

    override suspend fun getCameraPreview(lifecycleOwner: LifecycleOwner): PreviewView {
        cameraLifecycle = lifecycleOwner
        cameraProvider = context.getCameraProvider()

        try {
            imageAnalysis.clearAnalyzer()
            imageAnalysis.setAnalyzer(context.executor, this)
            cameraProvider.unbindAll()
            cameraControl = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalysis
            ).cameraControl
        } catch (e: Exception) {
            e.printStackTrace()
        }

        previewView.setOnClickListener {
            if (!focusDone) return@setOnClickListener
            focusDone = false
            try {
                cameraControl.startFocusAndMetering(focusAction)
                    .addListener(
                        {
                            focusDone = true
                        },
                        context.executor
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                focusDone = true
            }
        }

        return previewView
    }

    override suspend fun pauseScan() {
        try {
            if (cameraProvider.isBound(preview)) {
                imageAnalysis.clearAnalyzer()
                cameraProvider.unbindAll()
                scanStateFlow.value = Scan(
                    displayValue = "",
                    scanFormatId = R.string.scan_format_unknown,
                    ScanType.Text
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun resumeScan() {
        try {
            if (!cameraProvider.isBound(preview)) {
                imageAnalysis.setAnalyzer(context.executor, this)
                cameraControl = cameraProvider.bindToLifecycle(
                    cameraLifecycle,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalysis
                ).cameraControl
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)