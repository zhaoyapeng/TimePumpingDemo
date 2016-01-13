package com.jusfoun.timepumpingdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

    private TimePumpingView time;
    private CustomeScrollView scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time= (TimePumpingView) findViewById(R.id.time);
        scroll= (CustomeScrollView) findViewById(R.id.scroll);
        time.setListener(new TimePumpingView.OnTouchChangeListener() {
            @Override
            public void touchUp(float currentY, float moveY) {
                scroll.pointUp(-currentY,-moveY,false);
            }

            @Override
            public void touchMove(float moveY) {
                scroll.pointMove(-moveY,false);
            }

            @Override
            public void addData(int count) {
                scroll.refresh(18);
            }
        });

        scroll.setListener(new CustomeScrollView.OnScrollTouchListener() {
            @Override
            public void touchUp(float currentY, float moveY) {
                time.pointUp(-currentY, -moveY,false);
            }

            @Override
            public void touchMove(float moveY) {
                time.pointMove(-moveY,false);
            }
        });
    }
}
