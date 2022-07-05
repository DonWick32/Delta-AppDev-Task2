package com.example.jumpingball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ObstacleSprite {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY;
    private int xVelocity = 10;
    private int screenHeight =
            Resources.getSystem().getDisplayMetrics().heightPixels;

    public ObstacleSprite (Bitmap bmp, Bitmap bmp2, int x, int y) {
        image = bmp;
        image2 = bmp2;
        yY = y;
        xX = x;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xX, -(GameView.gapHeight / 2) + yY, null);
        canvas.drawBitmap(image2,xX, ((screenHeight / 2) + (GameView.gapHeight / 2)) + yY, null);

    }
    public void update() {
        xX -= GameView.velocity;
    }

}
