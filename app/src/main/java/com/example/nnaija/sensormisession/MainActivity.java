package com.example.nnaija.sensormisession;



import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import CustomControl.SpaceView;
import module.Astre;
import module.AstreWebQuery;
import module.DbWorker;
import module.IActivityUIUpdateWebQuery;

public class MainActivity extends AppCompatActivity implements SensorEventListener, IActivityUIUpdateWebQuery {


    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView tvSensorX;
    private  TextView tvPositionX;
    private TextView tvSensorY;
    private TextView tvSensorZ;
    private TextView tvSpeed;
    private RelativeLayout layout;

    private SpaceView spaceView;


    private ImageView vaisseau;



    private int x;
    private  int y;
    private final int deviceWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
    private  int layoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        tvSensorX=(TextView)findViewById(R.id.tvSensorX);
        tvPositionX=(TextView)findViewById(R.id.tvPostionX);
        tvSensorY=(TextView)findViewById(R.id.tvSensorY);
        tvSensorZ=(TextView)findViewById(R.id.tvSensorZ);
        tvSpeed=(TextView)findViewById(R.id.tvSpeed);
        layout=(RelativeLayout) findViewById(R.id.layoutRl);
        vaisseau=(ImageView)findViewById(R.id.ivSpaceShip);

        x=(int)vaisseau.getX();
        y=(int)vaisseau.getY();


        layoutHeight=Resources.getSystem().getDisplayMetrics().heightPixels-(getStatusBarHeight()+getActionBarSize());
        DbWorker worker =new DbWorker(this,new AstreWebQuery());
        worker.execute();

    }

    @Override
    protected void onResume(){
        super.onResume();
//        if(!sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)){
//            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
//        }





    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        float ax=((float)event.values[0])*2;
        float ay=((float)event.values[1])*2;

        tvSensorX.setText("X="+String.valueOf(x));
        tvSensorY.setText("Y="+String.valueOf(y));

        x=(int)(x-(ax*2));
        y=(int)(y+(ay*2));
        if(x<0){
            x=0;
        }
        if(x>deviceWidth-vaisseau.getWidth()){
            x=deviceWidth-vaisseau.getWidth();
        }
        if(y<0){
            y=0;
        }
        if(y>layoutHeight-vaisseau.getHeight()){
            y=layoutHeight-vaisseau.getHeight();
        }
        tvSensorZ.setText("y="+String.valueOf(y));
        tvPositionX.setText("x= "+String.valueOf(x));
        vaisseau.setX(x);
        vaisseau.setY(y);

        spaceView.setVaisseauPostionY(y);
        spaceView.setVaisseauPostionX(x);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    @Override
    public void updateUI(Object o) {

        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);

        List<Astre> listeAstre=(List<Astre>)o;

        spaceView = new SpaceView(this,null,listeAstre);
        layout.addView(spaceView);


    }

    public  int getActionBarSize(){
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
