package dayfour.com.lrm.myapplication.model

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View



/**
 * Created by manasi on 12/2/18.
 */
/*
open class OnSwipeTouchListener(ctx: Context): View.OnTouchListener {

    private var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())

    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(p1)
    }

    class GestureListener: GestureDetector.SimpleOnGestureListener()
    {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
        fun onSwipeRight() {}

        fun onSwipeLeft() {}

        fun onSwipeTop() {}

        fun onSwipeBottom() {}



    }
    }
*/
