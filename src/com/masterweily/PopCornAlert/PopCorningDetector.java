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
    private static final double POP_AMPLITUDE = 5;
    private static final long SAMPLE_RATE = 500L;
    private final PopCorning controller;
    private final SoundRecorder soundRecorder;
    private Timer timer;

    public PopCorningDetector(PopCorning controller) {
        super();
        timer = new Timer();
        this.controller = controller;
        this.soundRecorder = new SoundRecorder();
        this.run();
    }

    @Override
    public void run() {
        this.soundRecorder.run();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { if (detectPop()) popDetected(); } }, SAMPLE_RATE, SAMPLE_RATE);
    }

    public boolean detectPop() { return (soundRecorder.getMaxAmplitude() >= POP_AMPLITUDE); }

    public void popDetected() { controller.popDetected(); }

    public void close() { timer.cancel(); soundRecorder.close(); }


}
