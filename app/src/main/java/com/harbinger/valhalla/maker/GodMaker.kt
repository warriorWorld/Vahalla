package com.harbinger.valhalla.maker

import com.harbinger.valhalla.R
import com.harbinger.valhalla.bean.Bliss
import com.harbinger.valhalla.bean.BlissBean
import com.harbinger.valhalla.bean.GodBean

/**
 * Created by acorn on 2020/12/26.
 */
object GodMaker {
    fun getLifeGod(): GodBean {
        val bliss = ArrayList<BlissBean>()
        bliss.add(BlissBean(Bliss.ADD_HP, 2, 2))
        bliss.add(BlissBean(Bliss.ADD_HP, 4, 4))
        bliss.add(BlissBean(Bliss.ADD_HP, 8, 6))
        bliss.add(BlissBean(Bliss.ADD_HP, 12, 8))
        return GodBean("Isis", R.drawable.ic_god, bliss)
    }

    fun getHelmetHater(): GodBean {
        val bliss = ArrayList<BlissBean>()
        bliss.add(BlissBean(Bliss.REMOVE_HELMET, 2, 2))
        bliss.add(BlissBean(Bliss.REMOVE_HELMET, 4, 4))
        bliss.add(BlissBean(Bliss.REMOVE_HELMET, 8, 6))
        bliss.add(BlissBean(Bliss.REMOVE_HELMET, 12, 8))
        return GodBean("helmet hater", R.drawable.ic_god, bliss)
    }
}