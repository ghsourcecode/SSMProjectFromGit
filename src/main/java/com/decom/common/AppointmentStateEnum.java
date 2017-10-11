package com.decom.common;

/**
 * Created by Administrator on 2017/9/19.
 */
public enum AppointmentStateEnum {
    SUCCESS(1, "预约成功"),
    NO_NUMBER(2, "库存不足"),
    REPEAT_APPOINT(-1, "重复申请"),
    INNER_ERROR(-2, "系统异常");

    private int state;
    private String stateInfo;

    AppointmentStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AppointmentStateEnum stateOf(int index){
        for(AppointmentStateEnum appoint : values()){
            if(appoint.getState() == index)
                return appoint;
        }

        return null;
    }

}
