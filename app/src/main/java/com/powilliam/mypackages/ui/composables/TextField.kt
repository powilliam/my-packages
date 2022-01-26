package com.powilliam.mypackages.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppBarTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.clearFocus()
                    softwareKeyboardController?.hide()
                }
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navigationIcon?.let { composable ->
                        Box(modifier = modifier.padding(end = 16.dp)) {
                            composable()
                        }
                    }
                    Box(modifier.padding(end = 16.dp)) {
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(
                                modifier = modifier.align(Alignment.CenterStart),
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    label: String,
    onValueChange: (String) -> Unit,
    icon: (@Composable () -> Unit)? = null
) {
    val focus = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardActions = KeyboardActions(
            onAny = {
                focus.clearFocus()
                softwareKeyboardController?.hide()
            }
        ),
        decorationBox = { innerTextField ->
            Surface(modifier.fillMaxWidth()) {
                Column {
                    Row(
                        modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier.size(24.dp)) {
                            icon?.let { composable ->
                                Box(modifier.align(Alignment.Center)) {
                                    composable()
                                }
                            }
                        }
                        Column(
                            modifier.padding(end = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                            )
                            Box {
                                innerTextField()
                                if (value.isEmpty()) {
                                    Text(
                                        modifier = modifier.align(Alignment.CenterStart),
                                        text = placeholder,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                }
            }
        }
    )
}