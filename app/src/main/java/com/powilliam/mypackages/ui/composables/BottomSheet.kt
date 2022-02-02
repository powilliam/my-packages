package com.powilliam.mypackages.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    state: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit = {}
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetElevation = 2.dp,
        sheetContent = {
            InnerSheetContent {
                sheetContent()
            }
        }
    ) {
        content()
    }
}

@Composable
private fun InnerSheetContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier
            .navigationBarsPadding()
            .padding(vertical = 8.dp)
    ) {
        content()
    }
}