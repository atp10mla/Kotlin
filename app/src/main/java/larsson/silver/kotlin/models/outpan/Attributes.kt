package larsson.silver.kotlin.models.outpan

import com.google.gson.annotations.SerializedName

/**
 * Created by markus on 16-09-10.
 */
open class Attributes {

    @SerializedName("Amount")
    open var amount: String? = null

    @SerializedName("Brand")
    open var brand: String? = null

    @SerializedName("Net Weight")
    open var weight: String? = null

    @SerializedName("Title")
    open var title: String? = null

    @SerializedName("Volume")
    open var volume: String? = null
}