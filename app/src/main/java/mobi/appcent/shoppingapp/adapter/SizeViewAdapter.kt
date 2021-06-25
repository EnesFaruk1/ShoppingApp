package mobi.appcent.shoppingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.size_item.view.*
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.model.SizeModel

class SizeViewAdapter(private val context: Context, private var sizeList: List<SizeModel>, private val onSizeItemClickListener:OnSizeItemClickListener): RecyclerView.Adapter<SizeViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.size_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = sizeList.size

    fun setSizeList(sizeListt: List<SizeModel>){
        this.sizeList = sizeListt
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(){
            val item = sizeList[adapterPosition]

            itemView.tvSizeButton.text = item.sizeText
            itemView.setOnClickListener {
                onSizeItemClickListener.onClick(adapterPosition)
            }
            if (item.isSelected){
                itemView.tvSizeButton.background = ContextCompat.getDrawable(context, R.drawable.size_button_selected)
            } else {
                itemView.tvSizeButton.background = ContextCompat.getDrawable(context, R.drawable.size_button_unselected)
            }
        }
    }
}
interface OnSizeItemClickListener{
    fun onClick(position:Int)
}