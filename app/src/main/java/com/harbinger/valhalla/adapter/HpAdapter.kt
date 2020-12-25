package com.harbinger.valhalla.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.harbinger.valhalla.R

/**
 * Created by acorn on 2020/11/21.
 */
class HpAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var remainHp = 15
    private var imageRes = R.drawable.ic_heart1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hp, parent, false)
        return NormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return remainHp
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NormalViewHolder).hpIv.setImageResource(imageRes)
    }

    fun setRemainHp(remainHp: Int) {
        this.remainHp = remainHp
        notifyDataSetChanged()
    }

    fun removeHp(hp: Int) {
        remainHp -= hp
        notifyDataSetChanged()
    }

    fun addHp(hp: Int) {
        remainHp += hp
        notifyDataSetChanged()
    }

    fun setImageRes(imageRes: Int) {
        this.imageRes = imageRes
    }

    class NormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hpIv: ImageView = view.findViewById<View>(R.id.item_hp_iv) as ImageView
    }
}