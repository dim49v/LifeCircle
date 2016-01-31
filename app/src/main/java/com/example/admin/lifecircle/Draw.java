package com.example.admin.lifecircle;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Draw extends SurfaceView implements SurfaceHolder.Callback{
    int i, u, nomcir, dx, dy, centrelengh, kol=0;
    boolean poimk = false;
    boolean qw=false;
    public int getNum(int x, int y) {
        int col, num = -1;
        for (i = 0; i < drawThread.n; i++) {
            col = (int) Math.sqrt(Math.pow(drawThread.circle[i][1] - x, 2) + Math.pow(drawThread.circle[i][2] - y, 2));
            if (col < drawThread.circle[i][0]) {
                num = i;
            }
        }
        return num;
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (poimk == true){
                    drawThread.circle[nomcir][1] = (int)event.getX()+dx;
                    drawThread.circle[nomcir][2] = (int)event.getY()+dy;
                };
                break;
            case MotionEvent.ACTION_DOWN:
                qw = false;
                int circlenum = getNum((int) event.getX(), (int) event.getY());
                poimk = false;
                nomcir = -1;
                if (circlenum!=-1) {
                    poimk = true;
                    nomcir = circlenum;
                    dx = drawThread.circle[circlenum][1]-(int)event.getX();
                    dy = drawThread.circle[circlenum][2]-(int)event.getY();
                    qw = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (poimk == true){
                    poimk = false;
                    for (i=0;i<drawThread.n;i++){
                        if (i!=nomcir){
                            centrelengh = (int)Math.sqrt(Math.pow(Math.abs(drawThread.circle[i][1]-drawThread.circle[nomcir][1]),2)+Math.pow(Math.abs(drawThread.circle[i][2]-drawThread.circle[nomcir][2]),2));
                            if (centrelengh < drawThread.circle[i][0]+drawThread.circle[nomcir][0]){
                                drawThread.circle[nomcir][0]= (int) Math.sqrt(Math.pow(drawThread.circle[i][0],2)+Math.pow(drawThread.circle[nomcir][0],2));
                                drawThread.circle[i][0]=Color.BLACK;
                                drawThread.circle[i][3]=0;
                                drawThread.kol++;
                                i=drawThread.n;

                            }
                        }
                    }

                }
                break;
        }
        return true;
    }

    private DrawThread drawThread;
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

