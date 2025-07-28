package te.digo.mas.domain.useCase

import te.digo.mas.domain.audio.AudioRecorder
import javax.inject.Inject

class StopAudioRecorderUseCase @Inject constructor(
    private val audioRecorder: AudioRecorder
) {
    operator fun invoke() = audioRecorder.stop()

}