package com.example.a18_18079261_phungkhanhtoan_appmusic.SongNotify;

import static com.example.thigk_1.SongNotify.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.thigk_1.MainActivity;
import com.example.thigk_1.R;
import com.example.thigk_1.Song;

public class MyService extends Service {

    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_CLEAR = 3;
    public static final int ACTION_START = 4;


    private MediaPlayer mMediaPlayer;
    private boolean isPlaying;
    private Song mSong;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Toàn", "My Service onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getSerializableExtra("object_song") != null){
            Song song = (Song) intent.getSerializableExtra("object_song");

            if(song != null){
                mSong = song;
                startMusic(song);
                Log.e("TAG", "onStartCommand: " );

            }
        }

        int actionMusic = intent.getIntExtra("action_music_service",0);
        Log.e("Toàn","abbbbb" +actionMusic);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void startMusic(Song song) {
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(getApplicationContext(),song.getResource());
        }
        mMediaPlayer.start();
        isPlaying = true;
        sendActiontoActivity(ACTION_START);
    }

    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumMusic();
                break;
            case ACTION_CLEAR:
                stopSelf();
                sendActiontoActivity(ACTION_CLEAR);
                break;
            case  ACTION_START:
                startMusic(mSong);

                break;
        }
    }

    private void pauseMusic(){
        if(mMediaPlayer != null && isPlaying==true){
            mMediaPlayer.pause();
            isPlaying=false;

        }


    }
    private void resumMusic(){
        if(mMediaPlayer != null && isPlaying==false){
            mMediaPlayer.start();
            isPlaying = true;

        }
    }




    private PendingIntent getPendingIntent( Context context, int action){
        Intent intent = new Intent(this, MyReceiver.class);//truỳen action sang my Rêciver
        intent.putExtra("action_music",action);
        Log.e("Tincoder",action+"" );
        return PendingIntent.getBroadcast(context.getApplicationContext(),action, intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Tincoder","MyService onDestroy");
        if (mMediaPlayer != null){

            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void sendActiontoActivity(int action){
        Intent intent = new Intent("send_data_to_activity");
        Bundle bundle  =new Bundle();
        bundle.putSerializable("object_song",mSong);
        bundle.putBoolean("status_player",isPlaying);
        bundle.putInt("action_music",action);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
