package com.shometeam.ao.shome.GraphicViews;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.shometeam.ao.shome.ProjectExceptions.WrongSizeException;

import java.util.ArrayList;

/**
 * This class represents histogram widget
 * To get image use draw() in onDraw() method of View, where you want to place this widget
 * @author Anton Korenev
 * @version 1.0
 */
public class HistogramGraphic extends Graphic {
    /**
     * Indent from vertical borders of View-container
     */
    int mIndentX;

    /**
     * Indent from horizontal borders of View-container
     */
    int mIndentY;

    /**
     * Value's range from 0 to max
     */
    int mDiapazon;

    /**
     * Quantity of points in graph/histogram
     */
    int mPointQuantity;

    /**
     * Actual values for representing in graphic
     */
    ArrayList<Double> mValues;

    /**
     * Values for additional(comparable) layer
     */
    ArrayList<Double> mAdditionalValues;

    /**
     * The main constructor of the class
     * @param values the range of values for representing
     * @param additionalValues the range of additional values
     * @param width the width of paint in pixel
     * @param height the height of paint in pixels
     * @param colorScheme the color pallet
     * @param graphicName the name of histogram and view(is displayed in top right corner)
     * @param diapazon the range of value from 0 to diapazon(max)
     */
    public HistogramGraphic(ArrayList<Double> values, ArrayList<Double> additionalValues, int width, int height, ColorScheme colorScheme, String graphicName, int diapazon){
        mHeight = height;
        mWidth = width;
        mColorScheme = colorScheme;
        mGraphicName = graphicName;
        mDiapazon = diapazon;
        mIndentX = mWidth/10;
        mIndentY = mHeight/10;
        mValues = values;
        mAdditionalValues = additionalValues;
        mPointQuantity = values.size();
    }

    /// Override methods(from Graphic) ///

    @Override
    protected void draw(Canvas canvas){
        mPaintStyle.setColor(mColorScheme.getBackgroundColor());
        mPaintStyle.setStyle(Paint.Style.FILL);
        mPaintStyle.setAntiAlias(true);
        canvas.drawPaint(mPaintStyle);

        drawAxes(canvas, mIndentX, mIndentY, mDiapazon);
        drawName(canvas, mIndentY);


        ArrayList<Point> points = null;
        ArrayList<Point> additional_points = null;
        try {
            points = convertFromSeries(mValues);
            additional_points = convertFromSeries(mAdditionalValues);

        } catch (WrongSizeException e) {
            e.printStackTrace();
        }
        for(int i=1; i<points.size(); i++){
            mPaintStyle.setStyle(Paint.Style.FILL);
            setColor(mColorScheme.getBlinkColor());
            canvas.drawRect(points.get(i - 1).x, points.get(i).y, points.get(i).x, mHeight - mIndentY, mPaintStyle);

            mPaintStyle.setStyle(Paint.Style.STROKE);
            setColor(mColorScheme.getColor());
            canvas.drawRect(points.get(i - 1).x, points.get(i).y, points.get(i).x, mHeight - mIndentY, mPaintStyle);

            if(points.get(i-1).y > additional_points.get(i-1).y){
                setColor(mColorScheme.getBlinkBlinkColor());
                mPaintStyle.setStyle(Paint.Style.FILL);
                canvas.drawRect(points.get(i - 1).x, additional_points.get(i).y, points.get(i).x, points.get(i).y, mPaintStyle);

                setColor(mColorScheme.getColor());
                mPaintStyle.setStyle(Paint.Style.STROKE);
                canvas.drawRect(points.get(i - 1).x, additional_points.get(i).y, points.get(i).x, points.get(i).y, mPaintStyle);
            }
        }
    }

    @Override
    protected ArrayList<Point> convertFromSeries(ArrayList<? extends Number> values) throws WrongSizeException { //maximum 24 point
        ArrayList<Point> converted_points = new ArrayList<>();

        if(values.size() > 24 || values.size() < 2 ) throw new WrongSizeException(
                "Linear graphic can't show more than 24 points or less than 2");

        int stepX = (mWidth-mIndentX)/(mPointQuantity-1);
        int currentX = mIndentX;
        double currentDiapazon = mHeight- 2*mIndentY;
        double newDiapazon = currentDiapazon/(mDiapazon);

        for(Number d: values){
            converted_points.add(new Point(currentX, (int) ((mDiapazon - (double)d) * newDiapazon)+mIndentY));
            currentX += stepX;
        }

        return converted_points;
    }
}
