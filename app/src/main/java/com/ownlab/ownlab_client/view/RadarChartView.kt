package com.ownlab.ownlab_client.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.ownlab.ownlab_client.models.RadarData
import com.ownlab.ownlab_client.models.RadarType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class RadarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var radarData: ArrayList<RadarData>? = null

    private var radarTypes = arrayListOf(
        RadarType.conductivity,
        RadarType.cooperation,
        RadarType.passion,
        RadarType.leadership,
        RadarType.diligence,
        RadarType.responsibility
    )

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val textDraw = TextPaint().apply {
        textSize = 28f
        textAlign = Paint.Align.CENTER
    }

    private var path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas ?: return

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f

        val radian = PI.toFloat() * 2 / 6
        val step = 5
        val heightMaxValue = height / 4 * 0.8f
        val heightStep = heightMaxValue / step
        val cx = width / 2f
        val cy = height / 2f
        var startX = cx
        var startY = cy - heightMaxValue

        for (i in 0..step) {
            var startX = cx
            var startY = (cy - heightMaxValue) + heightStep * i

            repeat(radarTypes.size) {
                val stopPoint = transformRotate(radian, startX, startY, cx, cy)

                canvas.drawLine(startX, startY, stopPoint.x, stopPoint.y, paint)

                startX = stopPoint.x
                startY = stopPoint.y
            }

            if (i < step) {
                val strValue = "${100 - 20 * i}"
                textDraw.textAlign = Paint.Align.LEFT
                canvas.drawText(
                    strValue,
                    startX + 10,
                    textDraw.fontMetrics.getBaseLine(startY),
                    textDraw
                )
            }
        }

        repeat(radarTypes.size) {
            val stopPoint = transformRotate(radian, startX, startY, cx, cy)
            canvas.drawLine(cx, cy, stopPoint.x, stopPoint.y, paint)

            startX = stopPoint.x
            startY = stopPoint.y
        }

        textDraw.textAlign = Paint.Align.CENTER

        startX = cx
        startY = (cy - heightMaxValue) * 0.89f

        var r = 0f

        path.reset()
        radarTypes.forEach { type ->
            val point = transformRotate(r, startX, startY, cx, cy)
            canvas.drawText(
                type.value,
                point.x,
                textDraw.fontMetrics.getBaseLine(point.y),
                textDraw
            )

            radarData?.firstOrNull { it.type == type }?.value?.let { value ->
                val conValue = heightMaxValue * value / 100
                val valuePoint = transformRotate(r, startX, cy - conValue, cx, cy)

                if (path.isEmpty) {
                    path.moveTo(valuePoint.x, valuePoint.y)
                } else {
                    path.lineTo(valuePoint.x, valuePoint.y)
                }
            }

            r += radian
        }

        path.close()
        paint.color = 0x7FFF0000
        paint.style = Paint.Style.FILL

        canvas.drawPath(path, paint)
    }

    fun setRadarData(_radarData: ArrayList<RadarData>) {
        if (_radarData.isEmpty()) {
            return
        }

        this.radarData = _radarData

        invalidate()
    }

    private fun transformRotate(radian: Float, x: Float, y: Float, cx: Float, cy: Float): PointF {
        val stopX = cos(radian) * (x - cx) - sin(radian) * (y - cy) + cx
        val stopY = sin(radian) * (x - cx) + cos(radian) * (y - cy) + cy

        return PointF(stopX, stopY)
    }
}

fun Paint.FontMetrics.getBaseLine(y: Float): Float {
    val halfTextAreaHeight = (bottom - top) / 2

    return y - halfTextAreaHeight - top
}