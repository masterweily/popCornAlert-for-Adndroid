package com.masterweily.PopCornAlert;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    PopCorning popcorning = new PopCorning(this);
    TextView statusView;
    Button toggleButton;
    private int popsCount;
    private TextView popsCountTextView;
    final Handler uiThreadHandler = new Handler();
    private AlarmWidget alarmWidget;

    private enum Status { READY, POPCORNING, REST }
    private Status status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmWidget = new AlarmWidget(this);
        setContentView(R.layout.main);

        statusView = (TextView)findViewById(R.id.statusTextView);
        toggleButton = (Button)findViewById(R.id.toggleBtn);
        popsCountTextView = (TextView)(findViewById(R.id.popsCountTextView));

        status = Status.REST;

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { togglStatus();    }
        });
    }


    // Activity Callbacks

    public void popcorningStoped() { if (status != Status.READY) setStatus(Status.REST); }

    public void popcorningStarted() {
        popsCount = 0;
        showPopsCount();
        setStatus(Status.POPCORNING);
    }


    public void popCornIsReady() {
        uiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                setStatus(Status.READY);
                stopPopcorning();
                startAlarm();
            }
        });
    }

    public void popDetected() {
        popsCount ++;
        uiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                showPopsCount();
            }
        });
    }


    // Activity Actions

    private void setStatus(Status newStatus) {
        this.status = newStatus;
        showStatus();
        refreshButtonText();
    }

    private void togglStatus() {
        switch (status) {
            case READY:
                stopAlarm();
                setStatus(Status.REST);
                break;
            case POPCORNING:
                stopPopcorning();
                break;
            case REST:
                startPopcorning();
                break;
        }
    }

    // controller actions

    private void togglePopcorning() {
        if (isPopcorning())
            stopPopcorning();
        else
            startPopcorning();
    }

    private void stopPopcorning() { popcorning.stop(); }

    private void startPopcorning() { popcorning.run(); }

    // view actions

    private void startAlarm() { this.alarmWidget.start(); }

    private void stopAlarm() { this.alarmWidget.stop(); }

    private void showPopsCount() { popsCountTextView.setText(""+popsCount); }

    private void refreshButtonText() {
        String buttonText = "";
        switch (status){
            case READY:
                buttonText = "Stop Alarm";
                break;
            case POPCORNING:
                buttonText = "Stop Popcorning";
                break;
            case REST:
                buttonText = "Start Popcorning";
                break;
        }
        toggleButton.setText(buttonText);
    }

    private void showStatus() {
        String statusText = "";
        switch (status){
            case READY:
                statusText = "Ready";
                break;
            case POPCORNING:
                statusText = "Popcorning";
                break;
            case REST:
                statusText = "Waiting";
                break;
        }
        statusView.setText(statusText);
    }

    // Activity Queries

    private boolean isPopcorning() { return popcorning.isRunning(); }
}
