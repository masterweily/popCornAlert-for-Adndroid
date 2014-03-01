package com.masterweily.PopCornAlert;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: masterweily
 * Date: 7/20/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopCorningInspector extends Thread {
    private final PopCorning controller;
    private int popsCount;
    private Timer timer;


    // Constructors

    public PopCorningInspector(PopCorning controller) {
        super();
        this.controller = controller;
        timer = new Timer();
        this.run();
    }

    // Actions

    @Override
    public void run() {
        popsCount = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                inspectPopcornIsReady();
            }
        },1000L,100L);
    }

    private void inspectPopcornIsReady() {
        Log.d("inspectPopcorn", "count: " + popsCount + ", ready: " + (isPopcornReady() ? "Yes" : "No"));
        if (isPopcornReady()) controller.popCornIsReady();
    }

    private boolean isPopcornReady() { return popsCount > 2; }

    public void close() { timer.cancel(); }

    private void registerPop() { popsCount ++; }

    // Callbacks

    public void popDetected() { registerPop(); }




}
