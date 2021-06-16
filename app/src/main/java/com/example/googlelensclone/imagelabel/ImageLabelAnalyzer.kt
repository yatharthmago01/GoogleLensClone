package com.example.googlelensclone.imagelabel

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class ImageLabelAnalyzer: ImageAnalysis.Analyzer {

    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.Builder()
        .setConfidenceThreshold(0.7F).build())

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("LABEL", "Image Captured!")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            labeler.process(inputImage).addOnSuccessListener { labels ->
                labels.forEach{label ->
                    Log.d("LABEL", """Format = ${label.text} Value = ${label.confidence}""".trimIndent())
                }
            } .addOnFailureListener { ex ->
                Log.d("LABEL", "Detection failed!", ex)
            } .addOnCompleteListener {
                imageProxy.close()
            }
        } ?: imageProxy.close()
    }
}