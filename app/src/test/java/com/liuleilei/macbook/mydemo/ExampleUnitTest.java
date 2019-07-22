package com.liuleilei.macbook.mydemo;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG  = ExampleUnitTest.class.getSimpleName();
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void add(){
        System.out.print("3+3"+(3+3));
    }
    @Test
    public void forTest(){
        for (int i = 0; i < 10; i++) {
            int j = i;
        }
    }
}