package com.hhjt.baselibrary.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/*
    Activity管理器
 */
class AppManager private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }

    /*
        Activity入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /*
        Activity出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /*
        获取当前栈顶
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /*
        清理栈
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     */
    fun finishActivityByClass(clazz: Class<*>) {
        val activity = activityStack.lastOrNull {
            it.javaClass == clazz
        }
        if (activity!=null){
            finishActivity(activity)
        }
    }

    /*
        退出应用程序
     */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}
