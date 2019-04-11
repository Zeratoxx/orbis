package com.badtke.orbis

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by nbtk on 5/4/18.
 */
class SliderItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    val tvItem: ImageView? = itemView?.findViewById(R.id.tv_item)
    val textView_worldname: TextView? = itemView?.findViewById(R.id.textView_worldname)
    val imageView_locked: ImageView? = itemView?.findViewById(R.id.imageView_locked)
}