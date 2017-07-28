package net.catchpole.console;

import java.util.Date;

public class TimeTracker {
    private long start;

    public TimeTracker() {
        this.start = System.currentTimeMillis();
    }

    public String getStartDate() {
        return new Date(start).toString();
    }

    public String getHoursMinsSeconds() {
        long milliseconds = getElapsed();
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        return String.format("%d:%02d:%02d",hours,minutes,seconds);
    }

    public long getElapsed() {
        return System.currentTimeMillis() - start;
    }

    public static void main(String[] args) throws Exception {
        TimeTracker timeTracker = new TimeTracker();
        Thread.sleep(3000);
        System.out.println(timeTracker.getHoursMinsSeconds());
        System.out.println(timeTracker.getStartDate());
    }
}
