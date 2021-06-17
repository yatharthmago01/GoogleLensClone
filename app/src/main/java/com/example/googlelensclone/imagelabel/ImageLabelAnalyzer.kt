package com.example.googlelensclone.imagelabel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.android.synthetic.main.activity_lens.*

class ImageLabelAnalyzer(private val context: Context): ImageAnalysis.Analyzer {

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
                    (context as Activity).textView.apply{
                        visibility = View.VISIBLE
                        text = """${label.text}(${label.confidence * 100}%)"""
                    }
                }
            } .addOnFailureListener { ex ->
                Log.d("LABEL", "Detection failed!", ex)
            } .addOnCompleteListener {
                imageProxy.close()
            }
        } ?: imageProxy.close()
    }
}