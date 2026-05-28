package com.nct.trenx.utils;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

/**
 * Quản lý timer tổng và rest cho màn tập luyện.
 */
public class TrainingTimerHelper {

    public static final long DEFAULT_REST_MS = 45_000;

    public interface Listener {
        void onElapsedTick(String formattedTime);

        void onRestTick(int secondsLeft);

        void onRestFinished();
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable elapsedRunnable;
    private CountDownTimer restTimer;
    private long startTimeMillis;
    private long restTimeLeftMs = DEFAULT_REST_MS;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void startElapsedTimer() {
        startTimeMillis = System.currentTimeMillis();
        stopElapsedTimer();
        elapsedRunnable = new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    long elapsed = System.currentTimeMillis() - startTimeMillis;
                    listener.onElapsedTick(DateUtils.formatElapsedMillis(elapsed));
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(elapsedRunnable);
    }

    public void stopElapsedTimer() {
        if (elapsedRunnable != null) {
            handler.removeCallbacks(elapsedRunnable);
            elapsedRunnable = null;
        }
    }

    public void startRestTimer() {
        startRestTimer(restTimeLeftMs);
    }

    public void startRestTimer(long durationMs) {
        restTimeLeftMs = durationMs;
        cancelRestTimer();
        restTimer = new CountDownTimer(restTimeLeftMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                restTimeLeftMs = millisUntilFinished;
                if (listener != null) {
                    listener.onRestTick((int) (millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onRestFinished();
                }
            }
        }.start();
    }

    public void adjustRestTime(long deltaMs) {
        restTimeLeftMs += deltaMs;
        if (restTimeLeftMs < 0) {
            restTimeLeftMs = 0;
        }
        startRestTimer(restTimeLeftMs);
    }

    public void resetRestDuration() {
        restTimeLeftMs = DEFAULT_REST_MS;
    }

    public void cancelRestTimer() {
        if (restTimer != null) {
            restTimer.cancel();
            restTimer = null;
        }
    }

    public void release() {
        stopElapsedTimer();
        cancelRestTimer();
        listener = null;
    }
}
