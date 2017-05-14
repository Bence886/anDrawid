package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ZoomButtonsController;

import java.util.Random;

public class DrawingView extends View {
    //drawing variables
    private Path path;
    private Paint paint, canvasPaint;
    private Paint dpaint;
    private Canvas canvas;
    private Bitmap bmp;
    public float brushSize;
    float x, y;
    boolean draw;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bmp, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
        if (!draw)
        {
            canvas.drawPoint(x, y, dpaint);
            canvas.drawPoint(x, y, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bmp);
    }

    public void setColor(int color)
    {
        paint.setColor(color);
        if (color == Color.WHITE)
        {
            dpaint.setColor(Color.BLACK);
        }
    }

    public void startNew(){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    private void Init()
    {
        path = new Path();
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        dpaint = new Paint();
        dpaint.setColor(Color.WHITE);
        dpaint.setAntiAlias(true);
        dpaint.setStyle(Paint.Style.STROKE);
        dpaint.setStrokeJoin(Paint.Join.ROUND);
        dpaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void drawFromTo(float x1, float y1, float x2, float y2)
    {
        draw = true;
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        canvas.drawPath(path,  paint);
        path.reset();
        invalidate();
    }

    public void Move(float x, float y)
    {
        draw = false;
        this.x = x;
        this.y = y;
        invalidate();
    }

    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        paint.setStrokeWidth(brushSize);
        dpaint.setStrokeWidth(brushSize + 10);
    }
}
