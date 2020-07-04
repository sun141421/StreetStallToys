package com.bozhong.streetstalltoys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bozhong.streetstalltoys.databinding.ActivityDisplayBinding
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity() {
    private var actDuration = 5000L;


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityDisplayBinding>(this, R.layout.activity_display)
        binding.bean = intent.getSerializableExtra(KEY_TOY) as StallToy?

        startScroll()
    }

    private fun startScroll() {
        val anim = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT,
            1F,
            TranslateAnimation.RELATIVE_TO_PARENT,
            -1F,
            TranslateAnimation.RELATIVE_TO_PARENT,
            0F,
            TranslateAnimation.RELATIVE_TO_PARENT,
            0F
        ).apply {
            duration = actDuration
            repeatMode = TranslateAnimation.RESTART
            repeatCount = TranslateAnimation.INFINITE
        }

        tvDisplay.startAnimation(anim)
    }

    fun toggleActionView(@Suppress("UNUSED_PARAMETER") view: View) {
        groupAction.visibility =
            if (groupAction.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    fun doSpeedUp(@Suppress("UNUSED_PARAMETER") view: View) {
        tvDisplay.clearAnimation()
        actDuration -= 200
        startScroll()
    }

    fun doSlowDown(@Suppress("UNUSED_PARAMETER") view: View) {
        tvDisplay.clearAnimation()
        actDuration += 200
        startScroll()
    }

    fun doExit(@Suppress("UNUSED_PARAMETER") view: View) {
        finish()
    }
}