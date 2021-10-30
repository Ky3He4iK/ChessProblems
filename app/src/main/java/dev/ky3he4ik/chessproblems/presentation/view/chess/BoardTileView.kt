package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.scaleMatrix
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.BitmapStorage
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece
import kotlin.math.min


class BoardTileView : View {
    private val isWhiteTile: Boolean
        get() = (posX + posY) % 2 == 1

    private var backgroundBitmap: Bitmap? = null
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private var coordBitmap: Bitmap? = null
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private var pieceBitmap: Bitmap? = null
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    var posX: Int = -1
        set(value) {
            if (field != value) {
                field = value
                onCoordChanged()
            }
        }

    var posY: Int = -1
        set(value) {
            if (field != value) {
                field = value
                onCoordChanged()
            }
        }

    var isSelectedTile: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                onSelectionChanged()
            }
        }

    var isSelectedPosition: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                onSelectionChanged()
            }
        }

    var isBoardFlipped: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                onCoordChanged()
            }
        }

    var piece: Piece? = null
        set(value) {
            if (field != value) {
                field = value
                pieceBitmap = BitmapStorage.getBitmap(value?.id, context)
            }
        }

    private fun onCoordChanged() {
        coordBitmap = when {
            !isBoardFlipped && posY == 0 && posX in 0..7 -> BitmapStorage.getBitmap(
                coordsLetter[posX],
                context
            )
            isBoardFlipped && posY == 7 && posX in 0..7 -> BitmapStorage.getBitmap(
                coordsLetterReversed[posX],
                context
            )
            isBoardFlipped && posX == 7 && posY in 0..7 -> BitmapStorage.getBitmap(
                coordsNumber[posY],
                context
            )
            !isBoardFlipped && posX == 0 && posY in 0..7 -> BitmapStorage.getBitmap(
                coordsNumber[posY],
                context
            )
            else -> null
        }
        onSelectionChanged()
    }

    private fun onSelectionChanged() {
        backgroundBitmap = BitmapStorage.getBitmap(
            when {
                isSelectedPosition && isWhiteTile != isBoardFlipped ->
                    R.drawable.background_white_selected_position
                isSelectedPosition ->
                    R.drawable.background_green_selected_position
                isSelectedTile && isWhiteTile != isBoardFlipped ->
                    R.drawable.background_white_selected
                isSelectedTile ->
                    R.drawable.background_green_selected
                isWhiteTile != isBoardFlipped ->
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
            canvas.drawBitmap(
                it, scaleMatrix(
                    width.toFloat() / it.width,
                    height.toFloat() / it.height
                ), null
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val displayMetrics = resources.displayMetrics
//        Log.e(
//            "Scale", "${displayMetrics.density} ${displayMetrics.densityDpi}\n" +
//                    "${displayMetrics.heightPixels} ${displayMetrics.widthPixels}\n" +
//                    "${displayMetrics.scaledDensity} ${displayMetrics.xdpi} ${displayMetrics.ydpi}\n" +
//                    "$widthMeasureSpec $heightMeasureSpec\n"
//        )
        val size = min(displayMetrics.heightPixels / 14, displayMetrics.widthPixels / 8)
//        Log.d("Scale", "Set $size")
        setMeasuredDimension(size, size)
    }

    companion object {
        val coordsLetter = arrayOf(
            R.drawable.coord_a1, R.drawable.coord_b, R.drawable.coord_c,
            R.drawable.coord_d, R.drawable.coord_e, R.drawable.coord_f, R.drawable.coord_g,
            R.drawable.coord_h
        )
        val coordsLetterReversed = arrayOf(
            R.drawable.coord_a, R.drawable.coord_b, R.drawable.coord_c,
            R.drawable.coord_d, R.drawable.coord_e, R.drawable.coord_f, R.drawable.coord_g,
            R.drawable.coord_h8
        )
        val coordsNumber = arrayOf(
            R.drawable.coord_1, R.drawable.coord_2, R.drawable.coord_3,
            R.drawable.coord_4, R.drawable.coord_5, R.drawable.coord_6, R.drawable.coord_7,
            R.drawable.coord_8
        )
    }
}
