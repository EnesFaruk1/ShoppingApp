package mobi.appcent.shoppingapp.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.enum.CategoryType
import mobi.appcent.shoppingapp.extension.changeTextColor
import mobi.appcent.shoppingapp.model.ProductList
import mobi.appcent.shoppingapp.utils.Constant.TURKISH_LIRA

class ProductRecyclerAdapter (private val productList: List<ProductList>, var onProductItemClickListener: OnProductClickListener ): RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(){
            val item = productList[adapterPosition]
            Picasso.get().load(item.image).into(itemView.ivProductImage)
            itemView.tvProductName.text = item.productName
            item.priceDiscount?.let {
                itemView.tvOldPrice.text = item.price + TURKISH_LIRA
                itemView.tvNewPrice.text = item.priceDiscount + TURKISH_LIRA
                itemView.tvOldPrice.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
            }?: run {
                itemView.tvOldPrice.text = item.price + TURKISH_LIRA
                itemView.tvNewPrice.visibility = View.GONE
                itemView.tvOldPrice.changeTextColor(R.color.title_color)
            }
            item.category?.let { categoryCheck(it,itemView) }
            itemView.setOnClickListener {
                onProductItemClickListener.onClick(adapterPosition)
            }
        }
    }
}
fun categoryCheck(data:String, itemView: View){
    when(data){
        CategoryType.MALE.value-> {
            itemView.tvCategoryName.setBackgroundResource(R.drawable.male_circle)
            itemView.tvCategoryName.changeTextColor(R.color.male_blue)
        }
        CategoryType.FEMALE.value -> {
            itemView.tvCategoryName.setBackgroundResource(R.drawable.female_circle)
            itemView.tvCategoryName.changeTextColor(R.color.female_text)
        }
    }
    itemView.tvCategoryName.text = data
}

interface OnProductClickListener{
    fun onClick(position: Int)
}
