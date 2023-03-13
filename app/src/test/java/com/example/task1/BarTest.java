package com.example.task1;

import static org.junit.Assert.*;

import android.graphics.RectF;

import junit.framework.TestCase;

import org.junit.Test;

public class BarTest extends TestCase {
    Bar bar = new Bar(1080,1920);

    @Test
    public void testGetRect() {
        assertEquals(bar.getRect().getClass(), RectF.class);

    }

    @Test
    public void testUpdateLeft(){
        float xCoord = 1080/2.8F;
        bar.setMovementState(bar.LEFT);
        xCoord = xCoord - 1080/60;
        bar.update(60);
        assertEquals(bar.getRect().left,xCoord);
        assertEquals(bar.getRect().right,xCoord + (1080/4));


    }
    public void testUpdateRight(){
        float xCoord = 1080/2.8F;
        bar.setMovementState(bar.RIGHT);
        bar.update(60);
        xCoord = xCoord + 1080 / 60;
        assertEquals(bar.getRect().left,xCoord);
        assertEquals(bar.getRect().right,xCoord + (1080/4));

    }

}