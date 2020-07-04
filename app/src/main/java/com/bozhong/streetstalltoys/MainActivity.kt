package com.bozhong.streetstalltoys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bozhong.streetstalltoys.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import top.defaults.colorpicker.ColorPickerPopup

class MainActivity : AppCompatActivity() {
    val toy = StallToy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply { bean = toy }

        setupUI()
    }

    private fun setupUI() {


        btnStartPlay.setOnClickListener {
            startPlay()
        }

        tvPickScreenColor.setOnClickListener {
            showColorPicker(object : ColorPickerPopup.ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    toy.screenColor = color
                    vScreenColor.setBackgroundColor(color)
                }
            })
        }
        tvPickTxtColor.setOnClickListener {
            showColorPicker(object : ColorPickerPopup.ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    toy.txtColor = color
                    vTxtColor.setBackgroundColor(color)
                }
            })
        }
    }

    private fun showColorPicker(callback: ColorPickerPopup.ColorPickerObserver) {
        ColorPickerPopup.Builder(this).enableBrightness(false)
            .enableAlpha(false).showIndicator(true).build().show(callback)
    }

    private fun startPlay() {
        if (toy.txt.isEmpty()) {
            Toast.makeText(this, "文字不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        DisplayActivity.launch(this, toy)
    }
}