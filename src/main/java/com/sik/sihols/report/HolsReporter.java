package com.sik.sihols.report;

import com.sik.sihols.core.AnnualLeave;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HolsReporter {
    private static final DecimalFormat DF = new DecimalFormat("###.#");
    private static final String F_AL = "%16s - %16s %3s";
    private static final float DIY = 365.25F;

    public static void main(String[] args) {

        AnnualLeaveManager mgr = new AnnualLeaveManager();

        System.out.println(String.format(F_AL, "Start", "End","Days"));
        System.out.println(String.format(F_AL, "-----", "---","----"));

        mgr.getAnnualLeaveSet().stream()
                .forEach(a -> print(a));

        System.out.println("");
        System.out.println(String.format("Total booked: %s/%s (%s%%)", mgr.getTotalBooked(),
                mgr.getAnnualEntitlement(),
                DF.format((mgr.getTotalBooked()/mgr.getAnnualEntitlement())*100)));
        System.out.println(String.format("Total taken : %s/%s (%s%%)", mgr.getTotalTaken(),
                mgr.getAnnualEntitlement(),
                DF.format((mgr.getTotalTaken()/mgr.getAnnualEntitlement())*100)));
        System.out.println(String.format("Days elapsed: %s (%s%%)", mgr.getHolidayYearStart().until(mgr.getToday(), ChronoUnit.DAYS),
                DF.format((mgr.getHolidayYearStart().until(mgr.getToday(), ChronoUnit.DAYS)/DIY)*100)));

    }

    private static void print(AnnualLeave annualLeave) {
        System.out.println(String.format("%16s - %16s %3s", annualLeave.getStartDate(), annualLeave.getEndDate(),annualLeave.getDurationDays()));
    }

}
