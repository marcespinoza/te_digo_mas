package te.digo.mas.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import te.digo.mas.R
import te.digo.mas.domain.model.Tile
import te.digo.mas.ui.viewmodel.TileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomShettDialog(
    onDismissRequest: () -> Unit,
    selectedTile: Tile,
    tileViewModel: TileViewModel = hiltViewModel()
) {
    var isPressed by rememberSaveable { mutableStateOf(false) }
    var textState by remember { mutableStateOf(selectedTile.description) }
    var showConfirmationDialog by rememberSaveable { mutableStateOf(false)}

    val focusRequester = remember { FocusRequester() }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(isPressed) {
          if (isPressed) focusRequester.requestFocus()
    }

    if (showConfirmationDialog) ConfirmationDialog(
        onDismissRequest = { showConfirmationDialog = false },
        onConfirmation = {
            tileViewModel.deleteTile(selectedTile.description)
            showConfirmationDialog = false
            onDismissRequest()
                         },
        description = selectedTile.description
    )

    ModalBottomSheet(
        modifier = Modifier.focusRequester(focusRequester),
        sheetState = bottomSheetState,
        onDismissRequest = { onDismissRequest() },
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDefaults.DragHandle()
                Text(text = "Editar mosaico", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
            }
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                TextField(
                    value = TextFieldValue(
                        text = textState,
                        selection = TextRange(textState.length)
                    ),
                    enabled = isPressed,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(5.dp)
                        .focusRequester(focusRequester)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Transparent
                        ),
                    onValueChange = { textState = it.text },
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.comforta_medium)),
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
                IconButton(
                    onClick = { isPressed = !isPressed },
                    enabled = !isPressed
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit ,
                        contentDescription = if (isPressed) "Selected icon button" else "Unselected icon button."
                    )
                }
            }
            
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable { showConfirmationDialog = true },
                text = stringResource(id = R.string.delete),
                fontSize = 18.sp,
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.comforta_medium)),
                    textAlign = TextAlign.Center
                )
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDismissRequest() }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }

    }
}


