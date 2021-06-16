package com.example.googlelensclone.facedetect

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection

class FaceDetectAnalyzer: ImageAnalysis.Analyzer {

    val detector = FaceDetection.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("FACEDETECT", "Image Captured!")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)

            detector.process(inputImage).addOnSuccessListener { faces ->
                faces.forEach { face ->

                }
            }.addOnFailureListener { ex ->
                Log.d("FACEDETECT", "Detection failed!", ex)
            }.addOnCompleteListener {
                imageProxy.close()
            }

        } ?:imageProxy.close()
    }
}