package com.learn.widgettest.rest.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datum {
        @SerializedName("dartext")
        @Expose
        private String dartext;
        @SerializedName("darimage")
        @Expose
        private String darimage;
        @SerializedName("msgdate")
        @Expose
        private String msgdate;

        public String getDartext() {
            return dartext;
        }

        public void setDartext(String dartext) {
            this.dartext = dartext;
        }

        public String getDarimage() {
            return darimage;
        }

        public void setDarimage(String darimage) {
            this.darimage = darimage;
        }

        public String getMsgdate() {
            return msgdate;
        }

        public void setMsgdate(String msgdate) {
            this.msgdate = msgdate;
        }

    }
}
