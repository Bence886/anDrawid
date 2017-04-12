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
        if (a==0) a=1;
        if (r==0) r=1;
        if (g==0) g=1;
        if (b==0) b=1;
        colorint=android.graphics.Color.argb(a, r, g, b);
        text = "#"+Integer.toHexString(colorint);
        color = text;
    }
}
