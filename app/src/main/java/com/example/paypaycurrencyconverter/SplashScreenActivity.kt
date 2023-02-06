package com.example.paypaycurrencyconverter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.paypaycurrencyconverter.databinding.ActivitySplashScreenBinding
import com.example.paypaycurrencyconverter.view.CurrencyConverterActivity

class SplashScreenActivity : AppCompatActivity() {
    //I am using viewBinding to get the reference of the views
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar);
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, CurrencyConverterActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        },1000)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }
}