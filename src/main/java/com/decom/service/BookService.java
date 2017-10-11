package com.decom.service;

import com.decom.dto.AppointExecution;
import com.decom.entity.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface BookService {
    /**
     * 根据ID查询图书
     * @param bookId
     * @return
     */
    Book getBookById(long bookId);

    /**
     * 查询所有图书
     * @return
     */
    List<Book> getBookList();

    /**
     * 预约图书
     * @param bookId
     * @param studentId
     * @return
     */
    AppointExecution appoint(long bookId, long studentId);
}
