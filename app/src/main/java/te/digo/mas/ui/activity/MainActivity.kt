package te.digo.mas.ui.activity

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import te.digo.mas.domain.model.Tile
import te.digo.mas.domain.util.RandomColors
import te.digo.mas.ui.BottomShettDialog
import te.digo.mas.ui.viewmodel.TileViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PictogramApp()
        }
    }
}

@Composable
fun PictogramApp(viewModel: TileViewModel = hiltViewModel()) {
    val tiles by viewModel.listTiles.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        PictogramGrid(tiles)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PictogramGrid(listTiles: List<Tile>?) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val numRows = 2
    val itemHeight = screenHeight / numRows
    val randomColors = RandomColors()
    val context = LocalContext.current
    var selectedTile by remember { mutableStateOf<Tile?>(null) }
    var currentFocusedIndex by remember { mutableIntStateOf(-1) }
    var showDialog by remember {
        mutableStateOf(false)
    }

    if(showDialog) {
        selectedTile?.let { BottomShettDialog(onDismissRequest = { showDialog = false }, it) }
    }

    val mediaPlayer = remember { MediaPlayer() }
    val focusRequestersMap = remember { mutableMapOf<Int, FocusRequester>() }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Scaffold { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyDown) {
                        listTiles?.let {
                            val nextIndex = (currentFocusedIndex + 1) % it.size
                            focusRequestersMap[nextIndex]?.requestFocus()
                            return@onKeyEvent true
                        }
                    }
                    return@onKeyEvent false
                },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            listTiles?.let {
                itemsIndexed(listTiles) { index, pictogram ->
                    val itemColor = remember(pictogram) { Color(randomColors.getColor()) }
                    var isCardFocused by remember { mutableStateOf(false) }
                    val itemFocusRequester = remember {
                        focusRequestersMap.getOrPut(index) { FocusRequester() }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight - 12.dp)
                            .focusRequester(itemFocusRequester)
                            .onFocusChanged { focusState ->
                                isCardFocused = focusState.isFocused
                                if (focusState.isFocused) {
                                    currentFocusedIndex = index
                                }
                            }
                            .onKeyEvent {
                                if (it.key == Key.Tab && it.type == KeyEventType.KeyDown) {
                                    playSound(context, mediaPlayer, pictogram.audio)
                                    return@onKeyEvent true
                                }
                                return@onKeyEvent false
                            }
                            .focusable()
                            .border(
                                width = if (isCardFocused) 5.dp else 0.dp,
                                color = if (isCardFocused) Color.Black else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .combinedClickable(
                                onClick = {
                                    itemFocusRequester.requestFocus()
                                    playSound(context, mediaPlayer, pictogram.audio)
                                },
                                onLongClick = {
                                    selectedTile = pictogram
                                    showDialog = true
                                }
                            ),
                        colors = CardDefaults.cardColors(containerColor = itemColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pictogram.description,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(listTiles) {
            if (listTiles?.isNotEmpty() == true) {
                delay(100)
                focusRequestersMap[0]?.requestFocus()
            }
        }
    }
}

fun playSound(context: Context, mediaPlayer: MediaPlayer, audio: String) {
    try {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()

        val afd = context.resources.openRawResourceFd(context.resources.getIdentifier(audio, "raw", context.packageName))
        afd?.use {
            mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

