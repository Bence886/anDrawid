package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

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

    public DrawingView(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
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
