package com.example.googlelensclone.barcode

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(private val context: Context): ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("BARCODE", "Image Captured!")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            scanner.process(inputImage).addOnSuccessListener { codes ->
                codes.forEach{barcode ->
                    Log.d("BARCODE", """Format = ${barcode.format} Value = ${barcode.rawValue}""".trimIndent())
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(barcode.rawValue))
                    if (i.resolveActivity(context.packageManager) != null)
                        context.startActivity(i)
                }
            } .addOnFailureListener { ex ->
                Log.d("BARCODE", "Detection failed!", ex)
            } .addOnCompleteListener {
                imageProxy.close()
            }
        } ?: imageProxy.close()
    }
}