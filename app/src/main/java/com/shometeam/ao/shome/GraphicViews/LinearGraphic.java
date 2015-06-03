package com.shometeam.ao.shome.GraphicViews;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.shometeam.ao.shome.ProjectExceptions.WrongSizeException;

import java.util.ArrayList;

//TODO: выбиральщик графиков

/**
 * This class represents graph widget
 * @author Anton Korenev
 * @version 1.0
 */
public class LinearGraphic extends Graphic {
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
     * Statistical values
     */
    double mMin,mMax,mAverage;

    /**
     * The main constructor of the class
     * @param values the range of values for representing
     * @param width the width of paint in pixel
     * @param height the height of paint in pixels
     * @param colorScheme the color pallet
     * @param graphicName the name of histogram and view(is displayed in top right corner)
     * @param diapazon the range of value from 0 to diapazon(max)
     */
    LinearGraphic(ArrayList<Double> values,int width,int height,ColorScheme colorScheme,String graphicName,int diapazon){
        mHeight = height;
        mWidth = width;
        mColorScheme = colorScheme;
        mGraphicName = graphicName;
        mDiapazon = diapazon;
        mIndentX = mWidth/10;
        mIndentY = mHeight/10;
        mPointQuantity = values.size();
        try {
            mGraphicCurves.add(convertFromSeries(values));
        } catch (WrongSizeException e) {
            e.printStackTrace();
        }
        mMax = convertToValue(getMaxPoint('y').y);
        mMin = convertToValue(getMinPoint('y').y);
        mAverage = 0;
    }

    /**
     * Added a new graph curve into view
     * @param values added graph values
     * @throws WrongSizeException
     */
    public void addCurve(ArrayList<Double> values) throws WrongSizeException {
        mGraphicCurves.add(convertFromSeries(values));
        mMax = convertToValue(getMaxPoint('y').y);
        mMin = convertToValue(getMinPoint('y').y);
        mAverage = 0;
    }

    //Перевод из координатной системы в значение(для поиска мин макс и тд)
    private double convertToValue(double d){
        double converted_value;
        double currentDiapazon = mHeight-mIndentY*2;
        double newDiapazon = currentDiapazon/(mDiapazon);
        converted_value = -1*((d-mIndentY)/newDiapazon-mDiapazon);
        return converted_value;
    }

    //рисует минимумы и максимумы под графиком
    protected void drawMinMaxValues(Canvas canvas,int indentX,int indentY){
        int valueRectSize = 2*indentY/3;
        String str_min="Min "+Integer.toString((int)mMin);
        String str_max="Max "+Integer.toString((int)mMax);
        String str_aver="Average "+Integer.toString((int)mAverage);

        setColor(mColorScheme.getTextColor());
        mPaintStyle.setTextSize(valueRectSize/2);
        canvas.drawText(str_min, indentX, mHeight-valueRectSize/2, mPaintStyle);
        canvas.drawText(str_max, indentX+mWidth/4, mHeight-valueRectSize/2, mPaintStyle);
        canvas.drawText(str_aver, 2*indentX+mWidth/2, mHeight-valueRectSize/2, mPaintStyle);

        setColor(mColorScheme.getBlinkBlinkColor());
        mPaintStyle.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect( indentX - 2,
                mHeight - valueRectSize,
                indentX + str_min.length() * 19,
                mHeight - valueRectSize / 2 + 10,
                mPaintStyle);
        canvas.drawRect( indentX+mWidth/4-2,
                mHeight-valueRectSize,
                indentX+mWidth/4+str_max.length()*20,
                mHeight-valueRectSize/2+10,
                mPaintStyle);
        canvas.drawRect( 2*indentX+mWidth/2-2,
                mHeight-valueRectSize,
                2*indentX+mWidth/2+str_aver.length()*18,
                mHeight-valueRectSize/2+10,
                mPaintStyle);
    }

    /// Override methods(from Graphic) ///

    @Override
    public void draw(Canvas canvas) {
        mPaintStyle.setColor(mColorScheme.getBackgroundColor());
        mPaintStyle.setStyle(Paint.Style.FILL);
        mPaintStyle.setAntiAlias(true);
        canvas.drawPaint(mPaintStyle);

        drawAxes(canvas,mIndentX,mIndentY,mDiapazon);
        drawMinMaxValues(canvas,mIndentX,mIndentY);

        drawName(canvas, mIndentY);

        //drawing graphics
        for(int j = 0; j < mGraphicCurves.size(); j++){
            //forming j graphic
            Path graph = new Path();
            graph.moveTo(mGraphicCurves.get(j).get(0).x,mGraphicCurves.get(j).get(0).y);
            int i=0;
            Point previousPoint = null;

            for(Point thisPoint: mGraphicCurves.get(j)){
                drawPoint(canvas,thisPoint);

                if(i>0){
                    drawLine(canvas,previousPoint,thisPoint);
                    graph.lineTo(thisPoint.x,thisPoint.y);
                }

                previousPoint = thisPoint;
                i++;
            }
            //closing figure circuit
            graph.lineTo(mWidth,mHeight-mIndentY);
            graph.lineTo(mIndentX,mHeight-mIndentY);
            graph.lineTo(mGraphicCurves.get(j).get(0).x,mGraphicCurves.get(j).get(0).y);
            graph.close();
            mPaintStyle.setStyle(Paint.Style.FILL);
            setColor(mColorScheme.getBlinkBlinkColor());
            //formed figure drawing
            canvas.drawPath(graph, mPaintStyle);
            //choice rects drawing
            setColor(mColorScheme.getBlinkColor());
            int left = mGraphicCurves.get(j).get(0).x-30;
            int right = mGraphicCurves.get(j).get(0).x;
            int top = mGraphicCurves.get(j).get(0).y-15;
            int bottom = mGraphicCurves.get(j).get(0).y+15;
            canvas.drawRect(left,top,right,bottom,mPaintStyle);
        }
    }

    @Override
    protected ArrayList<Point> convertFromSeries(ArrayList<? extends Number> values) throws WrongSizeException { //maximum 24 point
        ArrayList<Point> converted_points = new ArrayList<>();

        if(values.size() > 24 || values.size() < 2 ) throw new WrongSizeException(
                "Linear graphic can't show more than 24 points or less than 2");

        /*if(mPointQuantity < values.size()){// понадобится, если кривых(графиков) будет несколько
            mPointQuantity = values.size();
            for(int i = 0; i < mGraphicCurves.size(); i++ ){
                recountX(mGraphicCurves.get(i));
            }
        }*/

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


/*

    //расчет следующего х с учетом шага(в локальной системе координат)
    private void recountX(ArrayList<Point> points){
        int currentX = mIndentX;
        int stepX = (mWidth-mIndentX)/(mPointQuantity-1);
        for(Point p: points){
            p.x = currentX;
            currentX += stepX;
        }
    }
 */