package com.mks.uplib.Service.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mks.uplib.C;
import com.mks.uplib.Service.SharedPreferencesServicer.SharedPreferencesServicer;
import com.mks.uplib.Shell;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class InitializeService extends Service {

    static int ONE_DAY = 24*60*60*1000;
    Date currentTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Long tmp_NextStartTime = getNextStartTime();
        Date date = new java.util.Date(tmp_NextStartTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("nextStartTime = " + tmp_NextStartTime + "[" + sdf.format(date) + "]");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime = Calendar.getInstance().getTime();



                long lastStartTime = 0;
                String savedLastStartTime = SharedPreferencesServicer.getSimplePreferences(getApplicationContext(), C.SPF_SESSION_LAST_START_TIME, C.SPF_KEY_LAST_START_TIME, "");
                if ((savedLastStartTime != null) && (!savedLastStartTime.equals(""))) {
                    lastStartTime = Long.parseLong(savedLastStartTime);
                }else{
                    lastStartTime = Calendar.getInstance().getTime().getTime();
                    SharedPreferencesServicer.setSimplePreferences(getApplicationContext(), C.SPF_SESSION_LAST_START_TIME, C.SPF_KEY_LAST_START_TIME, "" + lastStartTime);
                }

                long nextStartTime = getNextStartTime();

                if ((currentTime.getTime() > nextStartTime) || (Math.abs(currentTime.getTime() - lastStartTime) > ONE_DAY) )
                {
                    lastStartTime = Calendar.getInstance().getTime().getTime();
                    SharedPreferencesServicer.setSimplePreferences(getApplicationContext(), C.SPF_SESSION_LAST_START_TIME, C.SPF_KEY_LAST_START_TIME, "" + lastStartTime);

                    nextStartTime = currentTime.getTime() + getPeriod();
                    SharedPreferencesServicer.setSimplePreferences(getApplicationContext(), C.SPF_SESSION_NEXT_START_TIME, C.SPF_KEY_NEXT_START_TIME, "" + nextStartTime);
                    new Shell().updateLibs(getApplicationContext(), null);

                }
            }
        }, 0, 20000);

        return super.onStartCommand(intent, flags, startId);
    }


    private long getNextStartTime()
    {
        long nextStartTime = 0;
        String savedNextStartTime = SharedPreferencesServicer.getSimplePreferences(getApplicationContext(), C.SPF_SESSION_NEXT_START_TIME, C.SPF_KEY_NEXT_START_TIME, "");
        if ((savedNextStartTime != null) && (!savedNextStartTime.equals(""))) {
            nextStartTime = Long.parseLong(savedNextStartTime);
        }else{
            nextStartTime = Calendar.getInstance().getTime().getTime() + generateShift();
            SharedPreferencesServicer.setSimplePreferences(getApplicationContext(), C.SPF_SESSION_NEXT_START_TIME, C.SPF_KEY_NEXT_START_TIME, "" + nextStartTime);
        }
        return nextStartTime;
    }

    private long getPeriod()
    {
        String period = SharedPreferencesServicer.getSimplePreferences(getApplicationContext(), C.SPF_SESSION_PERIOD, C.SPF_KEY_PERIOD, "");
        if((period == null) || (period.equals("")))
        {
            return 4*60*60*1000;
        }
        else
        {
            return Long.parseLong(period);
        }
    }

    private long generateShift()
    {
        Calendar now = Calendar.getInstance();
        Random r = new Random();
        String seed = "" + UUID.randomUUID().toString() + " " + now.toString();
        r.setSeed(seed.hashCode());
        return r.nextInt(ONE_DAY);
    }

}
