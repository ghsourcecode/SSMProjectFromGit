package com.decom;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.decom.dao.AppointmentDao;
import com.decom.entity.Appointment;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AppointmentDaoTest extends BaseTest {
    @Autowired
    private AppointmentDao appointmentDao;

    @Test
    public void testInsertAppointment(){
        long bookId = 1003;
        long studentId = 1234567890;
        int insert = appointmentDao.insertAppointment(bookId, studentId);
        System.out.println("insert: " + insert);
    }

    @Test
    public void testQueryByKeyWithBook() {
        long bookId = 1003;
        long studentId = 1234567890;
        Appointment appointment = appointmentDao.queryByKeyWithBook(bookId, studentId);
        System.out.println(appointment);
        System.out.println(appointment.getBookId());
    }
}
