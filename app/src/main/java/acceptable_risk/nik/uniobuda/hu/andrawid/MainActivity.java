package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.support.v4.widget.DrawerLayout;
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

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;
    Button small_Button, medium_Button, large_Button;
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
    }

    private void createDrawerColors() {
        drawerColors = new ArrayList<ViewHolder>();
        //add colors
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
        drawerColors.add(new ViewHolder("Black", "#000000"));
        drawerColors.add(new ViewHolder("White", "#FFFFFF"));

        //create brushes
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }
}
