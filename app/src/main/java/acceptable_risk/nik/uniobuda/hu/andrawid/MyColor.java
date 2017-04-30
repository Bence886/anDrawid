package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.graphics.Color;

class MyColor {
    String colorName;
    int colorint;

    public MyColor(String text, int colorint)
    {
        this.colorName = text;
        this.colorint = colorint;
    }
    public MyColor()
    {
    }
    public MyColor(int a, int r, int g, int b)
    {
        if (a<17) a=17;
        colorint=Color.argb(a, r, g, b);
        colorName = "#"+Integer.toHexString(colorint);
    }

    public String ToFile()
    {
        return colorName +","+String.valueOf(colorint);
    }

    public static MyColor FromFile(String value)
    {
        String[] splitted = value.split(",");
        return new MyColor(splitted[0], Integer.valueOf(splitted[1]));
    }
}
