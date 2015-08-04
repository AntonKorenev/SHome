package com.shometeam.ao.shome.CustomGuiElements.GraphicViews;

/**
 * Class with visual parameters of GraphicView
 * @author Anton Korenev
 * @version 1.0
 */
public class GraphicWidgetData {
    /**
     * Color pallet
     */
    public String mColor;

    /**
     * View name
     */
    public String mName;

    /**
     * View type
     */
    public String mType;

    /**
     * Constructor with parameters
     * @param color color pallet
     * @param name View name
     * @param type View type
     */
    public GraphicWidgetData(String color, String name, String type){
        mColor = color;
        mName = name;
        mType = type;
    }

    /**
     * Default constructor
     */
    public GraphicWidgetData(){
        String mText = "Humidity";
        String mColor = "yellow";
        String mType = "linear";
    }
}
