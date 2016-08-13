package com.whans.platform.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author hanson.
 */
public class ErrorResponse implements Serializable {
    @SerializedName("error")
    public String error;
}
