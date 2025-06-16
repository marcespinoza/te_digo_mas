package te.digo.mas.domain.repository

import te.digo.mas.data.entities.Tile as TileEntity
import te.digo.mas.domain.model.Tile


interface TeDigoMasRepository {

    suspend fun getAllTiles(): List<Tile>

    suspend fun addTile(description: String, audio: String)

    suspend fun removeTile(id: Int)


}