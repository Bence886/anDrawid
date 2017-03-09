package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //sensor variables
    private SensorManager sensorManager;
    double ax,ay,az; //sensor x, y, z Acceleration
    float CursorX =500, CursorY=1000; //needs to be updated to the middle of the screen asap
    float height, width; //View width, height
    int div =1000; //divide the phone movement
    Date Start; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create sensor listener
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //get the window properties (hopefully they are set by this time)
        //height=<ourDrawingView>.getHeight();
        //width=<ourDrawingView>.getWidth();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Sensor event listener register
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister sensor listener at app pause
        sensorManager.unregisterListener(this);
    }

    //---!!---
    //This method needs cleanup, optimalization and corrections
    //---!!---
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Start==null)
        {//for the firs time
            Start=new Date(System.currentTimeMillis());
        }
        float Elapsed = new Date().getTime() - Start.getTime(); //calculate time between event calls

        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){ //get LinearAcceleration values
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];

            //if the acceleration is faster than 0.5m/s^2
            if ((Math.abs(ax)>0.5 || Math.abs(ay)>0.5) && Elapsed<1000) {
                //calculate X/Y Distance moved
                float newX = (float) (CursorX + (0.5 * ax * Math.pow(Elapsed, 2)) / div);
                float newY = (float) (CursorY + (0.5 * ay * Math.pow(Elapsed, 2)) / div);
                //if the cursor stays in the window
                if (newX >= 0 && newY >= 0 && newX <= width && newY <= height){
                    //---------------draw from CursorX/Y to newX/Y
                    //set new cursor position
                    CursorX=newX;
                    CursorY=newY;
                }else {
                    if (newX<0) newX=0;
                    if (newX>width) newX=width;
                    if (newY<0) newY=0;
                    if (newY>height) newY=height;
                    //----------------draw from CursorX/Y to newX/Y
                    CursorX=newX;
                    CursorY=newY;
                }
                Start=new Date(); //set new time
            }else if (Elapsed > 50)
            {
                Start = new Date();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}