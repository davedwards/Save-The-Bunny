package com.example.savethebunny;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView bunny, obstacle;
    private TextView scoreText;
    private Button startButton;
    private RelativeLayout gameArea;
    private Handler handler = new Handler();
    private Runnable gameRunnable;
    private int score = 0;
    private boolean isJumping = false;
    private boolean isGameRunning = false;
    private float bunnyY, bunnyStartY;
    private float obstacleX, obstacleStartX;
    private int jumpHeight = 250;
    private int jumpDuration = 300;
    private int obstacleSpeed = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bunny = findViewById(R.id.bunny);
        obstacle = findViewById(R.id.obstacle);
        scoreText = findViewById(R.id.score_text);
        startButton = findViewById(R.id.start_button);
        gameArea = findViewById(R.id.game_area);

        bunny.post(() -> {
            bunnyStartY = bunny.getY();
            bunnyY = bunnyStartY;
        });
        obstacle.post(() -> {
            obstacleStartX = gameArea.getWidth();
            resetObstacle();
        });

        startButton.setOnClickListener(v -> startGame());
        gameArea.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && isGameRunning) {
                jump();
            }
            return true;
        });
    }

    private void startGame() {
        score = 0;
        isGameRunning = true;
        startButton.setVisibility(View.GONE);
        scoreText.setText("Score: 0");
        bunny.setY(bunnyStartY);
        resetObstacle();
        handler.post(gameRunnable = new Runnable() {
            @Override
            public void run() {
                updateGame();
                if (isGameRunning) handler.postDelayed(this, 16);
            }
        });
    }

    private void endGame() {
        isGameRunning = false;
        handler.removeCallbacks(gameRunnable);
        startButton.setText("Restart Game");
        startButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
    }

    private void jump() {
        if (isJumping) return;
        isJumping = true;
        bunny.animate().y(bunnyStartY - jumpHeight).setDuration(jumpDuration).withEndAction(() ->
            bunny.animate().y(bunnyStartY).setDuration(jumpDuration).withEndAction(() -> isJumping = false)
        ).start();
    }

    private void updateGame() {
        // Move obstacle
        obstacleX -= obstacleSpeed;
        if (obstacleX + obstacle.getWidth() < 0) {
            score++;
            scoreText.setText("Score: " + score);
            resetObstacle();
        }
        obstacle.setX(obstacleX);
        // Collision detection
        if (isColliding()) {
            endGame();
        }
    }

    private void resetObstacle() {
        obstacleX = gameArea.getWidth();
        obstacle.setY(bunnyStartY);
        obstacle.setX(obstacleX);
    }

    private boolean isColliding() {
        float bunnyLeft = bunny.getX();
        float bunnyRight = bunny.getX() + bunny.getWidth();
        float bunnyTop = bunny.getY();
        float bunnyBottom = bunny.getY() + bunny.getHeight();
        float obsLeft = obstacle.getX();
        float obsRight = obstacle.getX() + obstacle.getWidth();
        float obsTop = obstacle.getY();
        float obsBottom = obstacle.getY() + obstacle.getHeight();
        return bunnyRight > obsLeft && bunnyLeft < obsRight && bunnyBottom > obsTop && bunnyTop < obsBottom;
    }
}