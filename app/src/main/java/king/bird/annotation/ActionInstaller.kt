package com.chen.annotation

import android.view.View
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

import king.bird.annotation.Action

/**
 * <pre>
 * author : Wp
 * e-mail : 18141924293@163.com
 * time   : 2018/09/10
 * desc   : 注解实例
 * version: 1.0
 *</pre>
 */
class ActionInstaller {

    companion object {

        fun processAnnotations(client: Any) {
            val clientClass = client.javaClass

            for (m in clientClass.declaredMethods) {

                //获取指定Annotation对象
                val listener = m.getAnnotation(Action::class.java!!)

                if (listener != null) {
                    try {
                        val f = clientClass.getDeclaredField(listener.source)
                        f.isAccessible = true
                        //控件对象
                        val focusView = f.get(client)
                        addListener(focusView, client, m)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

            }

        }

        @Throws(Exception::class)
        private fun addListener(focusView: Any, client: Any, m: Method) {

            val handler = InvocationHandler { proxy, method, args ->
                //场景类调用 onBtnClick() 方法
                m.invoke(client)
            }

            val onClickListener = Proxy.newProxyInstance(null, arrayOf<Class<*>>(View.OnClickListener::class.java), handler)
            val setOnClickListenerMethod = focusView.javaClass.getMethod("setOnClickListener", View.OnClickListener::class.java)
            setOnClickListenerMethod.invoke(focusView, onClickListener)

        }

    }

}