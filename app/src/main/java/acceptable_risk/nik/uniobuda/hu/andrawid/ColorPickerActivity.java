package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.app.Fragment;
import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by tbenc on 2017. 04. 11..
 */



//
//           BackupServicekey
//  AEdPqrEAAAAIbjXIchngK_AHGHIMqlX14Bmaccz_lycvj_mP4Q
//

public class ColorPickerActivity extends AppCompatActivity{

    Button cancel_btn, ok_btn;

    ColorPickerFragment cpf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_activity_layout);

        Bundle args = new Bundle();

        cpf = new ColorPickerFragment().newInstance(args);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_control, cpf);
        transaction.commit();

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
                output.putExtra("A", cpf.getArguments().getInt("A"));
                output.putExtra("R", cpf.getArguments().getInt("R"));
                output.putExtra("G", cpf.getArguments().getInt("G"));
                output.putExtra("B", cpf.getArguments().getInt("B"));
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }

    BackupAgent backup = new BackupAgent() {
        @Override
        public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
            
        }

        @Override
        public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {

        }
    };
}
