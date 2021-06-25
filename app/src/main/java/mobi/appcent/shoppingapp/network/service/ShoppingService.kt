package mobi.appcent.shoppingapp.network.service

import mobi.appcent.shoppingapp.model.ProductDetail
import mobi.appcent.shoppingapp.model.Shopping
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingService {
    @GET("main_page.php")
    fun getAllProducts():Call<Shopping>
    @GET("product_detail.php")
    fun getProductDetails(@Query("productId")productId: Int): Call<ProductDetail>

}