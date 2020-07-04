package com.bozhong.streetstalltoys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bozhong.streetstalltoys.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setupUI(binding)
    }

    private fun setupUI(binding: ActivityMainBinding) {
        binding.bean = StallToy()

        binding.btnStartPlay.setOnClickListener {
            startPlay(binding.bean as StallToy)
        }
    }

    private fun startPlay(bean: StallToy) {
        if (bean.txt.isEmpty()) {
            Toast.makeText(this, "文字不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        DisplayActivity.launch(this, bean)
    }
}