package te.digo.mas.domain.audio

import android.media.MediaPlayer
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
        mediaPlayer.reset()
        mediaPlayer.setDataSource(file.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}