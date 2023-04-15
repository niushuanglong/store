package com.niu;

import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("sdasdf","的沙发斯蒂芬");
        System.err.println(map);
        int i  = 1;
        System.err.println(i++);
        System.err.println(i);
        Thread thread = new Thread();
        System.err.println(thread.getName());
        thread.start();



    }
}
