package com.harbinger.valhalla.statemachine

/**
 * Created by acorn on 2020/12/24.
 */
class TaskExecutor(val mSession: StatusSession) : ITaskExecutor() {
    private val TAG = "StatusMachine";
    private var currentTask: StatusTask? = null

    override fun getCurrentTask(): StatusTask? {
        return currentTask
    }

    override fun finshCurrentExecuteNext(
        nextStatus: String,
        res: Any?,
        currentStatus: StatusTask?
    ) {
        TODO("Not yet implemented")
    }
}