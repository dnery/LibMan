package br.usp.icmc.ssc0103;

import java.io.IOException;

public class Watchdog implements Runnable
{
    private static final Watchdog watchdog = new Watchdog();

    private volatile Thread watchdogThread = null;

    private Watchdog()
    {
        System.out.println("Watchdog: Scheduled database updates enabled");
    }

    public static Watchdog getInstance() { return watchdog; }

    public void start()
    {
        if (watchdogThread == null) {
            watchdogThread = new Thread(this);
            watchdogThread.start();
        }
    }

    public void stop()
    {
        if (watchdogThread != null) {
            Thread swap = watchdogThread;
            watchdogThread = null;
            swap.interrupt();
        }
    }

    @Override
    public void run()
    {
        while (watchdogThread == Thread.currentThread()) {
            try {
                Thread.sleep(15000);
                Database.getInstance().serializeAndUpdate();
            } catch (InterruptedException e) {
                System.out.println("> Watchdog stopped");
            } catch (IOException e) {
                Formatter.outputError(e.getMessage());
            }
        }
    }
}
