package te.digo.mas.domain.audio

import android.content.Context
import android.media.MediaRecorder
import te.digo.mas.domain.IAudioRecorder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRecorder @Inject constructor(
    private val mediaRecorder: MediaRecorder
): IAudioRecorder {

    private var recorder: MediaRecorder? = null


    override fun start(outputFile: File) {
        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}