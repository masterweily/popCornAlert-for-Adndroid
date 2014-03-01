package com.masterweily.PopCornAlert;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by yaronweil on 3/1/14.
 */
public class AlarmWidget {
    static Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    private final Context context;
    private MediaPlayer player;
    private AudioManager audioManager;

    public AlarmWidget(Context context){
        this.context = context;
    }

    public void start() {
        stop();
        this.player = new MediaPlayer();
        this.audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        try {
            player.setDataSource(context,alert);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            try {
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            player.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
