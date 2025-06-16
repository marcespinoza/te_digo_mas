package te.digo.mas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import te.digo.mas.data.DatabaseConstants.DATABASE_VERSION
import te.digo.mas.data.dao.TileDao
import te.digo.mas.data.entities.Tile

@Database(
    entities = [Tile::class],
    version = DATABASE_VERSION
)

abstract class TeDigoMasDatabase : RoomDatabase() {
    abstract fun tileDao(): TileDao
}