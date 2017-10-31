package com.decom.chunks.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 *
 * @author Administrator
 */
public class CustomAnnotationTest {
    public static void main(String[] args) {
        genericTest();
    }

    @Override
    @CustomAnnotation(author = "admin au", date = "oct 16 2017", version = 1, comments = "")
    public String toString() {
        return  "override tostring methord!";
    }

    @CustomAnnotation(date = "oct 16 2017", comments = "deprecated methord!")
    public static void oldMethord(){
        System.out.println("old methord, do not use it!");
    }

    @CustomAnnotation(author = "micro admin", date = "oct 16 2017", comments = "generic test")
    public static void genericTest(){
        List list = new ArrayList();
        list.add("abc");
        oldMethord();
    }

}
