package mobi.appcent.shoppingapp.extension

import android.os.Build
import android.text.Html
import android.widget.TextView


fun TextView.changeTextColor(colorId:Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setTextColor(resources.getColor(colorId,context.theme))
    };
}

fun TextView.convertHtml(html:String){
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }
}

