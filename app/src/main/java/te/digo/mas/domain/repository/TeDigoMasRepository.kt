package te.digo.mas.domain.repository

import te.digo.mas.domain.model.Tile


interface TeDigoMasRepository {

    suspend fun getAllTiles(): List<Tile>

    suspend fun addTile(name: String)

    suspend fun deleteTile(name: String)

}