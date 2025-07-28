package te.digo.mas.domain.useCase

import te.digo.mas.domain.audio.AudioPlayer
import java.io.File
import javax.inject.Inject

class PlayAudioUseCase @Inject constructor(
    private val audioPlayer: AudioPlayer
) {
    operator fun invoke (audioFile: File) {
        audioPlayer.playFile(audioFile)
    }

}