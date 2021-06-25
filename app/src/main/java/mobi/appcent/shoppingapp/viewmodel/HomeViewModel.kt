package mobi.appcent.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobi.appcent.shoppingapp.model.Shopping
import mobi.appcent.shoppingapp.network.NetworkHelper
import mobi.appcent.shoppingapp.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val networkHelper = NetworkHelper()
    val productResponseList = MutableLiveData<Shopping>()

    fun getAllProducts(){
        networkHelper.shoppingService?.getAllProducts()?.enqueue(object :
            Callback<Shopping> {
            var mainTitle:String = ""
            override fun onResponse(
                call: Call<Shopping>,
                response: Response<Shopping>
            ) {
                response.body()?.let {
                    productResponseList.value = it
                }
            }

            override fun onFailure(call: Call<Shopping>, t: Throwable) {
                Log.e(Constant.HOME_VIEW_MODEL_TAG,"Data couldn't be taken!")
            }
        })
    }
}


