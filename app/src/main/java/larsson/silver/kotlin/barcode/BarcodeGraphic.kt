package larsson.silver.kotlin.barcode

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log

import com.google.android.gms.vision.barcode.Barcode
import larsson.silver.kotlin.models.outpan.Product
import larsson.silver.kotlin.network.ApiHandler
import larsson.silver.kotlin.network.OutpanListener

/**
 * Graphic instance for rendering barcode position, size, and ID within an associated graphic
 * overlay view.
 */
internal class BarcodeGraphic(overlay: GraphicOverlay) : TrackedGraphic<Barcode>(overlay) {

    private val mRectPaint: Paint
    private val mTextPaint: Paint
    @Volatile private var mBarcode: Barcode? = null

    init {

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.size
        val selectedColor = COLOR_CHOICES[mCurrentColorIndex]

        mRectPaint = Paint()
        mRectPaint.color = selectedColor
        mRectPaint.style = Paint.Style.STROKE
        mRectPaint.strokeWidth = 4.0f

        mTextPaint = Paint()
        mTextPaint.color = selectedColor
        mTextPaint.textSize = 36.0f
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    internal override fun updateItem(barcode: Barcode) {
        mBarcode = barcode
        /* TODO works. should stop barcode searcher..
        ApiHandler.getProduct(barcode.displayValue, object: OutpanListener {
            override fun onSuccess(product: Product) {
                Log.e("BarcodeGraphic", "Product: " + product);
            }

            override fun onFailure(code: Int) {
                Log.e("BarcodeGraphic", "onFailure code: " + code);

            }

            override fun onFailure(code: String) {
                Log.e("BarcodeGraphic", "onFailure code: " + code);

            }

        })
        */
        postInvalidate()
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas) {
        val barcode = mBarcode ?: return

        // Draws the bounding box around the barcode.
        val rect = RectF(barcode.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas.drawRect(rect, mRectPaint)

        // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint)
    }

    companion object {
        private val COLOR_CHOICES = intArrayOf(Color.BLUE, Color.CYAN, Color.GREEN)
        private var mCurrentColorIndex = 0
    }
}
