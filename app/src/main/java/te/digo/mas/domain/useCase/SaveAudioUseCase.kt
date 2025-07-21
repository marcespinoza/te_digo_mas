package te.digo.mas.domain.useCase

import te.digo.mas.domain.audio.AudioRecorder
import java.io.File

class SaveAudioUseCase (
    private val audioRecorder: AudioRecorder
) {
    operator fun invoke(outputFile: File) {
        audioRecorder.start(outputFile)

    }
}