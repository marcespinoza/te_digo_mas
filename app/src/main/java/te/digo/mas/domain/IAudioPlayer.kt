package te.digo.mas.domain

import java.io.File

interface IAudioPlayer {
    fun playFile(file: File)
    fun stop()
}