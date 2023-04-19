package com.pooa.model;

import java.util.concurrent.TimeUnit;

public class Clock extends Thread {
    private int minutes;
    private int seconds;
    private int lastMin = -1;
    private int lastSec = -1;
    private String clock;
    private boolean isRunning;

    public Clock(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            super.start();
        }
    }

    @Override
    public void run() {
        while (minutes >= 0 && !Thread.currentThread().isInterrupted()) {
            clock = String.format("%02d:%02d", minutes, seconds);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            lastSec++;
            seconds--;

            if (seconds < 0) {
                seconds = 59;
                minutes--;

                if (lastSec == 59) {
                    lastSec = 0;
                    lastMin++;
                }
            }
        }
        interrupt();
    }

    public String getClock() {
        return clock;
    }

    public String getTime() {
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getLastMin() {
        if (lastMin == -1) {
            return 0;
        }
        return lastMin;
    }

    public void setLastMin(int lastMin) {
        this.lastMin = lastMin;
    }

    public int getLastSec() {
        if (lastSec == -1) {
            return 0;
        }
        return lastSec;
    }

    public void setLastSec(int lastSec) {
        this.lastSec = lastSec;
    }
}
