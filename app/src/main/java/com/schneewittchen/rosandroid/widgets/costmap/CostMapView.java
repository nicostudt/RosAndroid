package com.schneewittchen.rosandroid.widgets.costmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.helper.MatrixGestureDetector;
import com.schneewittchen.rosandroid.utility.Utils;
import com.schneewittchen.rosandroid.widgets.base.BaseData;
import com.schneewittchen.rosandroid.widgets.base.BaseView;

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 14.09.2020
 * @updated
 * @modified
 */
public class CostMapView extends BaseView implements View.OnTouchListener {

    public static final String TAG = "CostMapView";

    // Grid Map Information
    CostMapData data;

    // Rectangle Surrounding
    Paint borderPaint;
    Paint gridPaint;
    float cornerWidth;
    private RectF drawRect;

    private Matrix matrix;
    MatrixGestureDetector gestureDetector;


    public CostMapView(Context context) {
        super(context);
        init();
    }

    public CostMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        this.matrix = new Matrix();
        this.matrix.setScale(1, -1);

        this.gestureDetector = new MatrixGestureDetector(matrix, matrix -> {
            this.invalidate();
        });

        this.cornerWidth = Utils.dpToPx(getContext(), 8);

        this.borderPaint = new Paint();
        this.borderPaint.setColor(getResources().getColor(R.color.whiteHigh));
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);

        this.gridPaint = new Paint();
        this.gridPaint.setColor(getResources().getColor(R.color.whiteHigh));
        this.gridPaint.setAntiAlias(false);
        this.gridPaint.setFilterBitmap(false);

        this.drawRect = new RectF(0, 0, 0, 0);

        this.setOnTouchListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null && data.map != null) {
            //canvas.drawBitmap(data.map, 0, 0, gridPaint);
            canvas.drawBitmap(data.map, matrix, gridPaint);
        }

        // Border the view
        float width = getWidth();
        float height = getHeight();

        // Do the canvas drawing
        drawRect.set(0, 0, width, height);

        canvas.drawRoundRect(drawRect, cornerWidth, cornerWidth, borderPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void setData(BaseData newData) {
        this.data = (CostMapData) newData;
        this.invalidate();
    }

}