package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Formatter
{
    public static void outputUsers(Stream<User> users){

    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillis = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
};
