package com.hj.scientificcalulator.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hj.scientificcalulator.R
import com.hj.scientificcalulator.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = DataBindingUtil.setContentView(this, R.layout.activity_main)
        view.view = this
        view.lifecycleOwner = this

        val transactionManager = supportFragmentManager.beginTransaction()
        transactionManager.replace(R.id.main, CalculatorFragment())
        transactionManager.commit()
    }


}