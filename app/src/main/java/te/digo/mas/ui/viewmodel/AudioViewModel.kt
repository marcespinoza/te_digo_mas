package te.digo.mas.ui.viewmodel

import androidx.lifecycle.ViewModel
import te.digo.mas.domain.useCase.PlayAudioUseCase
import te.digo.mas.domain.useCase.SaveAudioUseCase
import java.io.File

class AudioViewModel(
    private val playAudioUseCase: PlayAudioUseCase,
    private val saveAudioUseCase: SaveAudioUseCase
): ViewModel() {

    fun playAudio(file: File) {
        playAudioUseCase(file)
    }

    fun saveNewAudio(file: File) {
        saveAudioUseCase(file)
    }

}