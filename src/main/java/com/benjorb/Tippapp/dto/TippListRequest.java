package com.benjorb.Tippapp.dto;

import java.util.List;

public class TippListRequest {

    private List<TippRequest> tipps;

    public List<TippRequest> getTipps(){
        return tipps;

        }
    public void setTipps(){
        this.tipps=tipps;
    }
}
