package acceptable_risk.nik.uniobuda.hu.andrawid;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
=======
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
>>>>>>> sensor
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //sensor variables
    private SensorManager sensorManager;
    double ax,ay,az; //sensor x, y, z Acceleration
    float CursorX =500, CursorY=1000; //needs to be updated to the middle of the screen asap
    float height, width; //View width, height
    int div =1000; //divide the phone movement
    Date Start; //


    DrawingView drawingView;
    Button small_Button, medium_Button, large_Button, new_Button, save_Button, load_Button;
    float smallBrush, mediumBrush, largeBrush;
    GridView drawerList;
    RelativeLayout drawer;
    DrawerLayout drawerLayout;

    ArrayList<ViewHolder> drawerColors;
    private int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
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

        //new, dave, load
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

            }
        });
        load_Button = (Button)findViewById(R.id.loadButton);
        load_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createDrawerColors() {
        drawerColors = new ArrayList<ViewHolder>();
        //add colors
        drawerColors.add(new ViewHolder("Black", "#000000"));
        drawerColors.add(new ViewHolder("White", "#FFFFFF"));
        drawerColors.add(new ViewHolder("Red", "#F44336"));
        drawerColors.add(new ViewHolder("Pink", "#E91E63"));
        drawerColors.add(new ViewHolder("Purple", "#9C27B0"));
        drawerColors.add(new ViewHolder("Deep Purple", "#673AB7"));
        drawerColors.add(new ViewHolder("Indigo", "#3F51B5"));
        drawerColors.add(new ViewHolder("Blue", "#2196F3"));
        drawerColors.add(new ViewHolder("Light Blue", "#03A9F4"));
        drawerColors.add(new ViewHolder("Cyan", "#00BCD4"));
        drawerColors.add(new ViewHolder("Teal", "#009688"));
        drawerColors.add(new ViewHolder("Green", "#4CAF50"));
        drawerColors.add(new ViewHolder("Light Green", "#8BC34A"));
        drawerColors.add(new ViewHolder("Lime", "#CDDC39"));
        drawerColors.add(new ViewHolder("Yellow", "#FFEB3B"));
        drawerColors.add(new ViewHolder("Amber", "#FFC107"));
        drawerColors.add(new ViewHolder("Orange", "#FF9800"));
        drawerColors.add(new ViewHolder("Deep Orange", "#FF5722"));
        drawerColors.add(new ViewHolder("Brown", "#795548"));
        drawerColors.add(new ViewHolder("Grey", "#9E9E9E"));
        drawerColors.add(new ViewHolder("Blue Grey", "#607D8B"));

        //create brushes
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
    }
/*
    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }*/
}
=======
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
>>>>>>> sensor
