package com.decom.controller;

import com.decom.dto.AppointExecution;
import com.decom.dto.Result;
import com.decom.entity.Book;
import com.decom.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
@Controller
@RequestMapping("/book")
public class BookController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    private String list(Model model){
        List<Book> list = bookService.getBookList();
        model.addAttribute("list", list);

        return "list";
    }

    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    private String detail(@PathVariable("bookId") Long bookId, Model model){
        if(bookId == null){
            return "redirect:/book/list";
        }

        Book book = bookService.getBookById(bookId);
        if(book == null){
            return "redirect:/book/list";
        }

        model.addAttribute("book", book);

        return "detail";
    }

    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.GET, produces = {"application/json; charset=utf=8"})
    @ResponseBody
    private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId){
        if(bookId == null || studentId == null){
            return new Result<AppointExecution>(false, "学号不能为空");
        }

        AppointExecution execution = bookService.appoint(bookId, studentId);

        return new Result<AppointExecution>(true, execution);
    }

}
