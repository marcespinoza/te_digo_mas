package te.digo.mas.domain.useCase

import te.digo.mas.domain.audio.AudioRecorder
import java.io.File
import javax.inject.Inject

class StartAudioRecorderUseCase @Inject constructor (
    private val audioRecorder: AudioRecorder
) {
    operator fun invoke(outputFile: File) {
        audioRecorder.start(outputFile)
    }
}