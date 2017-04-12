package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by tbenc on 2017. 04. 11..
 */

public class ColorPickerActivity extends AppCompatActivity {
    Button ok_btn, cancel_btn;
    ImageView color;
    SeekBar S_A, S_R, S_G, S_B;

    int a=255, r=0, g=0, b=0;

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
        color = (ImageView) findViewById(R.id.color);

        S_A = (SeekBar) findViewById(R.id.a);
        S_R = (SeekBar) findViewById(R.id.r);
        S_G = (SeekBar) findViewById(R.id.g);
        S_B = (SeekBar) findViewById(R.id.b);

        S_A.setOnSeekBarChangeListener(bar);
        S_R.setOnSeekBarChangeListener(bar);
        S_G.setOnSeekBarChangeListener(bar);
        S_B.setOnSeekBarChangeListener(bar);

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

    SeekBar.OnSeekBarChangeListener bar = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar==S_A)
                a=progress;
            if (seekBar==S_R)
                r=progress;
            if (seekBar==S_G)
                g=progress;
            if (seekBar==S_B)
                b=progress;

            color.getDrawable().setColorFilter(new Color(a, r, g, b).colorint, PorterDuff.Mode.ADD);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
