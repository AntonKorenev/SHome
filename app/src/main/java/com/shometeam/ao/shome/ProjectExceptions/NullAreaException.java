package com.shometeam.ao.shome.ProjectExceptions;

/**
 * Created by ao on 14.12.14.
 */
public class NullAreaException extends Exception{
    public NullAreaException(String areaName) {
        super(areaName + " Forbidden null value of area size\n");
    }

    public NullAreaException() {

    }
}
