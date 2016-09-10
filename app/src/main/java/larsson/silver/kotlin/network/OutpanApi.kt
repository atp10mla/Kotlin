package larsson.silver.kotlin.network

import larsson.silver.kotlin.models.outpan.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by markus on 16-09-10.
 */
interface OutpanApi {

    @GET("v2/products/{eanCode}")
    fun getProduct(@Path("eanCode") eanCode: String, @Query("apikey") apiKey: String) : Call<Product>
}