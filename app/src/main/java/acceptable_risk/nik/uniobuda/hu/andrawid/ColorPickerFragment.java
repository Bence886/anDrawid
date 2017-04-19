package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by tbenc on 2017. 04. 14..
 */

public class ColorPickerFragment extends Fragment {
    View rootView;

    ImageView color;
    SeekBar S_A, S_R, S_G, S_B;

    int a=255, r=1, g=1, b=1;


    public static ColorPickerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ColorPickerFragment fragment = new ColorPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.color_picker_fragment_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        color = (ImageView) rootView.findViewById(R.id.color);

        S_A = (SeekBar) rootView.findViewById(R.id.a);
        S_R = (SeekBar) rootView.findViewById(R.id.r);
        S_G = (SeekBar) rootView.findViewById(R.id.g);
        S_B = (SeekBar) rootView.findViewById(R.id.b);

        SeekBar.OnSeekBarChangeListener bar = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == S_A)
                    a = progress;
                if (seekBar == S_R)
                    r = progress;
                if (seekBar == S_G)
                    g = progress;
                if (seekBar == S_B)
                    b = progress;

                getArguments().clear();
                getArguments().putInt("A", a);
                getArguments().putInt("R", r);
                getArguments().putInt("G", g);
                getArguments().putInt("B", b);

                color.getDrawable().setColorFilter(new Color(a, r, g, b).colorint, PorterDuff.Mode.ADD);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        S_A.setOnSeekBarChangeListener(bar);
        S_R.setOnSeekBarChangeListener(bar);
        S_G.setOnSeekBarChangeListener(bar);
        S_B.setOnSeekBarChangeListener(bar);
    }
}
