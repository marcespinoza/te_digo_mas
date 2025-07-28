package te.digo.mas.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import te.digo.mas.domain.useCase.PlayAudioUseCase
import te.digo.mas.domain.useCase.StartAudioRecorderUseCase
import te.digo.mas.domain.useCase.StopAudioRecorderUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val playAudioUseCase: PlayAudioUseCase,
    private val startAudioRecorderUseCase: StartAudioRecorderUseCase,
    private val stopAudioRecorderUseCase: StopAudioRecorderUseCase
): ViewModel() {

    fun playAudio(file: File) {
        playAudioUseCase(file)
    }

    fun saveNewAudio(file: File) {
        startAudioRecorderUseCase(file)
    }

    fun stopAudioRecorder() {
        stopAudioRecorderUseCase()
    }

}