package ru.noinknight.clock;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ShiftHandler {

        static long getDays(Date userDate){
            Date currentDate = new Date(System.currentTimeMillis());

            long daysMills=Math.abs(currentDate.getTime()-userDate.getTime());
            long days= TimeUnit.DAYS.convert(daysMills,TimeUnit.MILLISECONDS);
            return days;
        }

       static boolean daysChecker(long days){


           if (days == 0 ||
                   days == 1 ||
                   days % 4 == 0 ||
                   (days - 1) % 4 == 0
           ) return true;
           else return false;

        }




}
