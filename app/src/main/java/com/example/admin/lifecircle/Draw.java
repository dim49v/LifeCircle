package com.example.admin.lifecircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Admin on 17.01.2016.
 */

public class Draw extends SurfaceView implements SurfaceHolder.Callback{
    int i, u, nomcir, dx, dy, centrelengh, kol=0;
    boolean poimk = false;
    boolean qw=false;
    private DrawThread drawThread;

    public int getColor(int x, int y) {
        int col = getDrawingCache().getPixel(x, y);
        return col;
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (poimk == true){
                    drawThread.circle[nomcir][1] = (int)event.getX()+dx;
                    drawThread.circle[nomcir][2] = (int)event.getY()+dx;
                };
                break;
            case MotionEvent.ACTION_DOWN:
                qw = false;
                int colore1 = getColor((int) event.getX(), (int) event.getY());
                if (colore1!=Color.BLACK) {
                    for (i = 0; i >= drawThread.n; i++) {
                        if (drawThread.circle[i][3]==colore1){
                            poimk = true;
                            nomcir = i;
                            dx = drawThread.circle[i][1]-(int)event.getX();
                            dy = drawThread.circle[i][2]-(int)event.getY();
                            qw = true;
                        }
                    }
                    if (qw==false){
                        poimk=false;
                    }
                }
                destroyDrawingCache();
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                if (poimk == true){
                    for (i=0;i<drawThread.n;i++){
                        if (i!=nomcir){
                            centrelengh = (int)Math.sqrt(Math.pow(Math.abs(drawThread.circle[i][1]-drawThread.circle[nomcir][1]),2)+Math.pow(Math.abs(drawThread.circle[u][2]-drawThread.circle[nomcir][2]),2));
                            if (centrelengh < drawThread.circle[u][0]+drawThread.circle[nomcir][0]){
                                drawThread.circle[nomcir][0]= (int) Math.sqrt(Math.pow(drawThread.circle[i][0],2)+Math.pow(drawThread.circle[nomcir][0],2));
                                drawThread.circle[i][0]=Color.BLACK;
                                drawThread.circle[i][3]=0;
                                kol++;
                                if (kol==drawThread.n-1){
                                    ////
                                }
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    public Draw(Context context) {
        super(context);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            }
            catch (Exception e) {

            }
        }
    }
}

