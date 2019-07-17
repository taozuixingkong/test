package com.liuleilei.macbook.mydemo.activity

import android.content.ComponentName
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast

import com.liuleilei.macbook.basedispose.util.ToastUtil
import com.liuleilei.macbook.mydemo.R
import com.liuleilei.macbook.mydemo.adapter.MainActivityAdapter
import com.liuleilei.macbook.mydemo.inter.RecyclerInterface
import com.liuleilei.macbook.mydemo.kotlinactivity.ConstraintLayoutActivity

import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : AppCompatActivity(), RecyclerInterface {

    private var myRecyclerView: RecyclerView? = null
    private var list: MutableList<String>? = null
    private var mainActivityAdapter: MainActivityAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRecyclerView = findViewById(R.id.my_recycler_view)
        initData()
        initEvents()
    }

    private fun initData() {
        list = ArrayList()
        list!!.clear()
        list!!.add("分发")
        list!!.add("约束动画")
        list!!.add("radioGroup")
        list!!.add("tts")
        list!!.add("下载")
        list!!.add("https")
        list!!.add("约束布局")
        list!!.add("分发")
    }

    private fun initEvents() {
        myRecyclerView!!.layoutManager = LinearLayoutManager(this)
        myRecyclerView!!.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(0, 0, 0, 5)
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                val mPaint: Paint?
                mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                mPaint.color = Color.parseColor("#ff0000")
                mPaint.style = Paint.Style.FILL
                val left = parent.paddingLeft
                val right = parent.measuredWidth - parent.paddingRight
                val childSize = parent.childCount
                for (i in 0 until childSize) {
                    val child = parent.getChildAt(i)
                    val layoutParams = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + layoutParams.bottomMargin
                    val bottom = top + 5
                        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
                }
            }
        })
        mainActivityAdapter = MainActivityAdapter(this, list)
        myRecyclerView!!.adapter = mainActivityAdapter
        mainActivityAdapter!!.setRecyclerInterface(this)
    }

    override fun onItemListener(position: Int) {
        ToastUtil.showToastShort(this, list!![position])
        when (list!![position]) {
            "分发" -> {
            }
            "约束动画" -> ConstraintAnimationActivity.start(this)
            "radioGroup" -> RadioGroupTestActivity.start(this)
            "tts" -> TextToSpeechActivity.start(this)
            "下载" -> DownLoadActivity.start(this)
            "https" -> HttpUrlActivity.start(this)
            "约束布局" -> ConstraintLayoutActivity.start(this)
            else -> {
            }
        }

    }

    fun refreshLogInfo() {
        var AllLog = ""
        for (log in logList) {
            AllLog = AllLog + log + "\n\n"
        }
        //mLogView.setText(AllLog);
    }

    companion object {
        var logList: List<String> = CopyOnWriteArrayList()
    }


}
