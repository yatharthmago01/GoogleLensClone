package com.example.googlelensclone.textrecog

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class TextRecogAnalyzer: ImageAnalysis.Analyzer {

    private val recognizer = TextRecognition.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("TEXT", "Image Captured!")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            recognizer.process(inputImage).addOnSuccessListener { text ->
                text.textBlocks.forEach{ block ->
                    Log.d("TEXT", """LINES = ${block.lines.joinToString("\n"){it.text}}""".trimIndent())
                }
            } .addOnFailureListener { ex ->
                Log.d("TEXT", "Detection failed!", ex)
            } .addOnCompleteListener {
                imageProxy.close()
            }
        } ?: imageProxy.close()
    }
}