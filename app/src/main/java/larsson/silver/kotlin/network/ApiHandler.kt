package larsson.silver.kotlin.network

import larsson.silver.kotlin.models.outpan.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by markus on 16-09-10.
 */
object ApiHandler {
    var outPanApi: OutpanApi

    init {
        val retrofitOutpan: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.outpan.com/").build()

        outPanApi = retrofitOutpan.create(OutpanApi::class.java)
    }

    /*
    private object Holder { val INSTANCE = ApiHandler() }

    companion object {
        val instance: ApiHandler by lazy { Holder.INSTANCE }
    }
    */

    fun getProduct(eanCode: String, listener: OutpanListener) {
        outPanApi.getProduct(eanCode, "ec145bd5e2ca175e150b7df4234180aa").enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                listener.onFailure(call.request().toString())
            }
        })
    }
}