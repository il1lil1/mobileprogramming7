package com.example.newsifyprac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val subTextView = findViewById<TextView>(R.id.sub_tittle)

        titleTextView.visibility = View.INVISIBLE

        rotateAnim.duration = 2000
        rotateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                animFadeIn.duration = 3000
                animFadeIn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        Handler().postDelayed({
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000) // 2000 ms = 2 seconds
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        titleTextView.visibility = View.VISIBLE
                    }
                })
                titleTextView.startAnimation(animFadeIn)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        subTextView.startAnimation(rotateAnim)
    }
}
