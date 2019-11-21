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
        for (int i = 0; i < 0; i++) {
            int j = i;
            System.out.println("j="+j);
        }
    }
    @Test
    public void testZhuanyi() {
        String ss ="2019-3-8_1.txt";
        String[] segments = ss.split("\\.");
        System.out.println("segments:"+segments[0]);
        System.out.println("segments:"+segments.length);
        String[] fileSegments = segments[0].split("\\_");
        System.out.println("fileSegments:"+fileSegments[0]);
        System.out.println("fileSegments:"+fileSegments.length);
    }
}