package te.digo.mas.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import te.digo.mas.data.TeDigoMasDatabase
import te.digo.mas.data.dao.TileDao
import te.digo.mas.data.repository.TeDigoMasRepositoryImpl
import te.digo.mas.domain.repository.TeDigoMasRepository

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideTeDigoMasDatabase(@ApplicationContext context: Context): TeDigoMasDatabase {
        return Room.databaseBuilder(context, TeDigoMasDatabase::class.java, "tedigomas")
            /*.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(" INSERT INTO Tile (name, text, audio) VALUES (''Hola', 'hola', 'Hola')")
                    db.execSQL(" INSERT INTO Tile (name, text, audio) VALUES (''Hola', 'Chau', 'Hola')")

                }
            })*/
            .createFromAsset("database/tedigomas.db")
            .build()
    }

    @Provides
    fun provideTileDao(database: TeDigoMasDatabase) = database.tileDao()

}
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideTedigoMasRepository(tileDao: TileDao): TeDigoMasRepository {
        return TeDigoMasRepositoryImpl(tileDao)
    }
}