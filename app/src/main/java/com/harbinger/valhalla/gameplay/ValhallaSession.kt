package com.harbinger.valhalla.gameplay

import com.harbinger.valhalla.scene.IValhallaScene
import com.harbinger.valhalla.statemachine.Creator
import com.harbinger.valhalla.statemachine.StatusSession
import com.harbinger.valhalla.statemachine.StatusTask

/**
 * Created by acorn on 2020/12/30.
 */
class ValhallaSession(var ui: IValhallaScene) : StatusSession() {
    init {
        reg(GameState.IDLE, object : Creator {
            override fun create(): StatusTask {
                return IdleStatus(GameState.IDLE, ui)
            }
        }).reg(GameState.OPPOSITE_ROUND, object : Creator {
            override fun create(): StatusTask {
                return OppositeRoundStatus(GameState.OPPOSITE_ROUND, ui)
            }
        }).reg(GameState.DICE, object : Creator {
            override fun create(): StatusTask {
                return DiceStatus(GameState.DICE, ui)
            }
        }).reg(GameState.CHOOSE_DICE, object : Creator {
            override fun create(): StatusTask {
                return DiceChooseStatus(GameState.CHOOSE_DICE, ui)
            }
        }).reg(GameState.CHOOSE_BLISS, object : Creator {
            override fun create(): StatusTask {
                return BlissChooseStatus(GameState.CHOOSE_BLISS, ui)
            }
        }).reg(GameState.FIGHT, object : Creator {
            override fun create(): StatusTask {
                return FightStatus(GameState.FIGHT, ui)
            }
        }).reg(GameState.BLISS, object : Creator {
            override fun create(): StatusTask {
                return BlissStatus(GameState.BLISS, ui)
            }
        }).reg(GameState.FINISH, object : Creator {
            override fun create(): StatusTask {
                return FinishStatus(GameState.FINISH, ui)
            }
        })
    }
}

class IdleStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {

    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class OppositeRoundStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class DiceStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class DiceChooseStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class BlissChooseStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class FightStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class BlissStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}

class FinishStatus(mStatus: String, val ui: IValhallaScene) : StatusTask(mStatus) {
    override fun onDoing(res: Any?, lastStatus: String?) {
    }

    override fun onFinally(res: Any?, nextStatus: String) {
    }
}