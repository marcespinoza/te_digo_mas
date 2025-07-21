package te.digo.mas.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import te.digo.mas.data.TeDigoMasDatabase
import te.digo.mas.data.dao.TileDao
import te.digo.mas.data.repository.TeDigoMasRepositoryImpl
import te.digo.mas.domain.repository.TeDigoMasRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideTeDigoMasDatabase(@ApplicationContext context: Context): TeDigoMasDatabase {
        return Room.databaseBuilder(context, TeDigoMasDatabase::class.java, "tedigomas")
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
@Module
@InstallIn(SingletonComponent::class)
object MediaPlayerModule {
    @Provides
    fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer {
        return MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }
}