package com.coderz.f1.sketchwareprojectmanager.circleimageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import com.coderz.f1.sketchwareprojectmanager.R

@SuppressLint("AppCompatCustomView")
class CircleImageView(context: Context, attrs:AttributeSet?): ImageView(context,attrs) {

    private var _borderColor:Int = Color.BLACK
    private var _borderWidth:Float = 1f

    var borderColor:Int
        get() = this._borderColor
        set(value){
            _borderColor = value
            updatePaint()
        }

    var borderWidth:Float
        get() = this._borderWidth
        set(value){
            _borderWidth = value
            updatePaint()
        }

    private var borderPaint: Paint = Paint()

    private var viewOutlineProvider: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(0,0,view.width,view.height)
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleImageView,0,0).apply {
            try {
                borderWidth = getDimension(R.styleable.CircleImageView_borderWidth, convertDpToPx(context,1).toFloat())
                borderColor = getColor(R.styleable.CircleImageView_borderColor,Color.BLACK)
            } finally {
                recycle()
            }
        }


        outlineProvider = viewOutlineProvider
        clipToOutline = true

        borderPaint.style = Paint.Style.STROKE
        updatePaint()
    }

    private fun updatePaint(){
        borderPaint.strokeWidth = convertDpToPx(context,borderWidth.toInt()).toFloat()
        borderPaint.color = borderColor
        borderPaint.isAntiAlias = true
        borderPaint.isDither = true
        invalidate()
        requestLayout()
    }

    private fun convertDpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawOval(0f,0f,width.toFloat(), height.toFloat(),borderPaint)
    }
}