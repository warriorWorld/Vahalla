package com.harbinger.valhalla.statemachine

/**
 * Created by acorn on 2020/12/24.
 */
abstract class ITaskExecutor {
    abstract fun getCurrentTask(): StatusTask?
    abstract fun finshCurrentExecuteNext(
        nextStatusTask: String,
        res: Any? = null,
        currentStatusTask: StatusTask? = null
    ): Boolean
}