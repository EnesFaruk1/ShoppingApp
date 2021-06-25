package mobi.appcent.shoppingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SendDataModelToMap(
    val latitude:String?,
    val longitude:String?,
    val productId:Int?
):Parcelable