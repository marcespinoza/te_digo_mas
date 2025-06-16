package te.digo.mas.data.repository

import te.digo.mas.data.dao.TileDao
import te.digo.mas.domain.model.Tile
import te.digo.mas.data.entities.Tile as TileEntity
import te.digo.mas.domain.repository.TeDigoMasRepository
import javax.inject.Inject

class TeDigoMasRepositoryImpl @Inject constructor(
    private val tileDao: TileDao
): TeDigoMasRepository {
    override suspend fun getAllTiles(): List<Tile> {
        val list: List<TileEntity> = tileDao.getAllTiles()
        return list.map { it.toModel() }
    }

    override suspend fun addTile(description: String, audio: String) {
        return tileDao.insertTile(description, audio)
    }

    override suspend fun removeTile(id: Int){
        return tileDao.deleteTileById(id)
    }
}