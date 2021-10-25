package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dev.ky3he4ik.chessproblems.R

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

    val isWhiteTile: Boolean
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
        if (posY == 0 && posX in 0..7) {
            coordBitmap = BitmapStorage.getBitmap(
                when (posX) {
                    0 -> R.drawable.coord_a1
                    1 -> R.drawable.coord_b
                    2 -> R.drawable.coord_c
                    3 -> R.drawable.coord_d
                    4 -> R.drawable.coord_e
                    5 -> R.drawable.coord_f
                    6 -> R.drawable.coord_g
                    7 -> R.drawable.coord_h
                    else -> 0 // unused but necessary to kotlin
                }, context
            )
        } else if (posX == 0 && posY in 0..7) {
            coordBitmap = BitmapStorage.getBitmap(
                when (posY) {
                    0 -> R.drawable.coord_a1
                    1 -> R.drawable.coord_2
                    2 -> R.drawable.coord_3
                    3 -> R.drawable.coord_4
                    4 -> R.drawable.coord_5
                    5 -> R.drawable.coord_6
                    6 -> R.drawable.coord_7
                    7 -> R.drawable.coord_8
                    else -> 0 // unused but necessary to kotlin
                }, context
            )
        } else
            coordBitmap = null
    }

    private fun onSelectionChanged() {
        backgroundBitmap = BitmapStorage.getBitmap(
            when {
                isSelectedPosition && isWhiteTile ->
                    R.drawable.background_white_selected_position
                isSelectedPosition && !isWhiteTile ->
                    R.drawable.background_green_selected_position
                isSelectedTile && isWhiteTile ->
                    R.drawable.background_white_selected
                isSelectedTile && !isWhiteTile ->
                    R.drawable.background_green_selected
                isWhiteTile ->
                    R.drawable.background_white
                else ->
                    R.drawable.background_green
            }, context
        )
    }

    constructor(context: Context?) : super(context)

    constructor(context: Context?, atts: AttributeSet?) : super(context, atts)


    init {
        isFocusable = true
        onSelectionChanged()
    }

    public override fun onDraw(canvas: Canvas) {
        listOfNotNull(backgroundBitmap, coordBitmap, pieceBitmap).forEach {
            matrix.setScale(width.toFloat() / it.width, height.toFloat() / it.height)
            canvas.drawBitmap(it, matrix, paint)
        }
    }

    companion object {
        var paint = Paint()
    }
}
