package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ZoomButtonsController;

import java.util.Random;

/**
 * Created by tbenc on 2017. 03. 10..
 * Class to hold and handle the drawing
 */
public class DrawingView extends View {
    //drawing variables
    private Path path;
    private Paint paint, canvasPaint;
    private Canvas canvas;
    private Bitmap bmp;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);

        //Only for testing
        Random r = new Random();
            //drawFromTo(r.nextInt(1000), r.nextInt(2000), r.nextInt(1000), r.nextInt(2000));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bmp);
    }

    private void Init()
    {
        path = new Path();
        paint = new Paint();
        paint.setColor(0xff0000ff);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void drawFromTo(float x1, float y1, float x2, float y2)
    {
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        if (x1!=x2 && y1!=y2)
        {
            canvas.drawPath(path,  paint);
        }
        path.reset();
        invalidate();
    }
}
