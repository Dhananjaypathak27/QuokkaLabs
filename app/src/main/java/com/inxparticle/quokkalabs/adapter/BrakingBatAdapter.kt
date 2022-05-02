package com.inxparticle.quokkalabs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.inxparticle.quokkalabs.R
import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse
import com.squareup.picasso.Picasso


private const val TAG = "BrakingBatAdapter"

class BrakingBatAdapter(
    private val mList: ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>,
    val itemClickedListner: ItemClickInterface,
    val mContext: Context
) : RecyclerView.Adapter<BrakingBatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        Picasso.get()
            .load(item.img)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageView);

        holder.itemView.setOnClickListener {


            itemClickedListner.itemClicked(position)

            holder.itemView.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        itemClickedListner.hideTheView(position)
                    }
                    MotionEvent.ACTION_DOWN -> {
                        itemClickedListner.showTheView(position)
                    }
                }
                true
            }

        }
    }

    override fun getItemCount(): Int {
        if (mList.size == 0) {
            return 0;
        }
        return mList.size;
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
    }
}

interface ItemClickInterface {
    fun itemClicked(position: Int)
    fun hideTheView(position: Int)
    fun showTheView(position: Int)
}