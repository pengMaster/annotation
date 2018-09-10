package king.bird.annotation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import com.chen.annotation.ActionInstaller

/**
 * <pre>
 * author : Wp
 * e-mail : 18141924293@163.com
 * time   : 2018/09/10
 * desc   : 测试页
 * version: 1.0
 *</pre>
 */
@SuppressLint("Registered")
class TestActivity : Activity() {

    private var mBtnTest: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBtnTest = findViewById<Button>(R.id.mBtnTest)
        ActionInstaller.processAnnotations(this)

    }

    @Action(source = "mBtnTest")
    fun onBtnClick() {
        Log.e("Test", "mBtnTest点击")
    }
}
