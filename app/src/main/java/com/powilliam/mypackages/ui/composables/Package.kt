package com.powilliam.mypackages.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.powilliam.mypackages.data.entity.Package

@Composable
fun PackageOnMapCard(
    modifier: Modifier = Modifier,
    entity: Package,
    onClick: (Package) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(modifier.clickable { onClick(entity) }) {
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
fun PackageCard() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column {
            Package(
                tracker = { Text(text = "123124124124") }
            ) {
                Text(text = "Meu pacote")
            }
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
            PackageDetails {
                PackageDetail(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Previsão de entrega") }
                ) {
                    Text(text = "Daqui 12 dias")
                }
                PackageDetail(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Map,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Último evento") }
                ) {
                    Text(text = "Em trânsito para Agência dos Correios - Curitiba")
                }
                PackageDetail(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Última localização") }
                ) {
                    Text(text = "Unidade de distribuição - Curitiba")
                }
                PackageDetail(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Update,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Última atualização") }
                ) {
                    Text(text = "Há 20 minutos")
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

@Composable
fun EventsCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            PackageDetail(
                iconSize = 40.dp,
                icon = {
                    Avatar(color = MaterialTheme.colorScheme.tertiary) {
                        Text(
                            modifier = modifier.align(Alignment.Center),
                            text = "01",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                label = { Text(text = "OBJETO POSTADO") }
            ) {
                Text(text = "Agência dos Correios - Curitiba")
            }
            PackageDetail(
                iconSize = 40.dp,
                icon = {
                    Avatar(color = MaterialTheme.colorScheme.tertiary) {
                        Text(
                            modifier = modifier.align(Alignment.Center),
                            text = "02",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                label = { Text(text = "OBJETO POSTADO") }
            ) {
                Text(text = "Agência dos Correios - Curitiba")
            }
        }
    }
}