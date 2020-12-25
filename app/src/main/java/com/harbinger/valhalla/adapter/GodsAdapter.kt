package com.harbinger.valhalla.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harbinger.valhalla.R
import com.harbinger.valhalla.bean.GodBean
import com.harbinger.valhalla.listener.OnRecycleItemClickListener

/**
 * Created by acorn on 2020/11/21.
 */
class GodsAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var gods: List<GodBean>? = null
    private var onRecycleItemClickListener: OnRecycleItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gods, parent, false)
        return NormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (null == gods || gods?.size!! <= 0) {
            0
        } else {
            gods?.size!!
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val god = gods?.get(position)

        god?.icon?.let { (holder as NormalViewHolder).godIv.setImageResource(it) }
        god?.name?.let { (holder as NormalViewHolder).nameTv.text = it }
        (holder as NormalViewHolder).godRl.setOnClickListener {
            onRecycleItemClickListener?.onItemClick(position)
        }
    }

    fun setGods(gods: List<GodBean>) {
        this.gods = gods
    }

    fun setOnRecycleItemClickListener(onRecycleItemClickListener: OnRecycleItemClickListener) {
        this.onRecycleItemClickListener = onRecycleItemClickListener
    }

    class NormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var godRl: View = view.findViewById(R.id.item_god_rl)
        var godIv: ImageView = view.findViewById<View>(R.id.god_iv) as ImageView
        var nameTv: TextView = view.findViewById(R.id.name_tv) as TextView
    }
}