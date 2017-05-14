package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Date;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    //sensor variables
    private SensorManager sensorManager;
    float ax,ay,az; //sensor x, y, z Acceleration
    float CursorX =500, CursorY=1000; //needs to be updated to the middle of the screen asap
    float height, width; //View width, height
    float xVelocity=0, yVelocity=0;
    float elozoX = 0;
    float elozoY = 0;
    int nX = 0;
    int nY = 0;
    float sumX = 0;
    float sumY = 0;
    boolean touched = false;
    Date Start;

    DrawingView drawingView;
    Button small_Button, medium_Button, large_Button, new_Button, save_Button, newColor_Button;
    ImageButton DrawerOpen_Button;
    float smallBrush, mediumBrush, largeBrush;
    GridView drawerList;
    RelativeLayout drawer;
    DrawerLayout drawerLayout;
    String model;

    ArrayList<MyColor> drawerMyColors;
    FileReadWrite fileReadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileReadWrite = new FileReadWrite(getBaseContext());
        createDrawerColors();
        //Get views from xml
        drawer = (RelativeLayout) findViewById(R.id.drawerPane);
        drawerList = (GridView) findViewById(R.id.drawerList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawingView = (DrawingView)findViewById(R.id.drawingView);
        drawingView.setBrushSize(mediumBrush);
        model = Build.MODEL;

        drawingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getX();
                float y = event.getY();
                int eventaction = event.getAction();

                switch (eventaction)
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        touched = true;
                    }break;

                    case MotionEvent.ACTION_UP:
                    {
                        touched = false;
                    }break;
                }

                return true;
            }
        });

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
        DrawerListAdapter adapter =new DrawerListAdapter(this, drawerMyColors);
        drawerList.setAdapter(adapter);

        DrawerOpen_Button = (ImageButton) findViewById(R.id.OpenDrawerButton);
        DrawerOpen_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });


        //MyColor picker
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawingView.setColor(drawerMyColors.get(position).colorint);
                ((DrawerListAdapter) parent.getAdapter()).selectedNum = drawerMyColors.get(position).colorint; //set the selected
                ((DrawerListAdapter) parent.getAdapter()).notifyDataSetChanged(); //redraw list
                Toast.makeText(view.getContext(), drawerMyColors.get(position).colorName, Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }

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
                }).show();
                saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
            }
        });

        newColor_Button = (Button) findViewById(R.id.addNewColor_btn);
        newColor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ColorPickerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private void createDrawerColors()
    {
        drawerMyColors = new ArrayList<MyColor>();
        //add colors

        drawerMyColors = fileReadWrite.Read();

        if (drawerMyColors.size()==0) {
            drawerMyColors.add(new MyColor("Black", 0xFF000000));
            drawerMyColors.add(new MyColor("White", 0xFFFFFFFF));
            drawerMyColors.add(new MyColor("Red", 0xFFF44336));
            drawerMyColors.add(new MyColor("Pink", 0xFFE91E63));
            drawerMyColors.add(new MyColor("Purple", 0xFF9C27B0));
            drawerMyColors.add(new MyColor("Deep Purple", 0xFF673AB7));
            drawerMyColors.add(new MyColor("Indigo", 0xFF3F51B5));
            drawerMyColors.add(new MyColor("Blue", 0xFF2196F3));
            drawerMyColors.add(new MyColor("Light Blue", 0xFF03A9F4));
            drawerMyColors.add(new MyColor("Cyan", 0xFF00BCD4));
            drawerMyColors.add(new MyColor("Teal", 0xFF009688));
            drawerMyColors.add(new MyColor("Green", 0xFF4CAF50));
            drawerMyColors.add(new MyColor("Light Green", 0xFF8BC34A));
            drawerMyColors.add(new MyColor("Lime", 0xFFCDDC39));
            drawerMyColors.add(new MyColor("Yellow", 0xFFFFEB3B));
            drawerMyColors.add(new MyColor("Amber", 0xFFFFC107));
            drawerMyColors.add(new MyColor("Orange", 0xFFFF9800));
            drawerMyColors.add(new MyColor("Deep Orange", 0xFFFF5722));
            drawerMyColors.add(new MyColor("Brown", 0xFF795548));
            drawerMyColors.add(new MyColor("Grey", 0xFF9E9E9E));
            drawerMyColors.add(new MyColor("Blue Grey", 0xFF607D8B));

            fileReadWrite.WriteAll(drawerMyColors);
        }

        //create brushes
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==1 && resultCode == RESULT_OK && data != null) {
            int a, r, g, b;
            String name;
            a=data.getIntExtra("A", 0);
            r=data.getIntExtra("R", 0);
            g=data.getIntExtra("G", 0);
            b=data.getIntExtra("B", 0);
            name=data.getStringExtra("name");

            MyColor newColor = new MyColor(a, r, g, b);
            if (name!=null && !name.equals("")) {
                newColor.colorName = name;
            }

            drawerMyColors.add(newColor);
            drawingView.setColor(newColor.colorint);
            ((DrawerListAdapter) drawerList.getAdapter()).selectedNum = drawerMyColors.get(drawerMyColors.size()-1).colorint;
            ((DrawerListAdapter) drawerList.getAdapter()).notifyDataSetInvalidated();
            Toast.makeText(getBaseContext(), newColor.colorName, Toast.LENGTH_SHORT).show();

            fileReadWrite.Write(newColor);

            //drawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Sensor event listener register
        sensorManager.registerListener(sensorsListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        //unregister sensor listener at app pause
        sensorManager.unregisterListener(sensorsListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    SensorEventListener sensorsListener = new SensorEventListener() {
        //---!!---
        // ToDo : This method needs cleanup, optimalization and corrections
        //---!!---
        @Override
        public void onSensorChanged(SensorEvent event) {
            //get the window properties (hopefully they are set by this time)
            float newX;
            float newY;
            height = drawingView.getHeight();
            width = drawingView.getWidth();

            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && height != 0) { //get LinearAcceleration values
                if (model.contains("SM"))
                {
                    ax = -event.values[0];
                }
                else
                {
                    ax = event.values[0];
                }
                ay = event.values[1];
                az = event.values[2];



                //Avarge filter
                nX++;
                sumX += ax;
                nY++;
                sumY += ay;
                float avargeX;
                avargeX = sumX / nX;
                float avargeY;
                avargeY = sumY / nY;
                // --------------
                float irvektorX = avargeX - elozoX;
                float irvektorY = avargeY - elozoY;
                elozoX = avargeX;
                elozoY = avargeY;
                //

                double angle = Math.atan2(irvektorY, irvektorX);
                xVelocity = (float) Math.cos(angle) * Math.abs(ax) * 2;
                yVelocity = (float) Math.sin(angle) * Math.abs(ay) * 2;

                //calculate X/Y Distance moved

                newX = (float) (CursorX + xVelocity);
                newY = (float) (CursorY + yVelocity);

                //if screen is touched
                if (touched)
                {
                    if (newX >= 0 && newY >= 0 && newX <= width && newY <= height) {
                        drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                        drawingView.Move(newX, newY);
                        //set new cursor position
                        CursorX = newX;
                        CursorY = newY;
                    } else { //if not
                        if (newX < 0) newX = 0;
                        if (newX > width) newX = width;
                        if (newY < 0) newY = 0;
                        if (newY > height) newY = height;
                        drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                        drawingView.Move(newX, newY);
                        CursorX = newX;
                        CursorY = newY;
                    }
                }
                else
                {
                    if (newX >= 0 && newY >= 0 && newX <= width && newY <= height) {
                        drawingView.Move(newX, newY);
                        //set new cursor position
                        CursorX = newX;
                        CursorY = newY;
                    } else { //if not
                        if (newX < 0) newX = 0;
                        if (newX > width) newX = width;
                        if (newY < 0) newY = 0;
                        if (newY > height) newY = height;
                        drawingView.Move(newX, newY);
                        CursorX = newX;
                        CursorY = newY;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}