package com.bozhong.streetstalltoys

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bozhong.streetstalltoys.databinding.ActivityDisplayBinding
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity() {
    private val initDuration = 3000L

    private var actDuration = initDuration
    private val anim by lazy {
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        //按initDuration是滑过一个屏幕所需时间的速度来计算
        actDuration = (initDuration * (screenWidth * 2 + tvDisplay.width) / screenWidth).toLong()
        ObjectAnimator.ofFloat(
            tvDisplay,
            "translationX",
            screenWidth,
            -(screenWidth + tvDisplay.width)
        ).apply {
            duration = actDuration
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
    }

    companion object {
        private const val KEY_TOY = "StallToy"
        fun launch(context: Context, toy: StallToy) {
            val i = Intent(context, DisplayActivity::class.java)
            i.putExtra(KEY_TOY, toy)
            if (context !is Activity) {
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(i)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityDisplayBinding>(this, R.layout.activity_display)
        binding.bean = intent.getSerializableExtra(KEY_TOY) as StallToy?

        //post 以便tvDisplay的宽度是准确的
        tvDisplay.post {
            anim.start()
        }
        //TODO 禁止触摸滑动

        //字体大小，全屏高度减去上下50sp
        tvDisplay.textSize =
            resources.displayMetrics.heightPixels / resources.displayMetrics.scaledDensity - 100

    }

    fun toggleActionView(@Suppress("UNUSED_PARAMETER") view: View) {
        groupAction.visibility =
            if (groupAction.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    fun doSpeedUp(@Suppress("UNUSED_PARAMETER") view: View) {
        anim.cancel()
        //加减速按1/10
        actDuration -= actDuration / 10
        anim.duration = actDuration
        anim.start()
    }

    fun doSlowDown(@Suppress("UNUSED_PARAMETER") view: View) {
        anim.cancel()
        //加减速按1/10
        actDuration += actDuration / 10
        anim.duration = actDuration
        anim.start()
    }

    fun doExit(@Suppress("UNUSED_PARAMETER") view: View) {
        finish()
    }
}