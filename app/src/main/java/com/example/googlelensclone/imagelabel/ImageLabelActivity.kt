package com.example.googlelensclone.imagelabel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.googlelensclone.BaseLensActivity
import com.example.googlelensclone.R

class ImageLabelActivity : BaseLensActivity() {

    override val imageAnalyzer = ImageLabelAnalyzer(this)

    override fun startScanner() {
        startImageLabeling()
    }

    private fun startImageLabeling() {
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageAnalyzer)
    }
}