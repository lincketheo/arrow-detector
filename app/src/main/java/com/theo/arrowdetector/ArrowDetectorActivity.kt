package com.theo.arrowdetector

import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import org.opencv.android.CameraActivity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat


class ArrowDetectorActivity : CameraActivity(), CvCameraViewListener2 {
    private var openCVCameraView: CameraBridgeViewBase? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "called onCreate")
        super.onCreate(savedInstanceState)

        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully")
        } else {
            Log.e(TAG, "OpenCV initialization failed!")
            Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_LONG).show()
            return
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.surface_view)
        openCVCameraView =
            findViewById<View>(R.id.camera_activity_java_surface_view) as CameraBridgeViewBase
        openCVCameraView!!.visibility = SurfaceView.VISIBLE
        openCVCameraView!!.setCvCameraViewListener(this)
    }

    public override fun onPause() {
        super.onPause()
        openCVCameraView?.disableView()
    }

    public override fun onResume() {
        super.onResume()
        openCVCameraView?.enableView()
    }

    override fun getCameraViewList(): List<CameraBridgeViewBase?> {
        return listOf(openCVCameraView)
    }

    public override fun onDestroy() {
        super.onDestroy()
        openCVCameraView?.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {}
    override fun onCameraViewStopped() {}
    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        return inputFrame.rgba()
    }

    companion object {
        private const val TAG = "OCVSample::Activity"
    }
}
