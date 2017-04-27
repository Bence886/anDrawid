package acceptable_risk.nik.uniobuda.hu.andrawid;

class MyColor {
    String text;
    String color;
    int colorint;

    public MyColor(String text, String color, int colorint)
    {
        this.text = text;
        this.color = color;
        this.colorint = colorint;
    }
    public MyColor()
    {
    }
    public MyColor(int a, int r, int g, int b)
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

    public static MyColor FromFile(String value)
    {
        String[] splitted = value.split(",");
        return new MyColor(splitted[0], splitted[1],Integer.valueOf(splitted[2]));
    }
}
