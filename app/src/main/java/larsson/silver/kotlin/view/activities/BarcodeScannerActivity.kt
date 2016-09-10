package larsson.silver.kotlin.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import larsson.silver.kotlin.R

/**
 * Created by ossi on 10/09/16.
 */
class BarcodeScannerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)
    }
}