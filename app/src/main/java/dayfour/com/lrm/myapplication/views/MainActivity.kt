package dayfour.com.lrm.myapplication.views

import android.animation.Animator
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.*
import com.yuyakaido.android.cardstackview.CardStackView
import dayfour.com.lrm.myapplication.R
import dayfour.com.lrm.myapplication.model.Demo
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Handler
import android.view.*
import dayfour.com.lrm.myapplication.model.OnSwipeTouchListener
import android.widget.Toast
import com.yuyakaido.android.cardstackview.SwipeDirection


class MainActivity : AppCompatActivity() {

    private lateinit var swipeView: CardStackView
    private lateinit var imageView:ImageView
    private lateinit var demoCardArray:ArrayList<Demo>
    private lateinit var adapter: BaseAdapter
    private lateinit var rootView:ConstraintLayout
    private var zoom:Boolean = true
    lateinit var handler: Handler;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(R.id.cl_root) as ConstraintLayout
        imageView = findViewById(R.id.iv_main) as ImageView
        swipeView = findViewById(R.id.swipe_view) as CardStackView
        demoCardArray = ArrayList<Demo>()
        handler= Handler()
        setData()
        zoom = true


        //to create the separate thread
        var runnable = Runnable()
        {
        }

        imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                swipeView.visibility = View.VISIBLE
                imageView.visibility = View.INVISIBLE
                changeBackgroundColor(rootView,false)
                //rootView.setBackgroundResource(R.color.transperent2)
            }
        })
    }


    private fun scaleView(view: View, byFactor: Float, duration: Long, listener: Animator.AnimatorListener?) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", byFactor)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", byFactor)
        scaleX.duration = duration
        scaleY.duration = duration
        val scaleAnim = AnimatorSet()
        scaleAnim.play(scaleX).with(scaleY)
        if (listener != null)
            scaleAnim.addListener(listener)
        scaleAnim.start()
    }

    fun getArrayList():ArrayList<Demo>
    {
        demoCardArray.add(Demo(R.drawable.dummy,"Card No. 1"))
        return demoCardArray
    }

    fun setData()
    {
        adapter = MyAndroidAdapter(this@MainActivity, getArrayList())
        swipeView.setAdapter(adapter)
        swipeView.visibility=View.INVISIBLE
        imageView.visibility=View.VISIBLE
        //rootView.setBackgroundResource(R.color.white)
       changeBackgroundColor(rootView,true)

    }

    fun changeBackgroundColor(view: View, isReverse: Boolean) {
        if (isReverse) {
            setBackground(view, ColorDrawable(Color.TRANSPARENT))
        } else {
            setBackground(view, null)
            val color = arrayOf(ColorDrawable(Color.TRANSPARENT), ColorDrawable(this@MainActivity.getResources().getColor(R.color.black1)))
            val trans = TransitionDrawable(color)
            trans.resetTransition()
            //This will work also on old devices. The latest API says you have to use setBackground instead.
            setBackground(view, trans)
            trans.startTransition(500)
        }

    }

    private fun setBackground(view: View, drawable: Drawable?) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = drawable
        } else {
            view.setBackgroundDrawable(drawable)
        }

    }


    class MyAndroidAdapter(private var context: Context, private var items: ArrayList<Demo>): BaseAdapter() {

        var activity: MainActivity
        var context1:Context

        init {
            context1=context
            activity= context as MainActivity
        }

        private class ViewHolder(row: View?) {
            var imageView: ImageView? = null

            init {
                this.imageView = row?.findViewById<ImageView>(R.id.iv_dummy)
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View?
            val viewHolder: ViewHolder
            if (convertView == null) {
                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.item_demo, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            var demo = items[position]
            viewHolder.imageView?.setBackgroundResource(demo.imageRec)

            fun leftRightSwipe()
            {
                activity.scaleView( activity.swipeView, 1f, 600, object : Animator.AnimatorListener {

                    override fun onAnimationStart(animator: Animator) {
                        activity.changeBackgroundColor(activity.rootView, false)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        activity.zoom = true
                    }

                    override fun onAnimationCancel(animator: Animator) {}

                    override fun onAnimationRepeat(animator: Animator) {}
                })
            }

            viewHolder.imageView?.setOnTouchListener(object: OnSwipeTouchListener(context)
            {

                override fun onSwipeRight() {
                    activity.zoom=false

                    Handler().postDelayed({
                        if(!activity.zoom) {
                            activity.swipeRight()
                        }
                    }, 1000)

                   leftRightSwipe()
                }

                override fun onSwipeLeft() {

                    activity.zoom=false
                    Handler().postDelayed({
                        if(!activity.zoom) {
                            activity.swipeLeft()
                        }
                    }, 1000)
                leftRightSwipe()
                }

                override fun onSwipeTop() {

                }

                override fun onSwipeBottom() {}
                override fun onTouch() {

                    if (activity.zoom) {
                        activity.swipeView.setCardEventListener(null)
                        activity.scaleView( activity.swipeView, 6f, 600, object : Animator.AnimatorListener {

                            override fun onAnimationStart(animator: Animator) {
                                activity.changeBackgroundColor(activity.rootView, true)
                            }

                            override fun onAnimationEnd(animator: Animator) {
                                activity.zoom = false
                            }

                            override fun onAnimationCancel(animator: Animator) {}

                            override fun onAnimationRepeat(animator: Animator) {}
                        })

                    } else {

                        activity.scaleView( activity.swipeView, 1f, 600, object : Animator.AnimatorListener {

                            override fun onAnimationStart(animator: Animator) {
                                activity.changeBackgroundColor(activity.rootView, false)
                            }

                            override fun onAnimationEnd(animator: Animator) {
                                activity.zoom = true
                            }

                            override fun onAnimationCancel(animator: Animator) {}

                            override fun onAnimationRepeat(animator: Animator) {}
                        })

                    }

                }

            })


            return view as View
        }

        override fun getItem(i: Int): Demo {
            return items[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getCount(): Int {
            return items.size
        }
    }


    fun swipeLeft() {
        /*val spots = extractRemainingTouristSpots()
        if (spots.isEmpty()) {
            return
        }*/

        val target = swipeView.getTopView()

        val rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f))
        rotation.duration = 200
        val translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f))
        val translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f))
        translateX.startDelay = 100
        translateY.startDelay = 100
        translateX.duration = 500
        translateY.duration = 500
        val set = AnimatorSet()
        set.playTogether(rotation, translateX, translateY)

        swipeView.swipe(SwipeDirection.Left, set)
    }

    fun swipeRight() {
       /* val spots = extractRemainingTouristSpots()
        if (spots.isEmpty()) {
            return
        }*/

        val target = swipeView.getTopView()

        val rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f))
        rotation.duration = 200
        val translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f))
        val translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f))
        translateX.startDelay = 100
        translateY.startDelay = 100
        translateX.duration = 500
        translateY.duration = 500
        val set = AnimatorSet()
        set.playTogether(rotation, translateX, translateY)

        swipeView.swipe(SwipeDirection.Right, set)
    }



}
