package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Intent;

import java.math.BigInteger;

class Color {
    String text;
    String color;
    int colorint;

    public Color(String text, String color, int colorint)
    {
        this.text = text;
        this.color = color;
        this.colorint = colorint;
    }
    public Color()
    {
    }
    public Color(int a, int r, int g, int b)
    {
        if (a<50) a=17;
        colorint=android.graphics.Color.argb(a, r, g, b);
        text = "#"+Integer.toHexString(colorint);
        color = text;
    }

    public String ToFile()
    {
        return text+","+color+","+String.valueOf(colorint);
    }

    public static Color FromFile(String value)
    {
        String[] splitted = value.split(",");
        return new Color(splitted[0], splitted[1],Integer.valueOf(splitted[2]));
    }
}
