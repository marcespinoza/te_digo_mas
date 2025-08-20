package te.digo.mas.domain.useCase

import te.digo.mas.domain.repository.TeDigoMasRepository
import javax.inject.Inject

class AddTileUseCase @Inject constructor(
    private val repository: TeDigoMasRepository
) {

    suspend operator fun invoke(description: String){
        repository.addTile(description)
    }

}
