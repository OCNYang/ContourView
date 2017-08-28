package com.ocnyang.contuorviewdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ocnyang.contourview.ContuorView;

public class ContuorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contuor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initBeachContuorView();
        initCustomContuorView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/OCNYang/ContuorView");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    /**
     * Customize the coordinates of the anchor to control the area to be drawn。
     */
    private void initCustomContuorView() {
        ContuorView contuorViewCustom = (ContuorView) findViewById(R.id.contuorview_custom);
        int width = getWidth();
        int hight = 400;
        int[] ints = {width / 2, 0, width, hight / 2, width / 2, hight, 0, hight / 2};
        int[] intArr = new int[]{width / 2, hight / 4, width / 4 * 3, hight / 2, width / 2, hight / 4 * 3, width / 4, hight / 2};
        contuorViewCustom.setPoints(ints, intArr);
        contuorViewCustom.setShaderStartColor(getResources().getColor(R.color.startcolor));
        contuorViewCustom.setShaderEndColor(getResources().getColor(R.color.endcolor));
        contuorViewCustom.setShaderMode(ContuorView.SHADER_MODE_RADIAL);
//        contuorViewCustom.invalidate();
    }

    /**
     * Controls the color of the drawing.
     */
    private void initBeachContuorView() {
        ContuorView contuorViewBeach = ((ContuorView) findViewById(R.id.contuorview_beach));

        RadialGradient radialGradient = new RadialGradient(
                0, 0,
                4000,
                getResources().getColor(R.color.startcolor),
                getResources().getColor(R.color.endcolor),
                Shader.TileMode.CLAMP);
        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), 400,
                Color.argb(30, 255, 255, 255), Color.argb(90, 255, 255, 255),
                Shader.TileMode.REPEAT);
        contuorViewBeach.setShader(radialGradient, linearGradient);
    }

    public int getWidth() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        return width;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
