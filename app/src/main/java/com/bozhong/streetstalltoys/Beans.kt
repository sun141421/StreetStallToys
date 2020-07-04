package com.bozhong.streetstalltoys

import android.graphics.Color
import java.io.Serializable

data class StallToy(
    var txt: String = "",
    var txtColor: Int = Color.BLACK,
    var screenColor: Int = Color.WHITE,
    var audioPath: String? = null,
    var screenScroll: Boolean = false,
    var scrollSpeed: Int = 0
) : Serializable