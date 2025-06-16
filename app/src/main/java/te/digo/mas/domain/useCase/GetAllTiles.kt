package te.digo.mas.domain.useCase

import kotlinx.coroutines.flow.flow
import te.digo.mas.data.entities.Tile
import te.digo.mas.domain.repository.TeDigoMasRepository
import javax.inject.Inject

class GetAllTiles @Inject constructor(private val repository: TeDigoMasRepository) {

    suspend operator fun invoke() =  flow{
       emit(repository.getAllTiles())
    }

}