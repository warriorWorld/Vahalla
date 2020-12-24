package com.harbinger.valhalla.statemachine

import android.text.TextUtils

/**
 * Created by acorn on 2020/12/24.
 */
class TaskExecutor(private val mSession: StatusSession) : ITaskExecutor() {
    private val TAG = "StatusMachine";
    private var mCurrentStatusTask: StatusTask? = null

    override fun getCurrentTask(): StatusTask? {
        return mCurrentStatusTask
    }

    override fun finshCurrentExecuteNext(
        nextStatusTask: String,
        res: Any?,
        currentStatusTask: StatusTask?
    ): Boolean {
        return if (null == currentStatusTask || currentStatusTask === mCurrentStatusTask) {
            if (null != mCurrentStatusTask) {
                print("$TAG currentTask:$mCurrentStatusTask onFinally")
                mCurrentStatusTask?.onFinally(res, nextStatusTask)
            }
            if (!TextUtils.isEmpty(nextStatusTask)) {
                val taskCreator = mSession.getTasks()[nextStatusTask]
                if (taskCreator != null) {
                    val oldTask = mCurrentStatusTask
                    mCurrentStatusTask = taskCreator.create()
                    // taskCreator.create() cannot return null!!!
                    mCurrentStatusTask?.prepare(mSession, this)
                    mCurrentStatusTask?.onDoing(
                        res, oldTask?.getStatus()
                    )
                } else {
                    mCurrentStatusTask = null
                    print("$TAG no such status:\$nextStatusTask jam then")
                }
            } else {
                mCurrentStatusTask = null
                print("$TAG nextStatus is empty maybe status machine stopped.")
            }
            true
        } else {
            print(
                "$TAG not same with current task  current:$mCurrentStatusTask !=\$currentStatusTask"
            )
            false
        }
    }
}