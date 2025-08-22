package com.exemple;

import com.exemple.menu.DictionaryMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DictionaryMenu dictionaryMenu = context.getBean("dictionaryMenu", DictionaryMenu.class);
        dictionaryMenu.run();
    }
}
