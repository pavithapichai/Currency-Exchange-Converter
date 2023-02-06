package com.example.paypaycurrencyconverter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.paypaycurrencyconverter.databinding.ActivityCurrencyConverterBinding
import com.example.paypaycurrencyconverter.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    //I am using viewBinding to get the reference of the views
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashScreen, MainActivity::class.java)
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