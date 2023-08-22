package com.denicks21.scannercode.scanner

import androidx.camera.view.PreviewView
import com.denicks21.scannercode.model.Scan

data class ScannerUiState(
    val previewView: PreviewView? = null,
    val scan: Scan? = null,
    val showBottomSheet: Boolean = false,
    val showCameraRequiredDialog: Boolean = false
)
