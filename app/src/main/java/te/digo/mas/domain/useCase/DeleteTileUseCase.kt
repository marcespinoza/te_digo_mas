package te.digo.mas.domain.useCase

import kotlinx.coroutines.flow.flow
import te.digo.mas.domain.repository.TeDigoMasRepository
import javax.inject.Inject

class DeleteTileUseCase @Inject constructor(private val repository: TeDigoMasRepository) {

    suspend operator fun invoke(description: String){
        repository.deleteTile(description)
    }

}