package com.andrew.kotlinlearning

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RecyclerAdapter(val context: Context, val modelList: List<Model>, val onClicks: OnClick) :
    RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    interface OnClick {
        fun isClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_layout, p0, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(h: Holder, i: Int) {
        val user: Model = modelList.get(i)
        h.textview.text = user.image_name
        h.image.setImageBitmap(user.image)
        h.itemView.setOnClickListener {
            onClicks.isClick(i)
        }
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.recycler_image)
        val textview: TextView = v.findViewById(R.id.recycler_text)
    }
}