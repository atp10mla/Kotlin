package larsson.silver.kotlin.models.outpan

import com.google.gson.annotations.SerializedName

open class Product {

    @SerializedName("gtin")
    open var id: String? = null

    @SerializedName("name")
    open var name: String? = null

    @SerializedName("attributes")
    open var attributes: Attributes? = null


}