package te.digo.mas.ui

import android.Manifest
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import te.digo.mas.R
import te.digo.mas.ui.viewmodel.AudioViewModel
import te.digo.mas.ui.viewmodel.TileViewModel
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddTileDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    tileViewModel: TileViewModel,
    audioViewModel: AudioViewModel
) {
    var isPressed by remember { mutableStateOf(false) }
    var tileDescription by remember { mutableStateOf("") }
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.audiowaves
        )
    )
    val scale by animateFloatAsState(if (isPressed) 1.2f else 1f, label = "")
    val context = LocalContext.current
    var audioFile: File? by remember { mutableStateOf(null) }

    val audioRecordPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = tileDescription,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(20.dp)
                        //.focusRequester(focusRequester)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Transparent
                        ),
                    onValueChange = { tileDescription = it },
                    label = { Text("Tile Name") },

                )
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LottieAnimation(
                        isPlaying = isPressed,
                        composition = preloaderLottieComposition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(width = 200.dp, height = 100.dp)
                    )
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = { audioFile?.let { audioViewModel.playAudio(it) } },
                            enabled = audioFile != null,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.playicon),
                                contentDescription =  "Play icon button"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { /* handle long press */ },
                                        onPress = {
                                            if (audioRecordPermissionState.status.isGranted) {
                                            isPressed = true
                                                val audioDir = File(context.filesDir, "AudioTiles")
                                                if (!audioDir.exists()) {
                                                    audioDir.mkdir()
                                                }
                                                val file = File(audioDir, "$tileDescription.mp3")
                                                audioFile = file
                                                audioViewModel.saveNewAudio(file)

                                                tryAwaitRelease()
                                                isPressed = false
                                            audioViewModel.stopAudioRecorder()
                                            } else {
                                                audioRecordPermissionState.launchPermissionRequest()
                                            }
                                        }
                                    )
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.microphone),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = { tileViewModel.addTile(tileDescription) },
                        modifier = Modifier.padding(8.dp),
                        enabled = tileDescription.isNotBlank()
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }

}
