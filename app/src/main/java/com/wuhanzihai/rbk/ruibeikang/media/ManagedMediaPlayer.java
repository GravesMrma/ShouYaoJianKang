package com.wuhanzihai.rbk.ruibeikang.media;

import android.media.MediaPlayer;
import android.util.Log;

import com.eightbitlab.rxbus.Bus;
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent;

import java.io.IOException;

public class ManagedMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    public enum Status {
        IDLE,   //   原始状态
        INITIALIZED,  // 设置资源完毕
        STARTED, // 开始播放了
        PAUSED, //  正在暂停中
        STOPPED, // 停止状态中
        COMPLETED // 播放完成
    }

    private Status mState;

    private OnCompletionListener mOnCompletionListener;

    public ManagedMediaPlayer() {
        super();
        mState = Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void reset() {
        super.reset();
        mState = Status.IDLE;
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = Status.INITIALIZED;
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }

    @Override
    public void start() {
        super.start();
        Log.e("播放器","开始播放");
        mState = Status.STARTED;
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = Status.COMPLETED;
        Log.e("播放器","播放完成");
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = Status.STOPPED;
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = Status.PAUSED;
        Bus.INSTANCE.send(new MusicStateEvent(mState));
    }
//
//    public void setState(Status mState) {
//        this.mState = mState;
//    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == Status.COMPLETED;
    }

}