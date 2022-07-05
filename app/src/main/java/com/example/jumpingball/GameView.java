package com.example.jumpingball;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private int state = 1;
    private MainThread thread;
    public static int gapHeight = 500;
    public int score = 0;
    public int k = 0;
    public int highScore = 0;
    public String text = "Score = " + String.valueOf(score);
    public String text4 = "";
    public String text1 = "";
    public String text2 = "";
    public static int velocity = 15;
    private CharacterSprite characterSprite;
    private ObstacleSprite obs1,obs2,obs3,obs4,obs5,obs6,obs7,obs8,obs9,obs10;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public GameView (Context context) {

        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {


        if (event.getAction() == MotionEvent.ACTION_UP)
            characterSprite.yVelocity = 8;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            characterSprite.yVelocity = -8;
            return true;
        }
        /*
        if (state == 1)
            characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 15);*/
        if (state == 0 && (event.getX() > 700 && event.getX() < 1500) && (event.getY() > 700 && event.getY() < 850)){
            characterSprite.y = 420;
            characterSprite.x = 200;
            obs1.yY = 0;
            obs1.xX = 2000;
            obs2.yY = -150;
            obs2.xX = 3500;
            obs3.yY = 150;
            obs3.xX = 5000;
            obs4.yY = 200;
            obs4.xX = 6500;
            obs5.yY = -100;
            obs5.xX = 8000;
            obs6.yY = 150;
            obs6.xX = 9500;
            obs7.yY = -200;
            obs7.xX = 11000;
            obs8.yY = 200;
            obs8.xX = 12500;
            obs9.yY = 100;
            obs9.xX = 14000;
            obs10.yY = -100;
            obs10.xX = 15500;
            state = 1;
            velocity = 20;
            score = 0;
            text = "Score = " + String.valueOf(score);
            text1 = "";
            text2 = "";
            text4 = "";
            characterSprite.yVelocity = 8;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        makeLevel();
        thread.setRunning(true);
        thread.start();

    }
    private void makeLevel() {
        characterSprite = new CharacterSprite
                (getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ball3), 200, 200));
        Bitmap bmp;
        Bitmap bmp2;
        int y;
        int x;
        bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pipe_up), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        bmp2 = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pipe_up), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        obs1 = new ObstacleSprite(bmp, bmp2, 2000, 0);
        obs2 = new ObstacleSprite(bmp, bmp2, 3500, -150);
        obs3 = new ObstacleSprite(bmp, bmp2, 5000, 150);
        obs4 = new ObstacleSprite(bmp, bmp2, 6500, 200);
        obs5 = new ObstacleSprite(bmp, bmp2, 8000, -100);
        obs6 = new ObstacleSprite(bmp, bmp2, 9500, 150);
        obs7 = new ObstacleSprite(bmp, bmp2, 11000, -200);
        obs8 = new ObstacleSprite(bmp, bmp2, 12500, 200);
        obs9 = new ObstacleSprite(bmp, bmp2, 14000, 100);
        obs10 = new ObstacleSprite(bmp, bmp2, 15500, -100);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(Canvas canvas) {

        logic(canvas);
        characterSprite.update();
        obs1.update();
        obs2.update();
        obs3.update();
        obs4.update();
        obs5.update();
        obs6.update();
        obs7.update();
        obs8.update();
        obs9.update();
        obs10.update();
    }
    @Override
    public void draw (Canvas canvas) {

        super.draw(canvas);
        if (state == 0){
            canvas.drawRGB(255,255,255);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText(text, 100, 100, paint);
            paint.setTextSize(200);
            canvas.drawText(text1, 600, 420, paint);
            paint.setTextSize(100);
            canvas.drawText(text4, 1450, 100, paint);
            Paint paint2 = new Paint();
            paint2.setColor(Color.BLACK);
            paint2.setStrokeWidth(10);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(Color.BLUE);
            canvas.drawRect(700, 850, 1500, 700, paint2);
            paint.setColor(Color.WHITE);
            paint.setTextSize(100);
            canvas.drawText(text2, 865, 800, paint);
            paint.setColor(Color.BLACK);
        }
        else if (canvas != null) {
            canvas.drawRGB(255,255,255);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText(text, 100, 100, paint);
            paint.setTextSize(200);
            canvas.drawText(text1, 600, 420, paint);
            paint.setTextSize(50);
            canvas.drawText(text2, 750, 520, paint);
            paint.setTextSize(100);
            canvas.drawText(text4, 1450, 100, paint);

            characterSprite.draw(canvas);
            obs1.draw(canvas);
            obs2.draw(canvas);
            obs3.draw(canvas);
            obs4.draw(canvas);
            obs5.draw(canvas);
            obs6.draw(canvas);
            obs7.draw(canvas);
            obs8.draw(canvas);
            obs9.draw(canvas);
            obs10.draw(canvas);
        }

    }
    public void logic(Canvas canvas) {

        List<ObstacleSprite> obs = new ArrayList<>();
        obs.add(obs1);
        obs.add(obs2);
        obs.add(obs3);
        obs.add(obs4);
        obs.add(obs5);
        obs.add(obs6);
        obs.add(obs7);
        obs.add(obs8);
        obs.add(obs9);
        obs.add(obs10);

        if (score == 10){
            resetLevel(canvas);
        }
        for (int i = 0; i < obs.size(); i++) {
            //Detect if the character is touching one of the pipes
            if ((characterSprite.x - 200 > obs.get(i).xX + 250)
                    && !(characterSprite.y < obs.get(i).yY + (screenHeight / 2) - (gapHeight / 2) && characterSprite.x + 200 > obs.get(i).xX && characterSprite.x < obs.get(i).xX + 500)
                    && !(characterSprite.y + 200 > (screenHeight / 2) + (gapHeight / 2) + obs.get(i).yY && characterSprite.x + 200 > obs.get(i).xX && characterSprite.x < obs.get(i).xX + 500)
                    && !(characterSprite.y + 200 < 0) && !(characterSprite.y > screenHeight)){
                score = i + 1;
                text = "Score = " + String.valueOf(score);
                velocity = 15 + (i+1)*5;
            }
            if (characterSprite.y < obs.get(i).yY + (screenHeight / 2) - (gapHeight / 2) && characterSprite.x + 200 > obs.get(i).xX && characterSprite.x < obs.get(i).xX + 500) {
                resetLevel(canvas);
            } else if (characterSprite.y + 200 > (screenHeight / 2) + (gapHeight / 2) + obs.get(i).yY && characterSprite.x + 200 > obs.get(i).xX && characterSprite.x < obs.get(i).xX + 500) {
                resetLevel(canvas);
            }
        }

        //Detect if the character has gone off the bottom or top of the screen
        if (characterSprite.y + 200 < 0) {
            resetLevel(canvas);
        }
        if (characterSprite.y > screenHeight) {
            resetLevel(canvas); }
    }

    public void resetLevel(Canvas canvas) {

        state = 0;
        if (score > highScore){
            highScore = score;
        }
        text = "Your Score = " + String.valueOf(score);
        text1 = "Game Over";
        text2 = "Play Again";
        text4 = "High Score = " + String.valueOf(highScore);
        characterSprite.y = 20000;
        characterSprite.x = 20000;
        obs1.yY = 10000;
        obs1.xX = 10000;
        obs2.yY = 10000;
        obs2.xX = 10000;
        obs3.yY = 10000;
        obs3.xX = 10000;
        obs4.yY = 10000;
        obs4.xX = 10000;
        obs5.yY = 10000;
        obs5.xX = 10000;
        obs6.yY = 10000;
        obs6.xX = 10000;
        obs7.yY = 10000;
        obs7.xX = 10000;
        obs8.yY = 10000;
        obs8.xX = 10000;
        obs9.yY = 10000;
        obs9.xX = 10000;
        obs10.yY = 10000;
        obs10.xX = 10000;
        draw(canvas);
    }
}
