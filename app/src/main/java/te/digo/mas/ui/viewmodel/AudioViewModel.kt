package te.digo.mas.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import te.digo.mas.domain.audio.AudioPlayer
import te.digo.mas.domain.useCase.PlayAudioUseCase
import te.digo.mas.domain.useCase.StartAudioRecorderUseCase
import te.digo.mas.domain.useCase.StopAudioRecorderUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val playAudioUseCase: PlayAudioUseCase,
    private val startAudioRecorderUseCase: StartAudioRecorderUseCase,
    private val stopAudioRecorderUseCase: StopAudioRecorderUseCase,
    audioPlayer: AudioPlayer
): ViewModel() {

    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying

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