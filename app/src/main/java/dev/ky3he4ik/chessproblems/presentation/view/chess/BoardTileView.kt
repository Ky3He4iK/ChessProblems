package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.scaleMatrix
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.BitmapStorage
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece

class BoardTileView : View {
    private var backgroundBitmap: Bitmap? = null
    private var coordBitmap: Bitmap? = null
    private var pieceBitmap: Bitmap? = null

    var posX: Int = -1
        set(value) {
            field = value
            onCoordChanged()
        }

    var posY: Int = -1
        set(value) {
            field = value
            onCoordChanged()
        }

    private val isWhiteTile: Boolean
        get() = (posX + posY) % 2 == 1

    var isSelectedTile: Boolean = false
        set(value) {
            field = value
            onSelectionChanged()
        }

    var isSelectedPosition: Boolean = false
        set(value) {
            field = value
            onSelectionChanged()
        }

    var piece: Piece? = null
        set(value) {
            field = value
            if (value == null) {
                pieceBitmap = null
            } else
                pieceBitmap = BitmapStorage.getBitmap(value.id, context)
        }

    private fun onCoordChanged() {
        val coordsLetter = arrayOf(
            R.drawable.coord_a1, R.drawable.coord_b, R.drawable.coord_c,
            R.drawable.coord_d, R.drawable.coord_e, R.drawable.coord_f, R.drawable.coord_g,
            R.drawable.coord_h
        )
        val coordsNumber = arrayOf(
            R.drawable.coord_a1, R.drawable.coord_2, R.drawable.coord_3,
            R.drawable.coord_4, R.drawable.coord_5, R.drawable.coord_6, R.drawable.coord_7,
            R.drawable.coord_8
        )
        coordBitmap = when {
            posY == 0 && posX in 0..7 -> BitmapStorage.getBitmap(coordsLetter[posX], context)
            posX == 0 && posY in 0..7 -> BitmapStorage.getBitmap(coordsNumber[posY], context)
            else -> null
        }
        onSelectionChanged()
    }

    private fun onSelectionChanged() {
        backgroundBitmap = BitmapStorage.getBitmap(
            when {
                isSelectedPosition && isWhiteTile ->
                    R.drawable.background_white_selected_position
                isSelectedPosition ->
                    R.drawable.background_green_selected_position
                isSelectedTile && isWhiteTile ->
                    R.drawable.background_white_selected
                isSelectedTile ->
                    R.drawable.background_green_selected
                isWhiteTile ->
                    R.drawable.background_white
                else ->
                    R.drawable.background_green
            }, context
        )
    }

    constructor(context: Context?, posX: Int = 0, posY: Int = 0) : super(context) {
        this.posX = posX
        this.posY = posY
    }

    constructor(context: Context?, atts: AttributeSet?) : super(context, atts)

    init {
        isFocusable = true
        onSelectionChanged()
    }

    public override fun onDraw(canvas: Canvas) {
        listOfNotNull(backgroundBitmap, coordBitmap, pieceBitmap).forEach {
//            it.width = width
//            it.height = height
            val canvasBitmap =
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
            val temp = Canvas(canvasBitmap)
            temp.save()


            temp.concat(matrix)
            temp.drawBitmap(it, Rect(0, 0, it.width, it.height), Rect(0, 0, it.width, it.height), paint)
            temp.restore()

//            matrix.setScale(width.toFloat() / it.width.toFloat(), height.toFloat() / it.height.toFloat() )
//            matrix.setScale(it.width / width.toFloat(), it.height / height.toFloat() )
            canvas.drawBitmap(it, scaleMatrix(width.toFloat() / it.width.toFloat(), height.toFloat() / it.height.toFloat() ), paint)
//            canvas.drawBitmap(canvasBitmap, Rect(0, 0, width, height), Rect(0, 0, width, height), paint)
//            canvas.drawBitmap(it, Rect(0, 0, width, height), Rect(0, 0, width, height), paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(128, 128)
    }

    companion object {
        var paint = Paint()
    }
}
