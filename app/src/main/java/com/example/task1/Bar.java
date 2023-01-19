package com.example.task1;

import android.graphics.RectF;

public class Bar {
    private final RectF rectF;
    private final float barLength;
    private final float barHeight;
    private float xCoord;
    private final float yCoord;
    private final float barSpeed;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int barDirection = STOPPED;
    private final int screenWidth;
    private final int screenLength;
    public Bar(int width, int length){

        screenWidth = width;
        screenLength = length;
        barLength = screenWidth / 4;
        barHeight = screenLength / 12;
        xCoord = (float) (screenWidth / 2.8);
        yCoord = screenLength - 20;
        rectF = new RectF(xCoord, yCoord, xCoord + barLength, yCoord + barHeight);
        barSpeed = screenWidth;

    }

    public RectF getRect(){
        return rectF;
    }

    public void setMovementState(int state){
        barDirection = state;
    }

    public void update(long fps){

        if(barDirection == LEFT){
            xCoord = xCoord - barSpeed / fps;
        }

        if(barDirection == RIGHT){
            xCoord = xCoord + barSpeed / fps;
        }

        if(rectF.left < 0){ xCoord = 0; } if(rectF.right > screenWidth){
            xCoord = screenWidth -(rectF.right - rectF.left);
        }

        rectF.left = xCoord;
        rectF.right = xCoord + barLength;
    }

}