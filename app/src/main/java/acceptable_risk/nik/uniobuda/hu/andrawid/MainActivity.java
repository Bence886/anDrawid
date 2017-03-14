package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;
    Button New_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = (DrawingView)findViewById(R.id.drawingView);

        New_BTN = (Button)findViewById(R.id.smallBrush);
        New_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Small Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
