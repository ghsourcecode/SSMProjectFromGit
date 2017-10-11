package com.decom;

import com.decom.dao.BookDao;
import com.decom.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */
//@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml", "classpath:spring.xml"})
public class BookDaoTest extends BaseTest{
    @Autowired
    private BookDao bookDao;

    @Test
    public void testselectByPrimaryKey() {
        long bookId = 1000;
        Book book = bookDao.selectByPrimaryKey(bookId);
        System.out.println(book);
    }

    @Test
    public void testQueryAll() {
        List<Book> resultBookList = bookDao.queryAll(0, 2);
        for (int i = 0; i < resultBookList.size(); i++) {
            Book book = resultBookList.get(i);
            System.out.println(book);
        }
    }

    @Test
    public void testReduceNumber() {
        long bookId = 1;
        int updateRecodeNum = bookDao.reduceNumber(bookId);
        System.out.println("update recode num: " + updateRecodeNum);
    }

}
