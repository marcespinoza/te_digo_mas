package te.digo.mas.domain.useCase

import te.digo.mas.domain.audio.AudioPlayer
import java.io.File

class PlayAudioUseCase (
    private val audioPlayer: AudioPlayer
) {
    operator fun invoke (audioFile: File) {
        audioPlayer.playFile(audioFile)
    }

}