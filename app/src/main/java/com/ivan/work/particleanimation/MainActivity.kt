package com.ivan.work.particleanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var particleView: ParticleView
    private var isAnimating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        particleView = findViewById(R.id.particle_view)

        val startButton: Button = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            particleView.addParticles(150, particleView.width / 2f, particleView.height.toFloat())
        }

        val handler = Handler(Looper.getMainLooper())
        var lastUpdateTime = System.currentTimeMillis()

        val updateRunnable = object : Runnable {
            override fun run() {
                val deltaTime = System.currentTimeMillis() - lastUpdateTime
                particleView.updateParticles(deltaTime)
                lastUpdateTime = System.currentTimeMillis()
                handler.postDelayed(this, 10)
            }
        }

        handler.post(updateRunnable)

    }

    override fun onStop() {
        super.onStop()
        isAnimating = false
    }

}