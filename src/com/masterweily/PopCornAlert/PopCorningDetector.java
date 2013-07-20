package com.masterweily.PopCornAlert;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: masterweily
 * Date: 7/20/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopCorningDetector extends Thread {
    private final PopCorning controller;
    private Timer timer;

    public PopCorningDetector(PopCorning controller) {
        super();
        timer = new Timer();
        this.controller = controller;
        this.run();
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                popDetected();
            }
        }, 1000L, 1000L);
    }

    public void popDetected() { controller.popDetected(); }

    public void close() { timer.cancel(); }
}
