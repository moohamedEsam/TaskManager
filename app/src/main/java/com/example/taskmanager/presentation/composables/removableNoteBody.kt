package com.example.taskmanager.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun RemovableNoteBody(
    modifier: Modifier = Modifier,
    onRemove: () -> Unit,
    body: @Composable ConstraintLayoutScope.(Modifier) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .animateContentSize()
            .border(1.dp, MaterialTheme.colorScheme.inversePrimary)
    ) {
        val (bodyRef, removeButton) = createRefs()
        body(
            Modifier
                .constrainAs(bodyRef) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        IconButton(
            onClick = onRemove,
            modifier = Modifier.constrainAs(removeButton) {
                top.linkTo(bodyRef.top)
                bottom.linkTo(bodyRef.top)
                end.linkTo(parent.end)
            }
        ) {
            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
        }
    }
}
