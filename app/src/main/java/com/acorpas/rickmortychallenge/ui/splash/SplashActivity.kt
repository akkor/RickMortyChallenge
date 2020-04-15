package com.acorpas.rickmortychallenge.ui.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.acorpas.rickmortychallenge.R
import com.acorpas.rickmortychallenge.di.Injectable
import com.acorpas.rickmortychallenge.ui.characterList.MainActivity
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity(), Injectable {
    companion object {
        private val SPLASH_DELAY = TimeUnit.SECONDS.toMillis(2) // 2 seconds
    }

    private val handler = Handler()

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<MainActivity>()
            finish()
        }
    }

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}