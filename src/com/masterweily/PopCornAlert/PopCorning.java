package com.masterweily.PopCornAlert;

/**
 * Created with IntelliJ IDEA.
 * User: masterweily
 * Date: 7/20/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopCorning implements Runnable {

    private final MainActivity activity;
    private boolean isRunning;
    private PopCorningDetector detector;
    private PopCorningInspector inspector;

    // Constructors

    public PopCorning(MainActivity activity){
        this.activity   = activity;
        this.isRunning  = false;
    }

    // Actions

    @Override
    public void run() {
        inspector = new PopCorningInspector(this);
        detector = new PopCorningDetector(this);
        isRunning = true;
        activity.popcorningStarted();
    }

    public void stop() {
        detector.close();
        inspector.close();
        isRunning = false;
        activity.popcorningStoped();
    }

    // Queries

    public boolean isRunning() { return isRunning; }

    // Callbacks

    public void popDetected() {
        inspector.popDetected();
        activity.popDetected();
    }

    public void popCornIsReady() { activity.popCornIsReady(); }
}
