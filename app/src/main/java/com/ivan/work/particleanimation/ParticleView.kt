package com.ivan.work.particleanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin


class ParticleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val particleList = mutableListOf<Particle>()

    private val particlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    private var emitterX = 0f
    private var emitterY = 0f

    private val random = java.util.Random()

    fun addParticles(count: Int, emitterX: Float, emitterY: Float) {
        this.emitterX = emitterX
        this.emitterY = emitterY
        for (i in 0 until count) {
            particleList.add(createParticle())
        }
        invalidate()
    }

    private fun createParticle(): Particle {
        val particleX = emitterX + random.nextInt(200) - 100f
        val particleY = emitterY + random.nextInt(200) - 100f
        val particleSize = random.nextInt(50) + 50f
        val particleSpeed = random.nextInt(1000) + 500f
        return Particle(particleX, particleY, particleSize, particleSpeed)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        particleList.forEach { particle ->
            canvas.drawCircle(particle.x, particle.y, particle.size, particlePaint)
        }
    }

    fun updateParticles(deltaTime: Long) {
        particleList.forEach { particle ->
            val distance = particle.speed * deltaTime / 1000f
            val angle = random.nextInt(360).toDouble()
            val deltaX = (distance * cos(angle)).toFloat()
            val deltaY = (distance * sin(angle)).toFloat()
            particle.x += deltaX
            particle.y += deltaY
        }
        particleList.removeAll { particle -> particle.y < 0 }
        invalidate()
    }

    data class Particle(
        var x: Float,
        var y: Float,
        var size: Float,
        var speed: Float
    )
}

