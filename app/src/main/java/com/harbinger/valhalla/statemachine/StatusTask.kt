package com.harbinger.valhalla.statemachine

/**
 * Created by acorn on 2020/12/24.
 */
interface Creator {
    fun create(): StatusTask
}

abstract class StatusTask(private val mStatus: String) {
    private val TAG = "StatusTask";
    private var mSession: StatusSession? = null
    private var isFinished = false
    private var mExecutor: ITaskExecutor? = null

    fun prepare(statusSession: StatusSession, executor: ITaskExecutor) {
        mSession = statusSession;
        isFinished = false;
        mExecutor = executor;
    }

    /// @param reason 由于什么原因导致状态切换到此
    /// @param res 上次的状态切换时是否有res
    /// @param lastStatus 切换前的状态
    abstract fun onDoing(res: Any, lastStatus: String)

    abstract fun onFinally(res: Any, nextStatus: String)

    fun finish(nextStatus: String, res: Any? = null) {
        if (!isFinished) {
            print("$TAG $mSession StatusTask.finish: nextStatus=$nextStatus");
            isFinished = true;
            mExecutor?.finshCurrentExecuteNext(
                nextStatus, res, this
            )
        } else {
            print("$TAG $mSession StatusTask.finish: it's finishing now!");
        }
    }

    fun getStatus(): String {
        return mStatus;
    }

    fun getSession(): StatusSession? {
        return mSession;
    }

    fun isFinished(): Boolean {
        return isFinished;
    }
}