package com.example.chums.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.chums.R
import com.example.chums.ui.main_activity.MainActivity

class SplashActivity : AppCompatActivity() {
    companion object{
        private val SPLASH_TIME : Long = 2000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIME)
    }
}