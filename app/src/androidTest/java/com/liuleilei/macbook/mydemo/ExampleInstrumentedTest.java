package com.liuleilei.macbook.mydemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.liuleilei.macbook.mydemo", appContext.getPackageName());
    }
    @Test
    public void testCompare() {
        // Context of the app under test.
        int result = 1 << 1;
        System.out.print("result:"+(1<<1));
    }
    @Test
    public void testFor(){
        for (;;){
            Log.v("index","indext:");
        }
    }
    @Test
    public void test1()  {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(Integer.valueOf(i));
        }

        // 复现方法一
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                arrayList.remove(integer);
            }
        }

        // 复现方法二
        iterator = arrayList.iterator();
        for (Integer value : arrayList) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                arrayList.remove(integer);
            }
        }
    }
    @Test
    public void test2() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(Integer.valueOf(i));
        }

        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                iterator.remove();
            }
        }
        int _a;
        int String = 0;
        String ++;

    }
    @Test
    public void test3() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(Integer.valueOf(i));
        }

        ListIterator<Integer> iterator = arrayList.listIterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                iterator.set(Integer.valueOf(6));
                iterator.remove();
                iterator.add(integer);
            }
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
