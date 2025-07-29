package te.digo.mas.domain.audio

import android.media.MediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import te.digo.mas.domain.IAudioPlayer
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    private val mediaPlayer: MediaPlayer
): IAudioPlayer {

    private var player: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    override fun playFile(file: File) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(file.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
        _isPlaying.value = true
        mediaPlayer.setOnCompletionListener { _isPlaying.value = false }
    }

    override fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        player?.release()
        player = null
        _isPlaying.value = false
    }
}