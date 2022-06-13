package ru.gui.components.text


import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import ru.gui.R



class WelcomeText: TextView {



    var color: ArrayList<Int> = ArrayList<Int>()
    var primaryColor: Int = 0
    var secondaryColor: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setColors(primaryColor: Int, secondaryColor: Int){
        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val mycolor = mutableListOf<Int>(R.color.violet2,R.color.blue21,R.color.white,R.color.blue22,R.color.blue24).toIntArray()

        val floatArray = ArrayList<Float>(mycolor.size)
        for (i in mycolor.indices) {
            floatArray.add(i, 0.95f*i.toFloat() / (mycolor.size - 1))
        }
        if(changed){
           paint.shader = LinearGradient(0f, 0f, width.toFloat(), height.toFloat(),
            mycolor.map { ContextCompat.getColor(context,it) }.toIntArray(),
               floatArray.toFloatArray(),
                Shader.TileMode.MIRROR
              )
            var objectAnimator: ObjectAnimator = ObjectAnimator()








        }

    }


    open fun welcomeColor(): ArrayList<Int> {

        color.add(R.color.violet2)
        color.add(R.color.blue)
        color.add(R.color.white)
        color.add(R.color.violet1)
        color.add(R.color.violet3)
        return color
    }
}