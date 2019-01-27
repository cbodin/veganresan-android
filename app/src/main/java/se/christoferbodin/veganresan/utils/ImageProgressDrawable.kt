package se.christoferbodin.veganresan.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class ImageProgressDrawable(val context: Context) : CircularProgressDrawable(context) {
    init {
        strokeWidth = 10f
        centerRadius = 80f
        setColorSchemeColors(0xffccccc)
    }
}
