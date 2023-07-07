package com.example.analogclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomAnalogClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mHeight = 0
    private var mWidth = 0
    private var timeState = TimeState()

    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mPadding = 0
    private val mNumeralSpacing = 0
    private var mHandTruncation = 0
    private var mHourHandTruncation = 0

    private var mRadius = 0
    private lateinit var mPaint: Paint
    private var mRect = Rect()
    private var isInit = false

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50
            val minAttr = min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            isInit = true
        }
        canvas?.drawColor(Color.DKGRAY);

        mPaint.reset()
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 4F
        mPaint.isAntiAlias = true
        canvas?.drawCircle(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mRadius + mPadding - 10).toFloat(),
            mPaint
        )

        mPaint.style = Paint.Style.FILL
        canvas?.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 12F, mPaint)

        val fontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            14F,
            resources.displayMetrics
        )
        mPaint.textSize = fontSize

        mClockHours.forEach {
            val hour = it.toString()
            mPaint.getTextBounds(hour, 0, hour.length, mRect)

            val angle = Math.PI / 6 * (it - 3);
            val x = (mWidth / 2 + cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2).toInt()

            canvas?.drawText(hour, x.toFloat(), y.toFloat(), mPaint)
        }

        val time = timeState.time

        drawHandLine(
            canvas!!,
            (((time / 3600) + (time % 60).toDouble() / 60) * 5f).toDouble(),
            isHour = true,
            isSecond = false
        )
        drawHandLine(canvas, (time % 3600) / 60.toDouble(), false, false)
        drawHandLine(canvas, (time % 60).toDouble(), false, true)

        postInvalidateDelayed(500)
        invalidate()

    }

    fun setTime(timeS: TimeState) {
        timeState = timeS
        invalidate()
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint.color = Color.YELLOW
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + sin(angle) * handRadius).toFloat(),
            mPaint
        )
    }
}