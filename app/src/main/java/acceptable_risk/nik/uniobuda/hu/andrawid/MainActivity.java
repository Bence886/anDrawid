package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    int div =100; //divide the phone movement
    Date Start;
    String FILENAME = "CustomColors";

    boolean firstStart=true;

    DrawingView drawingView;
    Button small_Button, medium_Button, large_Button, new_Button, save_Button, newColor_Button;
    ImageButton DrawerOpen_Button;
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

        DrawerOpen_Button = (ImageButton) findViewById(R.id.OpenDrawerButton);
        DrawerOpen_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });


        //Color picker
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawingView.setColor(drawerColors.get(position).color);
                ((DrawerListAdapter) parent.getAdapter()).selectedNum = drawerColors.get(position).colorint; //set the selected
                ((DrawerListAdapter) parent.getAdapter()).notifyDataSetChanged(); //redraw list
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

    private void createDrawerColors() {
        drawerColors = new ArrayList<Color>();
        //add colors

        try {
            byte[] buffer = new byte[1024];
            int len;
            FileInputStream fis = openFileInput(FILENAME);
            len = fis.read(buffer);
            fis.close();
            String in =new String(buffer, 0, len);
            String [] asd = in.split(":");
            firstStart = Boolean.valueOf(asd[0]);
            String[] splitted = asd[1].split("_");
            for (int i = 0; i<splitted.length;i++){
                drawerColors.add(Color.FromFile(splitted[i]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (firstStart) {
            drawerColors.add(new Color("Black", "#000000", 0xFF000000));
            drawerColors.add(new Color("White", "#FFFFFF", 0xFFFFFFFF));
            drawerColors.add(new Color("Red", "#F44336", 0xFFF44336));
            drawerColors.add(new Color("Pink", "#E91E63", 0xFFE91E63));
            drawerColors.add(new Color("Purple", "#9C27B0", 0xFF9C27B0));
            drawerColors.add(new Color("Deep Purple", "#673AB7", 0xFF673AB7));
            drawerColors.add(new Color("Indigo", "#3F51B5", 0xFF3F51B5));
            drawerColors.add(new Color("Blue", "#2196F3", 0xFF2196F3));
            drawerColors.add(new Color("Light Blue", "#03A9F4", 0xFF03A9F4));
            drawerColors.add(new Color("Cyan", "#00BCD4", 0xFF00BCD4));
            drawerColors.add(new Color("Teal", "#009688", 0xFF009688));
            drawerColors.add(new Color("Green", "#4CAF50", 0xFF4CAF50));
            drawerColors.add(new Color("Light Green", "#8BC34A", 0xFF8BC34A));
            drawerColors.add(new Color("Lime", "#CDDC39", 0xFFCDDC39));
            drawerColors.add(new Color("Yellow", "#FFEB3B", 0xFFFFEB3B));
            drawerColors.add(new Color("Amber", "#FFC107", 0xFFFFC107));
            drawerColors.add(new Color("Orange", "#FF9800", 0xFFFF9800));
            drawerColors.add(new Color("Deep Orange", "#FF5722", 0xFFFF5722));
            drawerColors.add(new Color("Brown", "#795548", 0xFF795548));
            drawerColors.add(new Color("Grey", "#9E9E9E", 0xFF9E9E9E));
            drawerColors.add(new Color("Blue Grey", "#607D8B", 0xFF607D8B)); //0xFF607D8B
            File dir = getFilesDir();
            File file = new File(dir, FILENAME);
            file.delete();
            try {
                FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
                String out="1:";
                for (int i = 0; i<drawerColors.size(); i++)
                {
                    out+=drawerColors.get(i).ToFile()+"_";
                }
                fos.write(out.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //create brushes
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode == RESULT_OK && data != null) {
            int a, r, g, b;
            a=data.getIntExtra("A", 0);
            r=data.getIntExtra("R", 0);
            g=data.getIntExtra("G", 0);
            b=data.getIntExtra("B", 0);
            Color nc = new Color(a, r, g, b);
            drawerColors.add(nc);
            drawingView.setColor(nc.color);
            ((DrawerListAdapter) drawerList.getAdapter()).selectedNum = drawerColors.get(drawerColors.size()-1).colorint;
            ((DrawerListAdapter) drawerList.getAdapter()).notifyDataSetInvalidated();
            Toast.makeText(getBaseContext(), nc.text, Toast.LENGTH_SHORT).show();
            try {
                FileOutputStream fos = openFileOutput(FILENAME, MODE_APPEND);
                String out="";
                out+=nc.ToFile()+"_";
                fos.write(out.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //drawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Sensor event listener register
        sensorManager.registerListener(sensorsListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister sensor listener at app pause
        sensorManager.unregisterListener(sensorsListener);
    }

    @Override
    protected void onStop() {
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
            height=drawingView.getHeight();
            width=drawingView.getWidth();

            if (Start==null)
            {//for the first time
                Start=new Date(System.currentTimeMillis());
            }

            float Elapsed = new Date(System.currentTimeMillis()).getTime() - Start.getTime(); //calculate time between event calls

            if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION && height!=0) { //get LinearAcceleration values
                ax = event.values[0];
                ay = event.values[1];
                az = event.values[2];

                //if the acceleration is faster than 0.5m/s^2
                if ((Math.abs(ax) > 0.1 || Math.abs(ay) > 0.1) && Elapsed < 1000) {


                    xVelocity += (float) 2 * ax * Elapsed;
                    yVelocity += (float) 2 * ay * Elapsed;


                    //calculate X/Y Distance moved
                    //float newX = (CursorX + xVelocity/div);
                    //float newY = (CursorY + yVelocity/div);

                    newX = CursorX + xVelocity / div;
                    newY = CursorY + yVelocity / div;

                    //if the cursor stays in the window
                    if (newX >= 0 && newY >= 0 && newX <= width && newY <= height) {
                        drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                        //set new cursor position
                        CursorX = newX;
                        CursorY = newY;
                    } else { //if not
                        if (newX < 0) newX = 0;
                        if (newX > width) newX = width;
                        if (newY < 0) newY = 0;
                        if (newY > height) newY = height;
                        drawingView.drawFromTo(CursorX, CursorY, newX, newY);
                        CursorX = newX;
                        CursorY = newY;
                    }
                    Start = new Date(); //set new time
                } else if (Elapsed > 1000) {
                    Start = new Date();
                    xVelocity = 0;
                    yVelocity = 0;
                }
                else
                {
                    xVelocity = 0;
                    yVelocity = 0;
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}