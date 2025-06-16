package te.digo.mas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull
import te.digo.mas.domain.model.Tile as TileModel

@Entity(tableName = "Tile")

class Tile (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Nonnull val description: String,
    @Nonnull val audio: String,
) {
    fun toModel() = TileModel (
        description = description,
        audio = audio,
    )
}