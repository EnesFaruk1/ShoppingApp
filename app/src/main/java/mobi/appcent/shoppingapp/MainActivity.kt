package mobi.appcent.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import mobi.appcent.shoppingapp.adapter.ViewPagerAdapter
import mobi.appcent.shoppingapp.model.CategoryList
import mobi.appcent.shoppingapp.model.ProductList
import mobi.appcent.shoppingapp.model.Shopping
import mobi.appcent.shoppingapp.network.NetworkHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("ID", fragmentContainerView.id.toString())
        supportFragmentManager.findFragmentById(R.id.nav_graph)

        ivPinkCircle.visibility = View.INVISIBLE

    }
}