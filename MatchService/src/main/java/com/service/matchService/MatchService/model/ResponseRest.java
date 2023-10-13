package com.service.matchService.MatchService.model;
import io.swagger.annotations.ApiModelProperty;

public class ResponseRest {
    @ApiModelProperty(notes = "The data associated with the response")
    private String data;

    @ApiModelProperty(notes = "A message describing the response")
    private String msg;

    @ApiModelProperty(notes = "A boolean flag indicating the validity of the response")
    private boolean isValid;

    public ResponseRest(String data, String msg, boolean isValid) {
        this.data = data;
        this.msg = msg;
        this.isValid = isValid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "ResponseRest{" +
                "data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
