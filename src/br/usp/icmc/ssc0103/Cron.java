//DEPRECATED

package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Cron
{
    public static Date getCheckInDate(UserType userType, Date checkOutDate)
    {
        long dateSums = checkOutDate.getTime();
        switch (userType) {
            case TUTOR:
                dateSums += 5.184e+9;
                break;

            case STUDENT:
                dateSums += 1.296e+9;
                break;

            case COMMUNITY:
                dateSums += 1.296e+9;
                break;
        }
        return new Date(dateSums);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillis = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
};
