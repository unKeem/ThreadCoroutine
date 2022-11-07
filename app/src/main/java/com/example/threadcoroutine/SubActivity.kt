package com.example.threadcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.threadcoroutine.databinding.ActivityMainBinding
import com.example.threadcoroutine.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "${intent.getStringExtra("message")}", Toast.LENGTH_SHORT).show()

    }
}