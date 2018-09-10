# 注解实现三部曲


#### 使用场景
 - EventBus使用

```java
    @Subscriber(mode = ThreadMode.MAIN, tag = EventTag.STORE_OUT_SUMMARIZING)
    internal fun refresh(list: Any) {
        getOutList()
    }
```

 - bufferknife点击事件使用

```java
       @OnClick(R.id.btn_login)
       public void onViewClicked() {

       }
```
原理：和监听有点类似，通过定义一个接口实现注解标签的使用，然后定义一个类，通过反射对所有使用注解的文件进行二级操作。

那么问题来了，如果我们想使用注解，该如何做昵？

### 使用三部曲

- 1.定义一个接口

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    public String source() default "nothing";
}
```
解释：

@Target

```java

@Target说明了Annotation所修饰的对象范围：Annotation可被用于
packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。

取值(ElementType)有：
1.CONSTRUCTOR:用于描述构造器
2.FIELD:用于描述域
3.LOCAL_VARIABLE:用于描述局部变量
4.METHOD:用于描述方法
5.PACKAGE:用于描述包
6.PARAMETER:用于描述参数
7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
```

@Retention

```java

@Retention定义了该Annotation被保留的时间长短：某些Annotation仅出现在源代码中，而被编译器丢弃；而另一些却被编译在class文件中；编译在class文件中的Annotation可能会被虚拟机忽略，而另一些在class被装载时将被读取（请注意并不影响class的执行，因为Annotation与class在使用上是被分离的）。使用这个meta-Annotation可以对
Annotation的“生命周期”限制。

取值（RetentionPoicy）有：
1.SOURCE:在源文件中有效（即源文件保留）
2.CLASS:在class文件中有效（即class保留）
3.RUNTIME:在运行时有效（即运行时保留）
```
- 2.注解事件处理器
```java
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
```

- 3.实际应用
```java
    @Action(source = "mBtnTest")
    fun onBtnClick() {
        Log.e("Test", "mBtnTest点击")
    }
```

项目demo详见Github



#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### github地址

 - 博主推荐：https://github.com/pengMaster/marry

 - 本项目链接：https://github.com/pengMaster/annotation

