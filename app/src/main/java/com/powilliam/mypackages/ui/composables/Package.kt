package com.powilliam.mypackages.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Package(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tracker: @Composable () -> Unit,
    name: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        ProvideTextStyle(MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)) {
            tracker()
        }
        ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
            name()
        }
    }
}

@Composable
fun PackageDetails(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        content()
    }
}

@Composable
fun PackageDetail(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    icon: @Composable () -> Unit = {},
    label: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Surface {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier.size(iconSize)) {
                Box(modifier.align(Alignment.Center)) {
                    icon()
                }
            }
            Column(modifier = modifier.padding(start = 16.dp)) {
                ProvideTextStyle(MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)) {
                    label()
                }
                ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                    text()
                }
            }
        }
    }
}