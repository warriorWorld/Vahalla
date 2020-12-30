package com.harbinger.valhalla.statemachine


/**
 * Created by acorn on 2020/12/24.
 */
open class StatusSession {
    private val TAG = "StatusSession";
    private val mTasks = HashMap<String, Creator>();
    private var mExecutor: TaskExecutor? = null

    fun changeStatus(res: Any? = null, nextStatusTask: String) {
        if (mExecutor != null) {
            if (mExecutor?.getCurrentTask() != null) {
                mExecutor?.getCurrentTask()?.finish(nextStatusTask, res);
            } else {
                print("$TAG finish: current session has been finish");
            }
        } else {
            print("$TAG finish: $nextStatusTask as your start status.");
            start(nextStatusTask);
        }
    }

    private fun start(statusTask: String) {
        if (mExecutor == null) {
            val startTaskCreator = mTasks[statusTask];
            if (startTaskCreator != null) {
                mExecutor =  TaskExecutor (this);
                print("$TAG start: status=$statusTask");
                mExecutor?.finshCurrentExecuteNext(statusTask)
            } else {
                print("$TAG .start: no such status task, status=$statusTask");
            }
        } else {
            print("$TAG .start: session maybe has been started.");
        }
    }

    /// 构建状态图
    fun reg(status: String, taskCreator: Creator): StatusSession {
        mTasks[status] = taskCreator;
        return this;
    }

    fun getTasks(): Map<String, Creator> {
        return mTasks;
    }

    fun clean() {
        mTasks.clear();
        mExecutor = null;
    }
}