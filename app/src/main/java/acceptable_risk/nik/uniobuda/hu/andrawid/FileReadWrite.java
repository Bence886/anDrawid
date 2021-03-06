package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tbenc on 2017. 04. 27..
 */

public class FileReadWrite {

    Context context;
    String FILENAME = "CustomColors";

    public FileReadWrite(Context context) {
        this.context = context;
    }

    public void WriteAll(ArrayList<MyColor> drawerMyColors)
    {
        File dir = context.getFilesDir();
        File file = new File(dir, FILENAME);
        file.delete();
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, MODE_PRIVATE);
            String out="";
            for (int i = 0; i< drawerMyColors.size(); i++)
            {
                out+= drawerMyColors.get(i).ToFile()+"_";
            }
            fos.write(out.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MyColor> Read()
    {
        ArrayList<MyColor> myColors = new ArrayList<>();
        boolean firstStart=false;

        try {
            byte[] buffer = new byte[1024];
            int len;
            FileInputStream fis = context.openFileInput(FILENAME);
            len = fis.read(buffer);
            fis.close();
            String in =new String(buffer, 0, len);
            String[] splitted = in.split("_");
            for (int i = 0; i<splitted.length;i++){
                myColors.add(MyColor.FromFile(splitted[i]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myColors;
    }

    public void Write(MyColor nc)
    {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, MODE_APPEND);
            String out="";
            out+=nc.ToFile()+"_";
            fos.write(out.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
