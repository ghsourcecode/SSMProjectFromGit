package com.decom.dto;

import com.decom.common.AppointmentStateEnum;
import com.decom.entity.Appointment;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AppointExecution {
    private long bookId;                //预约图书id
    private int state;                  //预约状态
    private String stateInfo;           //预约状态描述
    private Appointment appointment;    //预约成功对象

    public AppointExecution(long bookId, AppointmentStateEnum stateEnum) {
        this.bookId = bookId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public AppointExecution(long bookId, AppointmentStateEnum stateEnum, Appointment appointment){
        this.bookId = bookId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.appointment = appointment;
    }

}
