package com.shometeam.ao.shome.GraphicViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.shometeam.ao.shome.R;

import java.util.ArrayList;

/**
 * This class represents the custom android widget/view for displaying different types of graphics
 * and diagrams
 * @author Anton Korenev
 * @version 1.0
 */
public class GraphicView extends View{
    /**
     * Visual parameters of graphic such as color,name, type of diagram/graph
     */
    GraphicWidgetData mWidgetAttrs;

    /**
     * Values for displaying on the main curve or histogram
     */
    ArrayList<Double> mGraphicValues;

    /**
     * Values for additional layer in histogram
     */
    ArrayList<Double> mComparableGraphicValues;

    /**
     * The constructor for view with parameters
     * @param context The context of Activity where a view is placed
     * @param attrs Attributes from xml
     */
    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mWidgetAttrs = new GraphicWidgetData();

        //Getting visual attributes from xml
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GraphicView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.GraphicView_mcolor:
                    mWidgetAttrs.mColor = a.getString(attr);
                    break;
                case R.styleable.GraphicView_mtext:
                    mWidgetAttrs.mName = a.getString(attr);
                    break;
                case R.styleable.GraphicView_mtype:
                    mWidgetAttrs.mType = a.getString(attr);
                    break;
            }
        }
        a.recycle();

    }

    /**
     * The constructor without xml attributes, visual parameters are set up by default
     * @param context The context of Activity where a view is placed
     */
    public GraphicView(Context context) {
        super(context);
        mWidgetAttrs = new GraphicWidgetData();
    }

    /// builder options ///

    /**
     * Setting view type
     * @param type Histogram, Linear or smth else
     * @return this view with new adjustments
     */
    public GraphicView withViewType(String type){
        mWidgetAttrs.mType = type;
        return this;
    }

    /**
     * Setting values
     * @param values The values for displaying on the main curve/histo
     * @return this view with new adjustments
     */
    public GraphicView withValues(ArrayList<? extends Number> values){
        mGraphicValues = new ArrayList<>();
        for(Number currentValue: values){
            mGraphicValues.add((double)currentValue);
        }
        return this;
    }

    /**
     * Setting additional values
     * @param comparableValues The values for the top layer of histogram(comparable values)
     * @return this view with new adjustments
     */
    public GraphicView withComparableValues(ArrayList<? extends Number> comparableValues){
        mComparableGraphicValues = new ArrayList<>();
        for(Number currentValue: comparableValues){
            mComparableGraphicValues.add((double)currentValue);
        }
        return this;
    }

    /**
     * Setting text in top part of view
     * @param name view name
     * @return this view with new adjustments
     */
    public GraphicView withName(String name){
        mWidgetAttrs.mName = name;
        return this;
    }

    /**
     * Setting color pallet of view
     * @param color main color
     * @return this view with new adjustments
     */
    public GraphicView withColor(String color){
        mWidgetAttrs.mColor = color;
        return this;
    }

    /// override methods ///

    /**
     * Actual drawing operation
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        switch (mWidgetAttrs.mType) {
            case "linear": { // линейный
                LinearGraphic graphic = new LinearGraphic(mGraphicValues, getWidth(), getHeight(),
                        ColorScheme.getColorScheme(mWidgetAttrs.mColor, getContext()),
                        mWidgetAttrs.mName, 60);
                graphic.draw(canvas);
                break;
            }
            case "histogram": { //гистограмма
                HistogramGraphic graphic = new HistogramGraphic(mGraphicValues, mComparableGraphicValues,
                        getWidth(), getHeight(), ColorScheme.getColorScheme(mWidgetAttrs.mColor,
                        getContext()), mWidgetAttrs.mName, 60);
                graphic.draw(canvas);
                break;
            }
            default:
                //
                break;
        }
        super.onDraw(canvas);
    }

    /// Setters ///

    /**
     * Setter for color pallet
     * @param color new color
     */
    public void setColor(String color){
        mWidgetAttrs.mColor = color;
    }

    /**
     * Setter for graphic name
     * @param name new name
     */
    public void setName(String name){
        mWidgetAttrs.mName = name;
    }

}
