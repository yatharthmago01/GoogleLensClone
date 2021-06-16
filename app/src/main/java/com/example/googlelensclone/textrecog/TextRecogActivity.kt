package com.example.googlelensclone.textrecog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContextCompat
import com.example.googlelensclone.BaseLensActivity

class TextRecogActivity : BaseLensActivity() {

    override val imageAnalyzer = TextRecogAnalyzer()

    override fun startScanner() {
        startTextRecog()
    }

    private fun startTextRecog() {
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageAnalyzer)
    }

}