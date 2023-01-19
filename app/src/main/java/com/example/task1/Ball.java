package com.example.task1;

import android.graphics.RectF;
import java.util.Random;

public class Ball {

    private final RectF rectF;
    private float horizontalVelocity;
    private float verticalVelocity;
    private final float ballWidth;
    private final float ballHeight;

    public Ball(int screenWidth, int screenHeight){

        ballWidth = screenWidth / 30;
        ballHeight = ballWidth;
        verticalVelocity = screenHeight / 4;
        horizontalVelocity = verticalVelocity;
        rectF = new RectF();

    }
    public RectF getRect(){
        return rectF;
    }

    public void update(long fps){
        rectF.left = rectF.left + (horizontalVelocity / fps);
        rectF.top = rectF.top + (verticalVelocity / fps);
        rectF.right = rectF.left + ballWidth;
        rectF.bottom = rectF.top - ballHeight;
    }

    public void reverseVerticalVelocity(){
        verticalVelocity = -verticalVelocity;
    }

    public void reverseHorizontalVelocity(){
        horizontalVelocity = -horizontalVelocity;
    }


    public void setRandomHorizontalVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseHorizontalVelocity();
        }
    }

    public void clearObstacleY(float y){
        rectF.bottom = y;
        rectF.top = y - ballHeight;
    }


    public void clearObstacleX(float x){
        rectF.left = x;
        rectF.right = x + ballWidth;
    }


    public void reset(int x, int y){
        rectF.left = x / 2;
        rectF.top = y - 20;
        rectF.right = x / 2 + ballWidth;
        rectF.bottom = y - 30 - ballHeight;
    }





}
