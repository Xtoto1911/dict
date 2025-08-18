package com.exemple;

import com.exemple.dictionary.BaseDictionary;
import com.exemple.dictionary.LatinDictionary;
import com.exemple.menu.DictionaryMenu;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        DictionaryMenu dictionaryMenu = new DictionaryMenu();
        dictionaryMenu.run();
    }
}
