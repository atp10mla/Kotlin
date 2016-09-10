package larsson.silver.kotlin.view.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiDetector
import com.google.android.gms.vision.MultiProcessor
import larsson.silver.kotlin.R


import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_barcode_scanner.*
import larsson.silver.kotlin.barcode.BarcodeTrackerFactory
import java.io.IOException

/**
 * Created by ossi on 10/09/16.
 */
class BarcodeScannerActivity : AppCompatActivity() {

    private val RC_HANDLE_GMS = 9001
    // permission request codes need to be < 256
    private val RC_HANDLE_CAMERA_PERM = 2

    private var mCameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)

        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            initScanner()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        Log.w("BarcodeScannerActivity", "Camera permission is not granted. Requesting permission")

        val permissions = arrayOf(Manifest.permission.CAMERA)

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }

        val thisActivity = this

       // val listener = object : View.OnClickListener() {
       //     override fun onClick(view: View) {
       //        ActivityCompat.requestPermissions(thisActivity, permissions,
       //                 RC_HANDLE_CAMERA_PERM)
       //     }
       // }

        //Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
         //       Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, listener).show()
    }

    fun initScanner() {

        val barcodeDetector = BarcodeDetector.Builder(this).build()
        val barcodeFactory = BarcodeTrackerFactory(xFaceOverlay)
        barcodeDetector.setProcessor(
                MultiProcessor.Builder(barcodeFactory).build())

        val multiDetector = MultiDetector.Builder().add(barcodeDetector).build()

        if (!multiDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w("BarcodeScannerActivity", "Detector dependencies are not yet available.")

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w("BarcodeScannerActivity", getString(R.string.low_storage_error))
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        mCameraSource = CameraSource.Builder(applicationContext, multiDetector).setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1024, 768).setAutoFocusEnabled(true).setRequestedFps(20.0f).build()
    }

    /**
     * Restarts the camera.
     */
    protected override fun onResume() {
        super.onResume()

        startCameraSource()
    }

    /**
     * Stops the camera.
     */
    protected override fun onPause() {
        super.onPause()
        xPreview.stop()
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    protected override fun onDestroy() {
        super.onDestroy()
        if (mCameraSource != null) {
            mCameraSource!!.release()
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *

     * @param requestCode  The request code passed in [.requestPermissions].
     * *
     * @param permissions  The requested permissions. Never null.
     * *
     * @param grantResults The grant results for the corresponding permissions
     * *                     which is either [PackageManager.PERMISSION_GRANTED]
     * *                     or [PackageManager.PERMISSION_DENIED]. Never null.
     * *
     * @see .requestPermissions
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d("BarcodeScannerActivity", "Got unexpected permission result: " + requestCode)
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("BarcodeScannerActivity", "Camera permission granted - initialize the camera source")
            // we have permission, so create the camerasource
            initScanner()
            return
        }

        Log.e("BarcodeScannerActivity", "Permission not granted: results len = " + grantResults.size +
                " Result code = " + if (grantResults.size > 0) grantResults[0] else "(empty)")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Multitracker sample").setMessage(R.string.no_camera_permission).setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, i ->
            finish()
        }).show()
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {

        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (mCameraSource != null) {
            try {
                xPreview.start(mCameraSource, xFaceOverlay)
            } catch (e: IOException) {
                Log.e("BarcodeScannerActivity", "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }

        }
    }
}