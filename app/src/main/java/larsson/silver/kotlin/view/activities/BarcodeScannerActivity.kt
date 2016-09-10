package larsson.silver.kotlin.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import larsson.silver.kotlin.R


import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by ossi on 10/09/16.
 */
class BarcodeScannerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)
    }

    fun initScanner() {

        /*
        val barcodeDetector = BarcodeDetector.Builder(context).build()
        val barcodeFactory = BarcodeTrackerFactory()
        barcodeDetector.setProcessor(
                MultiProcessor.Builder(barcodeFactory).build())

        mCameraSource = CameraSource.Builder(context, barcodeDetector).setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1600, 1024).build()
        */
    }
}