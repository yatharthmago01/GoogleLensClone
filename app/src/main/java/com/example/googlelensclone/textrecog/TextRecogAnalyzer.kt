package com.example.googlelensclone.textrecog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.android.synthetic.main.activity_lens.*

class TextRecogAnalyzer(private val context: Context): ImageAnalysis.Analyzer {

    private val recognizer = TextRecognition.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("TEXT", "Image Captured!")

        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            recognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    text.textBlocks.forEach { block ->
                        val finalString = block.lines.joinToString { it.text }
                        Log.d(
                            "TEXT RECOGNITION", """
                                TEXT : $finalString
                        """.trimIndent()
                        )
                        (context as Activity).textView.apply{
                            visibility = View.VISIBLE
                            setText(finalString)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("TEXT RECOGNITION", "Error recognizing Text", exception)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } ?: imageProxy.close()
    }
}