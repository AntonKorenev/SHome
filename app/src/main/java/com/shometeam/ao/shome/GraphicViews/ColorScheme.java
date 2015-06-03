package com.shometeam.ao.shome.GraphicViews;

import android.content.Context;

import com.shometeam.ao.shome.R;

/**
 * Class represent color model for different types of graphics
 *
 * @author Anton Korenev
 * @version 1.0
 */
public class ColorScheme {
    /**
     * The bold color without transparency, is used for dots and borders
     */
    private int mColor;
    /**
     * Middle transparency color
     */
    private int mBlinkColor;
    /**
     * The color with the highest level of transparency
     */
    private int mBlinkBlinkColor;
    /**
     * The color of the text labels
     */
    private int mTextColor;
    /**
     * BackgroundColor
     */
    private int mBackgroundColor;

    /**
     * The constructor with initial values of class parameters
     *
     * @param c bold color value
     * @param bc light transparency color value
     * @param bbc high transparency color value
     * @param tc text color value
     * @param bkc background color value
     */
    ColorScheme(int c,int bc,int bbc,int tc,int bkc){
        mColor = c;
        mBlinkColor = bc;
        mBlinkBlinkColor = bbc;
        mTextColor = tc;
        mBackgroundColor = bkc;
    }

    /**
     * The constructor without initial values of class parameters
     * By default, all of them are '0'
     */
    ColorScheme(){
        mColor = R.color.backColor;
        mBlinkColor = R.color.backColor;
        mBlinkBlinkColor = R.color.backColor;
        mTextColor = R.color.backColor;
        mBackgroundColor = R.color.backColor;
    }

    //////////////// Set methods ////////////////

    /**
     * Setter for text color
     * @param textColor color of the text
     */
    public void setTextColor(int textColor) { mTextColor = textColor; }

    /**
     * Setter for bold color
     * @param Color new bold color
     */
    public void setColor(int Color) {
        mColor = Color;
    }

    /**
     * Setter for middle blink color
     * @param BlinkColor new middle blink color
     */
    public void setBlinkColor(int BlinkColor) {
        mBlinkColor = BlinkColor;
    }

    /**
     * Setter for high blink color
     * @param BlinkBlinkColor new high blink color
     */
    public void setBlinkBlinkColor(int BlinkBlinkColor) {
        mBlinkBlinkColor = BlinkBlinkColor;
    }

    /**
     * Setter for background color
     * @param backgroundColor new background color
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    //////////////// Get methods ////////////////

    /**
     * Getter for bold color
     * @return current bold color
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Getter for middle blink color
     * @return current middle blink color
     */
    public int getBlinkColor() {
        return mBlinkColor;
    }

    /**
     * Getter for high blink color
     * @return current blink color
     */
    public int getBlinkBlinkColor() {
        return mBlinkBlinkColor;
    }

    /**
     * Getter for text color
     * @return current text color
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Getter for background color
     * @return current background color
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    //////////////// Class specific methods ////////////////

    /**
     * Static method for getting existing color schemes
     * They are Blue,Green,Red,Yellow
     *
     * @param color color of required scheme
     * @param context the context of current window to access variables in colors.xml
     * @return one of defined color schemes according to request
     */
    public static ColorScheme getColorScheme(String color,Context context){
        ColorScheme scheme = new ColorScheme();
        scheme.setBackgroundColor(context.getResources().getColor(R.color.backColor));
        scheme.setTextColor(context.getResources().getColor(R.color.textColor));

        switch (color) {
            case "red":
                scheme.setColor(context.getResources().getColor(R.color.redColor));
                scheme.setBlinkColor(context.getResources().getColor(R.color.blinkRedColor));
                scheme.setBlinkBlinkColor(context.getResources().getColor(R.color.blinkBlinkRedColor));
                break;
            case "green":
                scheme.setColor(context.getResources().getColor(R.color.greenColor));
                scheme.setBlinkColor(context.getResources().getColor(R.color.blinkGreenColor));
                scheme.setBlinkBlinkColor(context.getResources().getColor(R.color.blinkBlinkGreenColor));
                break;
            case "blue":
                scheme.setColor(context.getResources().getColor(R.color.blueColor));
                scheme.setBlinkColor(context.getResources().getColor(R.color.blinkBlueColor));
                scheme.setBlinkBlinkColor(context.getResources().getColor(R.color.blinkBlinkBlueColor));
                break;
            case "yellow":
                scheme.setColor(context.getResources().getColor(R.color.yellowColor));
                scheme.setBlinkColor(context.getResources().getColor(R.color.blinkYellowColor));
                scheme.setBlinkBlinkColor(context.getResources().getColor(R.color.blinkBlinkYellowColor));
                break;
            default:

                break;
        }

        return scheme;
    }
}
