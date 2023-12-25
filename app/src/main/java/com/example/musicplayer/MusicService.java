package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;
//这是一个Service服务类
public class MusicService extends Service {
    //声明一个MediaPlayer引用
    private MediaPlayer player;
    //声明一个计时器引用
    private Timer timer;
    //构造函数
    private int currentIndex = 0;
    // Assuming this is the index of the current song in your playlist
    private int[] musicArray = {R.raw.music0, R.raw.music1, R.raw.music2, R.raw.music3, R.raw.music4,R.raw.music5};
    public MusicService() {}
    @Override
    public  IBinder onBind(Intent intent){
        return new MusicControl();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        //创建音乐播放器对象
        player=new MediaPlayer();
    }
    //添加计时器用于设置音乐播放器中的播放进度条
    public void addTimer(){
        //如果timer不存在，也就是没有引用实例
        if(timer==null){
            //创建计时器对象
            timer=new Timer();
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    if (player==null) return;
                    int duration=player.getDuration();//获取歌曲总时长
                    int currentPosition=player.getCurrentPosition();//获取播放进度
                    Message msg= MusicPlayerActivity.handler.obtainMessage();//创建消息对象

                    Bundle bundle=new Bundle();//将音乐的总时长和播放进度封装至bundle中
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);

                    msg.setData(bundle);//再将bundle封装到msg消息对象中

                    MusicPlayerActivity.handler.sendMessage(msg);//最后将消息发送到主线程的消息队列
                }
            };

            timer.schedule(task,5,500);
        }
    }
    //Binder是一种跨进程的通信方式
    class MusicControl extends Binder{
        public void play(int i){//String path
            Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/"+"music"+i);
            try{
                //重置音乐播放器
                player.reset();
                //加载多媒体文件
                player=MediaPlayer.create(getApplicationContext(),uri);
                player.start();//播放音乐
                addTimer();//添加计时器
            }catch(Exception e){
                e.printStackTrace();
            }
            // Create an Intent for the broadcast
            Intent intent = new Intent("com.example.music.UPDATE_UI");
            intent.putExtra("position", i);
            // Send the broadcast
            sendBroadcast(intent);
        }
        //下面的暂停继续和退出方法全部调用的是MediaPlayer自带的方法
        public void pausePlay(){
            player.pause();//暂停播放音乐
        }
        public void continuePlay(){
            player.start();//继续播放音乐
        }
        public void playPrevious() {
            if (player != null) {
                currentIndex = (currentIndex - 1 + musicArray.length) % musicArray.length;
                play(currentIndex);
            }
        }
        public void playNext() {
            if (player != null) {
                currentIndex = (currentIndex + 1) % musicArray.length;
                play(currentIndex);
            }
        }
        public void seekTo(int progress){
            player.seekTo(progress);//设置音乐的播放位置
        }
    }
    //销毁多媒体播放器
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(player==null) return;
        if(player.isPlaying()) player.stop();
        player.release();
        player=null;
    }
}

