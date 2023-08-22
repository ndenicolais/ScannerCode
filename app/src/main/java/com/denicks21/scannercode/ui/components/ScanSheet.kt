package com.denicks21.scannercode.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.denicks21.scannercode.ui.theme.DarkGrey
import com.denicks21.scannercode.ui.theme.LightYellow
import com.denicks21.scannercode.model.Scan
import com.denicks21.scannercode.model.ScanType
import com.denicks21.scannercode.R

@Composable
fun ScanSheet(
    scan: Scan,
    onShareClicked: () -> Unit,
    onCopyClicked: () -> Unit,
    onWebClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(12.dp)
                .width(50.dp)
                .padding(top = 5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkGrey,
                contentColor = LightYellow
            )
        ) {}
        Text(
            text = stringResource(id = scan.scanFormatId),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        if (scan.displayValue.isNotBlank()) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = scan.displayValue,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            FloatingActionButton(
                onClick = onShareClicked,
                backgroundColor = DarkGrey,
                contentColor = LightYellow,
                modifier = Modifier.requiredSize(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }
            FloatingActionButton(
                onClick = onCopyClicked,
                backgroundColor = DarkGrey,
                contentColor = LightYellow,
                modifier = Modifier.requiredSize(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null
                )
            }
            if (scan.scanType == ScanType.Url) {
                ExtendedFloatingActionButton(
                    backgroundColor = DarkGrey,
                    contentColor = LightYellow,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(id = R.string.scan_sheet_web_action),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    onClick = onWebClicked
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
    }
}