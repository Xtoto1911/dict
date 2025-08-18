package com.exemple;

import com.exemple.dictionary.BaseDictionary;
import com.exemple.dictionary.LatinDictionary;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        File file = new File("latin.txt");
        if(!file.exists()){
            file.createNewFile();
        }

        BaseDictionary dictionary = new LatinDictionary(file.getAbsolutePath());
        dictionary.add("abcd", "asdasdasd");
        System.out.println(dictionary.toString());


    }
}
