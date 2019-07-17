package com.liuleilei.macbook.mydemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView

import com.liuleilei.macbook.mydemo.R

/**
 * create by liu
 * on2019/6/14
 * 约束动画
 */
class ConstraintAnimationActivity : AppCompatActivity(), View.OnClickListener {
    private var view1: View? = null
    private var view2: View? = null
    private var view3: View? = null
    private var arrowView: ImageView? = null
    internal var isShow = true
    private var parentConstrainLayout: ConstraintLayout? = null
    private var constraintSet: ConstraintSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_animation)
        parentConstrainLayout = findViewById(R.id.parent_constrain_layout)
        view1 = findViewById(R.id.view1)
        view2 = findViewById(R.id.view2)
        view3 = findViewById(R.id.view3)
        arrowView = findViewById(R.id.arrow_view)
        arrowView!!.setOnClickListener(this)
        view2!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.arrow_view -> rotateAnimation()
            R.id.view2 -> arrowView!!.rotation = 180f
            else -> {
            }
        }
    }

    /**
     * 旋转动画
     */
    private fun rotateAnimation() {
        val rotate = RotateAnimation((if (isShow) 0 else 180).toFloat(), (if (isShow) 180 else 0).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 200
        rotate.fillAfter = true
        arrowView!!.startAnimation(rotate)
        isShow = !isShow
        constraintSet = ConstraintSet()
        val parentLayout = parentConstrainLayout!!.parent as ConstraintLayout
        constraintSet!!.clone(parentLayout)
        constraintSet!!.clear(R.id.parent_constrain_layout, ConstraintSet.TOP)
        constraintSet!!.connect(R.id.parent_constrain_layout, ConstraintSet.TOP, R.id.view4, ConstraintSet.BOTTOM, if (isShow) 0 else 300)
        val transition = AutoTransition()
        transition.duration = 200
        TransitionManager.beginDelayedTransition(parentLayout, transition)
        constraintSet!!.applyTo(parentLayout)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ConstraintAnimationActivity::class.java)
            // starter.putExtra();
            context.startActivity(starter)

        }
    }

}
