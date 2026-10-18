package com.example.savethebunny

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var bunny: ImageView? = null
    private var obstacle: ImageView? = null
    private var scoreText: TextView? = null
    private var startButton: Button? = null
    private var gameArea: RelativeLayout? = null
    private val handler = Handler()
    private var gameRunnable: Runnable? = null
    private var score = 0
    private var isJumping = false
    private var isGameRunning = false
    private var bunnyY = 0f
    private var bunnyStartY = 0f
    private var obstacleX = 0f
    private var obstacleStartX = 0f
    private val jumpHeight = 250
    private val jumpDuration = 300
    private val obstacleSpeed = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bunny = findViewById<ImageView>(R.id.bunny)
        obstacle = findViewById<ImageView>(R.id.obstacle)
        scoreText = findViewById<TextView>(R.id.score_text)
        startButton = findViewById<Button>(R.id.start_button)
        gameArea = findViewById<RelativeLayout>(R.id.game_area)

        bunny!!.post(Runnable {
            bunnyStartY = bunny!!.getY()
            bunnyY = bunnyStartY
        })
        obstacle!!.post(Runnable {
            obstacleStartX = gameArea!!.getWidth().toFloat()
            resetObstacle()
        })

        startButton!!.setOnClickListener(View.OnClickListener { v: View? -> startGame() })
        gameArea!!.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent? ->
            if (event!!.getAction() == MotionEvent.ACTION_DOWN && isGameRunning) {
                jump()
            }
            true
        })
    }

    private fun startGame() {
        score = 0
        isGameRunning = true
        startButton!!.setVisibility(View.GONE)
        scoreText!!.setText("Score: 0")
        bunny!!.setY(bunnyStartY)
        resetObstacle()
        handler.post(object : Runnable {
            override fun run() {
                updateGame()
                if (isGameRunning) handler.postDelayed(this, 16)
            }
        }.also { gameRunnable = it })
    }

    private fun endGame() {
        isGameRunning = false
        handler.removeCallbacks(gameRunnable!!)
        startButton!!.setText("Restart Game")
        startButton!!.setVisibility(View.VISIBLE)
        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show()
    }

    private fun jump() {
        if (isJumping) return
        isJumping = true
        bunny!!.animate().y(bunnyStartY - jumpHeight).setDuration(jumpDuration.toLong())
            .withEndAction(
                Runnable {
                    bunny!!.animate().y(bunnyStartY).setDuration(jumpDuration.toLong())
                        .withEndAction(
                            Runnable { isJumping = false })
                }
            ).start()
    }

    private fun updateGame() {
        // Move obstacle
        obstacleX -= obstacleSpeed.toFloat()
        if (obstacleX + obstacle!!.getWidth() < 0) {
            score++
            scoreText!!.setText("Score: " + score)
            resetObstacle()
        }
        obstacle!!.setX(obstacleX)
        // Collision detection
//        if (this.isColliding) {
        if (alphaMasksOverlap(bunny!!, obstacle!!))
            endGame()
        }
    }

    private fun resetObstacle() {
        obstacleX = gameArea!!.getWidth().toFloat()
        obstacle!!.setY(bunnyStartY)
        obstacle!!.setX(obstacleX)
    }

    fun alphaMasksOverlap(a: ImageView, b: ImageView, alphaThreshold: Int = 1): Boolean {
        val locA = IntArray(2)
        val locB = IntArray(2)
        a.getLocationOnScreen(locA)
        b.getLocationOnScreen(locB)

        val left = maxOf(locA[0], locB[0])
        val top = maxOf(locA[1], locB[1])
        val right = minOf(locA[0] + a.width, locB[0] + b.width)
        val bottom = minOf(locA[1] + a.height, locB[1] + b.height)

        if (left >= right || top >= bottom) return false

        val width = right - left
        val height = bottom - top
        val bitmapA = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val bitmapB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        fun render(view: ImageView, screenX: Int, screenY: Int, bitmap: Bitmap) {
            val canvas = Canvas(bitmap)
            canvas.translate((screenX - left).toFloat(), (screenY - top).toFloat())
            view.draw(canvas)
        }

        render(a, locA[0], locA[1], bitmapA)
        render(b, locB[0], locB[1], bitmapB)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val alphaA = Color.alpha(bitmapA.getPixel(x, y))
                val alphaB = Color.alpha(bitmapB.getPixel(x, y))
                if (alphaA >= alphaThreshold && alphaB >= alphaThreshold) {
                    bitmapA.recycle()
                    bitmapB.recycle()
                    return true
                }
            }
        }

        bitmapA.recycle()
        bitmapB.recycle()
        return false
    }


//    private val isColliding: Boolean
//        get() {
//            val bunnyLeft = bunny!!.getX()
//            val bunnyRight = bunny!!.getX() + bunny!!.getWidth()
//            val bunnyTop = bunny!!.getY()
//            val bunnyBottom = bunny!!.getY() + bunny!!.getHeight()
//            val obsLeft = obstacle!!.getX()
//            val obsRight = obstacle!!.getX() + obstacle!!.getWidth()
//            val obsTop = obstacle!!.getY()
//            val obsBottom = obstacle!!.getY() + obstacle!!.getHeight()
//            return bunnyRight > obsLeft && bunnyLeft < obsRight && bunnyBottom > obsTop && bunnyTop < obsBottom
//        }
