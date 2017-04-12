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
        text = "#"+Integer.toHexString(a)+Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
        color = text;
        colorint = (a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 16 | (b & 0xff);
    }
}
