package com.shometeam.ao.shome.GraphicViews;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.shometeam.ao.shome.ProjectExceptions.WrongSizeException;

import java.util.ArrayList;

/**
 * Abstract class, which represent graphic
 * use only for formalizing different types og graphics
 *
 * @author Anton Korenev
 * @version 1.0
 */
public abstract class Graphic {//
    /**
     * The collection of graphics on this widget
     */
    protected ArrayList<ArrayList<Point>> mGraphicCurves = new ArrayList<>();
    /**
     * A style of graphic (text color,background, font etc).
     */
    protected Paint mPaintStyle = new Paint();

    /**
     * A width of draw area
     */
    protected int mWidth;

    /**
     * A height of draw area
     */
    protected int mHeight;

    /**
     * The Color Scheme
     */
    protected ColorScheme mColorScheme;

    /**
     * The name of graphic
     */
    protected String mGraphicName;

    //////////////// Set methods ////////////////

    /**
     * Setter for name
     * @param name new graphic name
     */
    public void setName(String name){
        mGraphicName = name;
    }


    /**
     * Setter for full color scheme
     * @param cs new color scheme
     */
    public void setColorScheme(ColorScheme cs){
        mColorScheme = cs;
    }


    /**
     * Setter for color
     * @param color new color, represented by string value
     *              ("green","blue",etc.)
     */
    public void setColor(String color){
        mPaintStyle.setColor(Color.parseColor(color));
    }

    /**
     * Setter for color
     * @param color new color, represented by int value
     */
    public void setColor(int color){
        mPaintStyle.setColor(color);
    }

    /**
     * Setter for point collection
     * @param points new points
     * @param graphic_number position of graphic in collection
     */
    public void setPoints(ArrayList<Point> points,int graphic_number){
        mGraphicCurves.get(graphic_number).clear();
        mGraphicCurves.get(graphic_number).addAll(points);
    }

    //////////////// Get methods ////////////////

    /**
     * Getter for point collection of graphic
     * @param graphic_number position of graphic in collection
     * @return points
     */
    public ArrayList<Point> getPoints(int graphic_number){
       return mGraphicCurves.get(graphic_number);
    }

    //////////////// Class specific methods ////////////////

    /**
     * Getter for min point in one of graphic curves
     * y is in opposite to Decart direction
     * @param axe 'x' - x axe, 'y' - y axe
     * @param graphic_number position of graphic curve in collection
     * @return min point according to chosen axe criterion
     */
    public Point getMinPoint(char axe, int graphic_number) throws IndexOutOfBoundsException{
        if(mGraphicCurves.size()-1<graphic_number){
            throw new IndexOutOfBoundsException("no such curve");
        }
        if(graphic_number < 0){
            throw new IndexOutOfBoundsException("accessing index is < 0");
        }

        Point min_point = mGraphicCurves.get(graphic_number).get(0);
        switch (axe){
            case 'x':
                for(Point p_temp: mGraphicCurves.get(graphic_number)){
                    if(p_temp.x < min_point.x) min_point = p_temp;
                }
                break;
            case 'y':
                for(Point p_temp: mGraphicCurves.get(graphic_number)){
                    if(p_temp.y > min_point.y) min_point = p_temp;
                }
                break;
        }
        return min_point;
    }

    /**
     * Getter for a general min point of all graphic curves
     * y is in opposite to Decart direction
     * @throws NullPointerException if the graphic array is empty and that's why the
     *                  minimum point don't exist
     * @param axe 'x' - x axe, 'y' - y axe
     * @return general min point according to chosen axe criterion
     */
    public Point getMinPoint(char axe)  throws NullPointerException{
        Point min_point = null;
        switch(axe){
            case 'x':
                min_point = getMinPoint('x',0);
                for(int i = 1; i < mGraphicCurves.size(); i++){
                    Point p_temp = getMinPoint('x',i);
                    if(p_temp.x < min_point.x) min_point = p_temp;
                }
                break;
            case 'y':
                min_point = getMinPoint('y',0);
                for(int i = 1; i < mGraphicCurves.size(); i++){
                    Point p_temp = getMinPoint('y',i);
                    if(p_temp.y > min_point.y) min_point = p_temp;
                }
                break;
        }
        if(min_point == null){
            throw new NullPointerException("");
        }
        return min_point;
    }

    /**
     * Getter for max point in one of graphic curves
     * y is in opposite to Decart direction
     * @throws IndexOutOfBoundsException if specified graphic don't exist
     * @param axe 'x' - x axe, 'y' - y axe
     * @param graphic_number position of graphic curve in collection
     * @return max point according to chosen axe criterion
     */
    public Point getMaxPoint(char axe, int graphic_number)  throws IndexOutOfBoundsException{
        if(mGraphicCurves.size()-1 < graphic_number){
            throw new IndexOutOfBoundsException("no such curve");
        }
        if(graphic_number < 0){
            throw new IndexOutOfBoundsException("accessing index is < 0");
        }

        Point max_point = mGraphicCurves.get(graphic_number).get(0);
        switch (axe){
            case 'x':
                for(Point p_temp: mGraphicCurves.get(graphic_number)){
                    if(p_temp.x > max_point.x) max_point = p_temp;
                }
                break;
            case 'y':
                for(Point p_temp: mGraphicCurves.get(graphic_number)){
                    if(p_temp.y < max_point.y) max_point = p_temp;
                }
                break;
        }
        return max_point;
    }

    /**
     * Getter for a general max point of all graphic curves
     * y is in opposite to Decart direction
     * @throws NullPointerException if the graphic array is empty and that's why the
     *                  maximum point don't exist
     * @param axe 'x' - x axe, 'y' - y axe
     * @return general min point according to chosen axe criterion
     */
    public Point getMaxPoint(char axe) throws NullPointerException{
        Point max_point = null;
        switch(axe){
            case 'x':
                max_point = getMaxPoint('x',0);
                for(int i = 1; i < mGraphicCurves.size(); i++){
                    Point p_temp = getMaxPoint('x',i);
                    if(p_temp.x > max_point.x) max_point = p_temp;
                }
                break;
            case 'y':
                max_point = getMaxPoint('y',0);
                for(int i = 1; i < mGraphicCurves.size(); i++){
                    Point p_temp = getMaxPoint('y',i);
                    if(p_temp.y < max_point.y) max_point = p_temp;
                }
                break;
        }
        if(max_point == null){
            throw new NullPointerException("");
        }
        return max_point;
    }

    /**
     * Add a point to graphic
     * @throws IndexOutOfBoundsException if specified graphic don't exist
     * @param point added point
     * @param graphic_number position of graphic in collection
     */
    public void addPoint(Point point,int graphic_number) throws IndexOutOfBoundsException{
        if(mGraphicCurves.size()-1 < graphic_number){
            throw new IndexOutOfBoundsException("no such curve");
        }
        else if(graphic_number < 0){
            throw new IndexOutOfBoundsException("accessing index is < 0");
        } else{
            mGraphicCurves.get(graphic_number).add(point);
        }
    }

    /**
     * Draw method for point
     * @param canvas
     * @param point point to draw
     */
    protected void drawPoint(Canvas canvas,Point point){
        //external frame of point
        mPaintStyle.setStyle(Paint.Style.STROKE);
        setColor(mColorScheme.getColor());
        canvas.drawCircle(point.x,point.y,10,mPaintStyle);

        //transparent circle around point
        mPaintStyle.setStyle(Paint.Style.FILL_AND_STROKE);
        setColor(mColorScheme.getBlinkColor());
        canvas.drawCircle(point.x,point.y,8,mPaintStyle);

        //bold point
        mPaintStyle.setStyle(Paint.Style.FILL);
        setColor(mColorScheme.getColor());
        canvas.drawCircle(point.x,point.y,2,mPaintStyle);
    }

    /**
     * Draw method for line
     * @param canvas
     * @param startPoint start point of line
     * @param endPoint end point of line
     */
    protected void drawLine(Canvas canvas,Point startPoint,Point endPoint){
        mPaintStyle.setStyle(Paint.Style.STROKE);
        setColor(mColorScheme.getColor());
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, mPaintStyle);
    }

    /**
     * Draw method for graphic name
     * @param canvas
     * @param indentY start point and indent from y axe
     */
    protected void drawName(Canvas canvas,int indentY){
        //text area
        mPaintStyle.setStyle(Paint.Style.FILL);
        setColor(mColorScheme.getColor());
        int nameRectSize = 2*indentY/3;
        canvas.drawRect(0,nameRectSize-4,mWidth,nameRectSize-2,mPaintStyle);

        //text
        setColor(mColorScheme.getTextColor());
        mPaintStyle.setTextSize(40);
        canvas.drawText(mGraphicName, mWidth-30*mGraphicName.length(), nameRectSize-6, mPaintStyle);
    }


    /**
     * @throws ArithmeticException if new diapason have zero length
     * @param canvas
     * @param indentX indent from x axe
     * @param indentY indent from y axe
     * @param newDiapason the maximum value of function
     */
    protected void drawAxes(Canvas canvas,int indentX,int indentY,int newDiapason)
            throws ArithmeticException {
        if(newDiapason==0) {
            throw new ArithmeticException("by zero");
        } else{
            //area
            mPaintStyle.setStyle(Paint.Style.STROKE);
            setColor(mColorScheme.getColor());
            canvas.getClipBounds();
            canvas.drawRect(indentX, indentY, mWidth, mHeight-indentY, mPaintStyle);

            //step size counting
            int stepY = newDiapason/20;

            //translate diapazon after indent counting
            int currentDiapazon = mHeight-indentY*2;

            //drawing labels on axes
            mPaintStyle.setColor(mColorScheme.getTextColor());
            mPaintStyle.setTextSize(indentX/4);
            for(int valueY = 0; valueY <=newDiapason; valueY+=stepY ){
                String strVal = Integer.toString(valueY);
                int yInViewCoordinates = mHeight-valueY*currentDiapazon/newDiapason-indentY;
                canvas.drawText(strVal,indentX/4,yInViewCoordinates,mPaintStyle);
                canvas.drawLine(0,yInViewCoordinates+2,indentX/2,yInViewCoordinates+2,mPaintStyle);
            }
        }
    }

    //////////////// Abstract methods ////////////////

    /**
     * Abstract method, have to be implemented in descendants
     * actual graphic drawing operations
     * @param canvas
     */
    protected abstract void draw(Canvas canvas);

    /**
     * Abstract method, have to be implemented in descendants
     * converting values into coordinates
     * @param values
     * @return ArrayList of android API Points - local coordinates of view area
     */
    protected abstract ArrayList<Point> convertFromSeries(ArrayList<? extends Number> values)
            throws WrongSizeException;
}
