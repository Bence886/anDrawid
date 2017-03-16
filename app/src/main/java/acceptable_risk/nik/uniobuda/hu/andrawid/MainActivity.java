package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //sensor variables
    private SensorManager sensorManager;
    double ax,ay,az; //sensor x, y, z Acceleration
    float CursorX =500, CursorY=1000; //needs to be updated to the middle of the screen asap
    float height, width; //View width, height
    float xVelocity=0, yVelocity=0;
    int div =10; //divide the phone movement
    Date Start;

    DrawingView drawingView;
    Button small_Button, medium_Button, large_Button, new_Button, save_Button, load_Button;
    float smallBrush, mediumBrush, largeBrush;
    GridView drawerList;
    RelativeLayout drawer;
    DrawerLayout drawerLayout;

    ArrayList<Color> drawerColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDrawerColors();

        //Get views from xml
        drawer = (RelativeLayout) findViewById(R.id.drawerPane);
        drawerList = (GridView) findViewById(R.id.drawerList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawingView = (DrawingView)findViewById(R.id.drawingView);
        drawingView.setBrushSize(mediumBrush);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawer.bringToFront();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //create new adapter for listView
        DrawerListAdapter adapter =new DrawerListAdapter(this, drawerColors);
        drawerList.setAdapter(adapter);

        //Color picker
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawingView.setColor(drawerColors.get(position).color);
                Toast.makeText(view.getContext(), drawerColors.get(position).text, Toast.LENGTH_SHORT).show();
            }
        });

        //brush size selector
        small_Button = (Button)findViewById(R.id.smallBrush);
        small_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrushSize(smallBrush);
                Toast.makeText(v.getContext(), "Small Button Selected", Toast.LENGTH_SHORT).show();
            }
        });

        medium_Button = (Button)findViewById(R.id.mediumBrush);
        medium_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrushSize(mediumBrush);
                Toast.makeText(v.getContext(), "Medium Button Selected", Toast.LENGTH_SHORT).show();
            }
        });

        large_Button = (Button)findViewById(R.id.largeBrush);
        large_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrushSize(largeBrush);
                Toast.makeText(v.getContext(), "Large Button Selected", Toast.LENGTH_SHORT).show();
            }
        });

        //new, save
        new_Button = (Button)findViewById(R.id.buttonNew);
        new_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder newDialog = new AlertDialog.Builder(v.getContext());
                newDialog.setTitle("New drawing");
                newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
                newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        drawingView.startNew();
                        CursorX=500;
                        CursorY=1000;
                        xVelocity=0;
                        yVelocity=0;
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialog.show();
            }
        });
        save_Button = (Button)findViewById(R.id.buttonSave);
        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(v.getContext());
                saveDialog.setTitle("Save drawing");
                saveDialog.setMessage("Save drawing to device Gallery?");
                saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        drawingView.setDrawingCacheEnabled(true);
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), drawingView.getDrawingCache(),
                                UUID.randomUUID().toString()+".png", "drawing");
                        if(imgSaved!=null){
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            savedToast.show();
                        }
                        else{
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        drawingView.destroyDrawingCache();
                    }
                });
                saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                saveDialog.show();
            }
        });
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private void createDrawerColors() {
        drawerColors = new ArrayList<Color>();
        //add colors
        drawerColors.add(new Color("Black", "#000000"));
        drawerColors.add(new Color("White", "#FFFFFF"));
        drawerColors.add(new Color("Red", "#F44336"));
        drawerColors.add(new Color("Pink", "#E91E63"));
        drawerColors.add(new Color("Purple", "#9C27B0"));
        drawerColors.add(new Color("Deep Purple", "#673AB7"));
        drawerColors.add(new Color("Indigo", "#3F51B5"));
        drawerColors.add(new Color("Blue", "#2196F3"));
        drawerColors.add(new Color("Light Blue", "#03A9F4"));
        drawerColors.add(new Color("Cyan", "#00BCD4"));
        drawerColors.add(new Color("Teal", "#009688"));
        drawerColors.add(new Color("Green", "#4CAF50"));
        drawerColors.add(new Color("Light Green", "#8BC34A"));
        drawerColors.add(new Color("Lime", "#CDDC39"));
        drawerColors.add(new Color("Yellow", "#FFEB3B"));
        drawerColors.add(new Color("Amber", "#FFC107"));
        drawerColors.add(new Color("Orange", "#FF9800"));
        drawerColors.add(new Color("Deep Orange", "#FF5722"));
        drawerColors.add(new Color("Brown", "#795548"));
        drawerColors.add(new Color("Grey", "#9E9E9E"));
        drawerColors.add(new Color("Blue Grey", "#607D8B"));

        //create brushes
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        //get the window properties (hopefully they are set by this time)
        height=drawingView.getHeight();
        width=drawingView.getWidth();
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

                xVelocity=(float)ax*Elapsed;
                yVelocity=(float)ax*Elapsed;

                //calculate X/Y Distance moved
                //float newX = (CursorX + xVelocity/div);
                //float newY = (CursorY + yVelocity/div);

                float newX=0;
                float newY=0;

                if (xVelocity>0)
                    newX=CursorX+div;
                if (xVelocity<0)
                    newX=CursorX-div;
                if (yVelocity>0)
                    newX=CursorY+div;
                if (yVelocity<0)
                    newX=CursorY-div;

                //if the cursor stays in the window
                if (newX >= 0 && newY >= 0 && newX <= width && newY <= height){
                    drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                    //set new cursor position
                    CursorX=newX;
                    CursorY=newY;
                }else {
                    if (newX<0) newX=0;
                    if (newX>width) newX=width;
                    if (newY<0) newY=0;
                    if (newY>height) newY=height;
                    drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                    CursorX=newX;
                    CursorY=newY;
                }
                Start = new Date(); //set new time
            }else if (Elapsed > 100)
            {
                Start = new Date();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}