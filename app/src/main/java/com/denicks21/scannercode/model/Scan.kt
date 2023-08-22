package com.denicks21.scannercode.model

import androidx.annotation.StringRes

data class Scan(
    val displayValue: String,
    @StringRes val scanFormatId: Int,
    val scanType: ScanType
)

enum class ScanType {
    Text,
    Url
}
