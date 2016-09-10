package larsson.silver.kotlin.network

import larsson.silver.kotlin.models.outpan.Product

/**
 * Created by markus on 16-09-10.
 */
interface OutpanListener {
    fun onSuccess(product: Product)

    fun onFailure(code: Int)

    fun onFailure(code: String)
}