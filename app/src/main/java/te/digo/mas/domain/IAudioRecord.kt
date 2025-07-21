package te.digo.mas.domain

import java.io.File

interface IAudioRecorder {
    fun start(outputFile: File)
    fun stop()
}