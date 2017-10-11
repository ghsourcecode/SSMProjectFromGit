package com.decom;

import com.decom.dto.AppointExecution;
import com.decom.service.BookService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BookServiceImplTest extends BaseTest {
    @Autowired
    private BookService bookService;

    @Test
    public void testAppoint() {
        long bookId = 1003;
        long studentId = 1234567820;

        AppointExecution appointExecution = bookService.appoint(bookId, studentId);
        System.out.println(appointExecution);
    }
}
