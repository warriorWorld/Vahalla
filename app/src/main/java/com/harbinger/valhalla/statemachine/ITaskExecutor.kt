package com.harbinger.valhalla.statemachine

/**
 * Created by acorn on 2020/12/24.
 */
abstract class ITaskExecutor {
    abstract fun getCurrentTask(): StatusTask?
    abstract fun finshCurrentExecuteNext(
        nextStatus: String,
        res: Any? = null,
        currentStatus: StatusTask? = null
    )
}