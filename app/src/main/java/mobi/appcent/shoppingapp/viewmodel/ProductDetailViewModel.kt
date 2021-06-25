package mobi.appcent.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobi.appcent.shoppingapp.model.ProductDetail
import mobi.appcent.shoppingapp.model.SizeModel
import mobi.appcent.shoppingapp.network.NetworkHelper
import mobi.appcent.shoppingapp.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel : ViewModel() {
    private val networkHelper = NetworkHelper()
    val productDetailResponseList = MutableLiveData<ProductDetail>()

    var localProduct: ProductDetail? = null
    val buttonEnable = MutableLiveData<Boolean>(false)


    fun getProductDetails(productId: Int) {
        networkHelper.shoppingService?.getProductDetails(productId)
            ?.enqueue(object : Callback<ProductDetail> {
                override fun onResponse(
                    call: Call<ProductDetail>,
                    response: Response<ProductDetail>
                ) {
                    response.body().let { product ->
                        localProduct = product

                        localProduct?.sizes?.forEach { size ->
                            if (localProduct?.sizeModel == null)
                                localProduct!!.sizeModel = arrayListOf()
                            localProduct?.sizeModel?.add(SizeModel(size, false))
                        }

                        productDetailResponseList.value = localProduct
                    }
                }

                override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                    Log.e(Constant.PRODUCT_DETAIL_VIEW_MODEL_TAG, "Data couldn't be taken!")
                }
            })
    }

    fun checkButtonEnable() {
        localProduct?.let { localProduct ->
            if (!localProduct.sizeModel.isNullOrEmpty()) {
                localProduct.sizeModel.forEach {
                    if (it.isSelected) {
                        buttonEnable.value = true
                        return@forEach
                    }
                }
            }
        }
    }
}