package com.liuleilei.macbook.mydemo.kotlinactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.liuleilei.macbook.mydemo.R

/**
 * create by liu
 * on2019/7/9
 */
class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)
    }
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ConstraintLayoutActivity::class.java)
            //starter.putExtra();
            context.startActivity(starter)
        }
    }
}
