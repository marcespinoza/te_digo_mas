package te.digo.mas.data.dao

import androidx.room.Dao
import androidx.room.Query
import te.digo.mas.data.entities.Tile

@Dao
interface TileDao {

    @Query("SELECT * FROM Tile")
    fun getAllTiles(): List<Tile>

    @Query("SELECT * FROM tile WHERE id = :id")
    fun getTileById(id: Int): Tile

    @Query("DELETE FROM tile where name = :name")
    fun deleteTileByDescription(name: String)

    @Query("INSERT INTO tile (name) VALUES (:name)")
    fun insertTile(name: String)


}