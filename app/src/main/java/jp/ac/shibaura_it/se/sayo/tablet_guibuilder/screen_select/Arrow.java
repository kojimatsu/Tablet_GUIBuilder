package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by matsu on 2014/10/30.
 */
public class Arrow extends View {

    Point start;
    Point end;

    protected Arrow(Context context, Point start, Point end) {
        super(context);
        this.start = start;
        this.end = end;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 必要があればテキストも描画
//        paint.setTextSize(12);
//        paint.setColor(Color.BLACK);
//        canvas.drawText("btn1をクリック", 10, 20, paint);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
        // いつか三角形を描画
//        Path path = new Path();
//        path.moveTo(150f, 50f);
//        path.lineTo(150f-58f,150f);
//        path.lineTo(150f+58f, 150f);
//        path.close();
//        canvas.drawPath(path,paint);
    }
}
