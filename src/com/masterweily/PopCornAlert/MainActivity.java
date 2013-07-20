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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        statusView = (TextView)findViewById(R.id.statusTextView);
        toggleButton = (Button)findViewById(R.id.toggleBtn);
        popsCountTextView = (TextView)(findViewById(R.id.popsCountTextView));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePopcorning();
            }
        });
    }



    // Activity Callbacks

    public void popcorningStoped() {
        showStatus("Stopping Popcorning");
        toggleButton.setText("Start Popcorning");
    }

    public void popcorningStarted() {
        popsCount = 0;
        showPopsCount();
        showStatus("Starting Popcorning");
        toggleButton.setText("Stop Popcorning");
    }


    public void popCornIsReady() {
        uiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                showStatus("Ready");
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

        // controller actions

    private void togglePopcorning() {
        if (isPopcorning()) stopPopcorning();
        else                startPopcorning();
    }

    private void stopPopcorning() { popcorning.stop(); }

    private void startPopcorning() { popcorning.run(); }

        // view actions

    private void showStatus(String status) { statusView.setText(status); }

    private void showPopsCount() { popsCountTextView.setText(""+popsCount); }


    // Activity Queries

    private boolean isPopcorning() { return popcorning.isRunning(); }
}
