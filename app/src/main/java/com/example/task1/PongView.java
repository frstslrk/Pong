package com.example.task1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class PongView extends SurfaceView implements Runnable {
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean isPlaying;
    boolean isPaused = true;
    Canvas canvas;
    Paint paint;
    long FPS;
    int screenWidth;
    int screenHeight;
    Bar bar;
    Ball ball;

    public PongView(Context context, int width, int height) {

        super(context);
        screenWidth = width;
        screenHeight = height;
        surfaceHolder = getHolder();
        paint = new Paint();
        bar = new Bar(screenWidth, screenHeight);
        ball = new Ball(screenWidth, screenHeight);
        setupAndRestart();

    }


    @Override
    public void run() {
        while (isPlaying) {

            long startFrameTime = System.currentTimeMillis();

            if (!isPaused) {
                update();
            }

            draw();
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                FPS = 1000 / timeThisFrame;
            }

        }

    }

    public void update() {

        bar.update(FPS);
        ball.update(FPS);

        if (RectF.intersects(bar.getRect(), ball.getRect())) {
            ball.setRandomHorizontalVelocity();
            ball.reverseVerticalVelocity();
            ball.clearObstacleY(bar.getRect().top - 2);
        }

        if (ball.getRect().bottom > screenHeight) {

            isPaused = true;
            bar = new Bar(screenWidth, screenHeight);
            ball = new Ball(screenWidth, screenHeight);
            this.setupAndRestart();

        }

        if (ball.getRect().top < 0) {
            ball.reverseVerticalVelocity();
            ball.clearObstacleY(20);
        }

        if (ball.getRect().left < 0) {
            ball.reverseHorizontalVelocity();
            ball.clearObstacleX(2);

        }

        if (ball.getRect().right > screenWidth) {
            ball.reverseHorizontalVelocity();
            ball.clearObstacleX(screenWidth - 22);

        }

    }

    public void setupAndRestart() {

        ball.reset(screenWidth, screenHeight);

    }


    public void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 120, 197, 87));
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(bar.getRect(), paint);
            canvas.drawRect(ball.getRect(), paint);
            paint.setColor(Color.argb(255, 255, 255, 255));
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }


    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                isPaused = false;
                break;
        }
        return true;
    }


    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < 0.0001) {
            bar.setMovementState(bar.RIGHT);
        } else {
            bar.setMovementState(bar.LEFT);
        }

    }

}
