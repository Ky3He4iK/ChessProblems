package dev.ky3he4ik.chessproblems.presentation.view.chess.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log


object BitmapStorage {
    private val loadedBitmap: HashMap<Int, Bitmap> = hashMapOf()

    private fun loadBitmap(bitmapId: Int, context: Context): Bitmap? {
        try {
            return BitmapFactory.decodeResource(context.resources, bitmapId)
        } catch (e: Exception) {
            Log.e("BitmapStorage/load", e.message, e)
        }
        return null
    }

    fun getBitmap(bitmapId: Int, context: Context): Bitmap? {
        var bitmap = loadedBitmap[bitmapId]
        if (bitmap != null)
            return bitmap
        bitmap = loadBitmap(bitmapId, context) ?: return null
        loadedBitmap[bitmapId] = bitmap
        return bitmap
    }
}
