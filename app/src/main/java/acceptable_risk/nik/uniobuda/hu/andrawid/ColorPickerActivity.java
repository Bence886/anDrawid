package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by tbenc on 2017. 04. 11..
 */

public class ColorPickerActivity extends AppCompatActivity {
    Button ok_btn, cancel_btn;

    int a=20, r=20, g=20, b=20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_activity_layout);

        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent output = new Intent();
                output.putExtra("A", a);
                output.putExtra("R", r);
                output.putExtra("G", g);
                output.putExtra("B", b);
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }
}
