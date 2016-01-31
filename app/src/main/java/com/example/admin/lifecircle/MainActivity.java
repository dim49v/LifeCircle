package com.example.admin.lifecircle;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.Menu;
import android.view.MenuItem;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(new Draw(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class DrawThread extends Thread{
    private boolean runFlag = false;
    Canvas canvas = new Canvas();
    Paint paint = new Paint();
    final SecureRandom random = new SecureRandom();
    int i=0;
    int u=0;
    float x;
    float y;
    float x0;
    float y0;
    int xnew;
    int ynew;
    int n = 0;
    int circle[][] = new int[10][4];
    int centrelengh;
    int colors[] = { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.CYAN, Color.LTGRAY, Color.BLACK, Color.DKGRAY };
    int xx=0;
    int yy=0;
    int r, g ,b, a;
    int kol=0;
    private SurfaceHolder surfaceHolder;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {

        canvas = null;
        canvas = surfaceHolder.lockCanvas(null);
        synchronized (surfaceHolder) {canvas.drawColor(Color.WHITE);}

        n = (Math.abs(random.nextInt() % 4))+4;
        for(i=0;i<n;i++){
            circle[i][3]=colors[i];
        }
        boolean mes = false;
        a=0;
        r=255;
        g=0;
        b=0;
        x=100;
        x0=100;
        y=100;
        y0=100;
        xx = canvas.getHeight();
        yy = canvas.getWidth();
        for (i = 0; i <= n; i++) {
            circle[i][3] = colors[i];
            while (circle[i][1] == 0) {
                circle[i][0] = 40 + (Math.abs(random.nextInt()%100));
                ynew = Math.abs(random.nextInt() % canvas.getHeight());
                xnew = Math.abs(random.nextInt() % canvas.getWidth());
                if (i>0){
                    for (u=0;u<i;u++) {
                       centrelengh = (int)Math.sqrt(Math.pow(Math.abs(circle[u][1]-xnew),2)+Math.pow(Math.abs(circle[u][2]-ynew),2));
                        if (centrelengh < circle[u][0]+circle[i][0]) {
                            mes = true;
                        }
                    }
                    if (!mes) {
                        circle[i][1] = xnew;
                        circle[i][2] = ynew;
                    }
                    mes = false;

                }else{
                     circle[i][1] = xnew;
                     circle[i][2] = ynew;
                }
            }
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.FILL);
        while (runFlag) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.WHITE);
                    for (i=0;i<n;i++){
                        if (circle[i][0]!=0){
                            paint.setColor(circle[i][3]);
                            canvas.drawCircle(circle[i][1],circle[i][2],circle[i][0],paint);
                        }
                    }
                    paint.setColor(Color.BLACK);
                    canvas.drawText(String.valueOf(xx), 10, 20, paint);
                    canvas.drawText(String.valueOf(yy),10,40,paint);
                    canvas.drawText(String.valueOf(n),10,60,paint);
                    canvas.drawText(String.valueOf(kol),10,80,paint);

                }


            }
            catch(Exception e){}
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
