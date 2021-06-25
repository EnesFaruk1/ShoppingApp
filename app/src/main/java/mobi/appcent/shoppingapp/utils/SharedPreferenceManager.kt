package mobi.appcent.shoppingapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

 class SharedPreferenceManager {

     var managerPref: SharedPreferences? = null

      fun createAddSharedPreferences(context: Context,boolValue: Boolean) {
          managerPref = context.getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
          val edited = managerPref!!.edit()
          edited.putBoolean(Constant.IS_ADDED_TAG,boolValue)
          edited.apply()
     }

    fun readFromSharedPreferences(context: Context, activity: Activity) {
        val sharedPreferences = context
            .getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isAdded = sharedPreferences.getBoolean(Constant.IS_ADDED_TAG, false)
        if (isAdded) {
            activity.ivPinkCircle?.visibility = View.VISIBLE
        } else {
            activity.ivPinkCircle?.visibility = View.INVISIBLE
        }

    }
}