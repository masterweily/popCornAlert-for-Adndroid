package com.masterweily.PopCornAlert;


import android.util.Log;

import java.util.ArrayList;
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
    private static final int MIN_POPS = 42;
    private static final long MAX_POPS_INTERVAL = 3000;
    private final PopCorning controller;
    private Timer timer;
    private PopList poplist;

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
        this.poplist = new PopList();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                inspectPopcornIsReady();
            }
        },1000L,100L);
    }

    private void inspectPopcornIsReady() { if (isPopcornReady()) controller.popCornIsReady(); }

    private boolean isPopcornReady() { return poplist.size() > MIN_POPS && (poplist.lastInterval() > MAX_POPS_INTERVAL || poplist.veryLastInterval() > MAX_POPS_INTERVAL); }

    public void close() { timer.cancel(); }

    private void registerPop() { poplist.registerPop(); }

    // Callbacks

    public void popDetected() { registerPop(); }

    private class PopList extends ArrayList<Long> {

        public void registerPop(){ poplist.add(new Long(System.currentTimeMillis())); }

        public long lastInterval() { return get(size() - 1) - get(size() - 2); }

        public long veryLastInterval() { return System.currentTimeMillis() - get(size() - 1);  }
    }

}
