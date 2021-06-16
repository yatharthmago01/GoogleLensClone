package com.example.googlelensclone.facedetect

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectAnalyzer: ImageAnalysis.Analyzer {

    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder().setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL).build())

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.i("FACEDETECT", "Image Captured!")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)

            detector.process(inputImage).addOnSuccessListener { faces ->
                Log.d("FACEDETECT", "Faces = ${faces.size}")
                faces.forEach { face ->
                    Log.d("FACEDETECT", """Left-eye ${face.leftEyeOpenProbability}
                        Right-eye ${face.rightEyeOpenProbability} Smile ${face.smilingProbability}""".trimMargin())
                }
            }.addOnFailureListener { ex ->
                Log.d("FACEDETECT", "Detection failed!", ex)
            }.addOnCompleteListener {
                imageProxy.close()
            }

        } ?:imageProxy.close()
    }
}