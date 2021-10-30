package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


class RecyclerItemClickListener(
    context: Context?,
    recyclerView: RecyclerView,
    private val listener: OnItemClickListener
) :
    OnItemTouchListener {
    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onLongItemClick(view: View?, position: Int)
    }

    private val gestureDetector: GestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val child: View? = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null)
                listener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child))
        }
    })

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView: View = view.findChildViewUnder(e.x, e.y) ?: return false
        if (gestureDetector.onTouchEvent(e)) {
            listener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    companion object {
        fun registerListener(context: Context?, recyclerView: RecyclerView, listener: OnItemClickListener) {
            recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, recyclerView, listener))
        }
    }
}
