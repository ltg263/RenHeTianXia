package com.jxxx.rhtx.base;


import com.jxxx.rhtx.utils.StringUtil;

import java.util.List;

public class ResultList<T> {
    private String error;

    public String getError() {
        if(StringUtil.isNotBlank(error)){
            return error;
        }
        return "\"status\":"+status;
    }

    public void setError(String error) {
        this.error = error;
    }

    private int status;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
