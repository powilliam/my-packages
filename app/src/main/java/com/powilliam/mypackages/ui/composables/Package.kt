package com.powilliam.mypackages.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.powilliam.mypackages.data.entity.Package

@Composable
fun PackageOnMapCard(modifier: Modifier = Modifier, entity: Package) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column {
            Package(
                tracker = {
                    Text(text = entity.tracker)
                },
                name = {
                    Text(text = entity.name)
                }
            )
            entity.events.firstOrNull()?.let { latestEvent ->
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12F))
                PackageDetails {
                    PackageDetail(
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Place,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        },
                        label = { Text(text = "Último evento") },
                        text = {
                            val text =
                                "${latestEvent.description} - ${latestEvent.location.address.city}, ${latestEvent.location.address.province}"
                            Text(text = text)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Package(
    modifier: Modifier = Modifier,
    tracker: @Composable () -> Unit,
    name: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun PackageDetail(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    label: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier.size(16.dp)) {
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