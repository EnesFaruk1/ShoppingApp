package mobi.appcent.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_pager.view.*
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.model.CategoryList

class ViewPagerAdapter (
    val images: List<CategoryList>
    ) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val curImage = images[position].imageUrl
        Picasso.get().load(curImage).into(holder.itemView.ivViewPager)
    }

    override fun getItemCount(): Int = images.size

        inner class ViewPagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    }
