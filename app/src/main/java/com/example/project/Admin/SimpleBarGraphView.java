package com.example.project.Admin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SimpleBarGraphView extends View {
    private Paint paint;
    private Rect rect;
    private int totalReservations = 0; // This should be set based on your data

    public SimpleBarGraphView(Context context) {
        super(context);
        init();
    }

    public SimpleBarGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleBarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Calculate the height of the bar based on total reservations
        int barHeight = (int) (height * ((float) totalReservations / 100)); // Adjust this calculation as needed

        // Set up the paint for the bar
        paint.setColor(Color.BLUE); // Set the color of the bar
        rect.set(width / 4, height - barHeight, width * 3 / 4, height); // Set the size of the bar

        canvas.drawRect(rect, paint); // Draw the bar

        // Set up the paint for the text
        paint.setColor(Color.BLACK); // Set the color of the text
        paint.setTextSize(50); // Set the text size

        String text = "Total: " + totalReservations;
        canvas.drawText(text, 10, 50, paint); // Draw the text
    }

    public void setTotalReservations(int total) {
        this.totalReservations = total;
        invalidate(); // Invalidate to trigger a redraw
    }
}
