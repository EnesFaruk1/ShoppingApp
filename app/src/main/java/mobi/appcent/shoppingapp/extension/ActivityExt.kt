package mobi.appcent.shoppingapp.extension

import android.app.Activity
import android.widget.Toast

fun Activity.showToastMsg(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}