package te.digo.mas.domain.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import te.digo.mas.domain.IAudioPlayer
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    private val mediaPlayer: MediaPlayer
): IAudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        mediaPlayer.setDataSource(file.absolutePath)
        mediaPlayer.start()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}