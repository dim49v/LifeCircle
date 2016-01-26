package com.example.admin.lifecircle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.lifecircle.R;

import java.sql.Time;
import java.util.Random;

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
    int devw, devh;
    private boolean runFlag = false;
    Canvas canvas = new Canvas();
    Paint paint = new Paint();
    //Paint paint1 = new Paint();
    final Random random = new Random();
    int ct = 0;
    int i=0;
    int u=0;
    //long c = System.currentTimeMillis()/1000;
    float x;
    float y;
    float x0;
    float y0;
    float dy;
    float dx;
    int xnew;
    int ynew;
    int n = 0;
    int circle[][] = new int[10][4];
    int centrelengh;
    int colors[] = { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.CYAN, Color.LTGRAY, Color.WHITE, Color.DKGRAY };

    int r, g ,b, a;
    private SurfaceHolder surfaceHolder;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        n = random.nextInt(3);
        n=n+5;
        for(i=0;i<n;i++){
            circle[i][3]=colors[i];
        }
        devh = 700;
        devw = 1200;

        a=0;
        r=255;
        g=0;
        b=0;
        x=100;
        x0=100;
        y=100;
        y0=100;
        for (i = 0; i <= n; i++) {
            circle[i][0] = random.nextInt(100) + 50;
            circle[i][3] = colors[random.nextInt(7)];
            while (circle[i][1] == 0) {
                xnew = random.nextInt(devw);
                ynew = random.nextInt(devh);
                for (u=0;u<i;u++) {
                    centrelengh = (int)Math.sqrt(Math.pow(Math.abs(circle[u][1]-xnew),2)+Math.pow(Math.abs(circle[u][2]-ynew),2));
                    if (centrelengh > circle[u][0]+circle[i][0]) {
                        circle[i][1] = xnew;
                        circle[i][2] = ynew;
                    }
                }
            }
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        while (runFlag) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.BLACK);
                    for (i=0;i<n;i++){
                        if (circle[i][0]!=0){
                            paint.setColor(circle[i][3]);
                            canvas.drawCircle(circle[i][1],circle[i][2],circle[i][0],paint);
                        }
                    }
                    //paint.setColor(Color.rgb(r, g, b));
                    //canvas.drawCircle(x, y, 100, paint);

                    i++;

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
