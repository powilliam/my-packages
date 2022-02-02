package com.powilliam.mypackages.ui.composables

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationCard(
    title: @Composable () -> Unit = {},
    body: @Composable () -> Unit
) {
    ListItem(
        icon = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null)
        },
        overlineText = {
            ProvideTextStyle(MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)) {
                title()
            }
        }
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)) {
            body()
        }
    }
}