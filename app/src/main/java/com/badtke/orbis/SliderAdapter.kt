package com.badtke.orbis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by nbtk on 5/4/18.
 */
class SliderAdapter : RecyclerView.Adapter<SliderItemViewHolder>() {

    private val data: ArrayList<String> = ArrayList();
    var callback: Callback? = null
    val clickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            v?.let { callback?.onItemClicked(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_world, parent, false)

        itemView.setOnClickListener(clickListener)

        val horizontalViewHolder = SliderItemViewHolder(itemView)
        return horizontalViewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SliderItemViewHolder, position: Int) {
        holder.tvItem?.setImageResource(R.drawable.comic_world_croped)
    }

    fun setData(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClicked(view: View)
    }
}