package com.sik.sihols.report;

import com.sik.sihols.core.AnnualLeave;
import com.sik.sihols.core.HalfDay;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnualLeaveManager {
    private static final String PASTED_HOLS = "src/main/resources/pasted_hols.txt";
    private static final String RGX_SPACES = "\\s+";
    private static final String SPC = " ";
    private static final String AM = "AM";
    private static final String PM = "PM";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
    private static final String AM_START_TIME = " 08:00:00 AM";
    private static final String PM_START_TIME = " 12:00:00 PM";

    private static final int CURRENT_YEAR = 2019;
    private static float ANNUAL_ENTITLEMENT = 31.0F;

    private LocalDateTime holidayYearStart = LocalDateTime.of(CURRENT_YEAR, 4,1, 0,0,0);
    private LocalDateTime today = LocalDateTime.now();

    private float totalBooked;
    private float totalTaken;

    private Collection<AnnualLeave> annualLeaveSet;

    public AnnualLeaveManager() {
        this.totalBooked = 0.0F;
        this.totalTaken = 0.0F;
        this.annualLeaveSet = this.readAnnualLeave(PASTED_HOLS);
    }

    public Collection<AnnualLeave> getAnnualLeaveSet() {
        return this.annualLeaveSet;
    }

    public float getTotalBooked() {
        return this.totalBooked;
    }

    public float getTotalTaken() {
        return this.totalTaken;
    }

    public float getAnnualEntitlement() {
        return ANNUAL_ENTITLEMENT;
    }

    public LocalDateTime getHolidayYearStart() { return this.holidayYearStart; }

    public LocalDateTime getToday() { return this.today; }

    private Collection<AnnualLeave> readAnnualLeave(String fileName) {
        return annualLeaveSet = this.readLineByLine(fileName).stream()
                .map(a -> this.buildAnnualLeave(a))
                .filter(a -> a.getStartDate().isAfter(holidayYearStart))
                .sorted()
                .map(this::updateStats)
                .collect(Collectors.toList());
    }

    private AnnualLeave buildAnnualLeave(final String line) {
        String[] fields = line.split(RGX_SPACES);
        if (fields.length==10) {
            AnnualLeave annualLeave =  AnnualLeave.builder()
                    .startDate(LocalDateTime.parse(fields[0] + (fields[1].equalsIgnoreCase(AM) ? AM_START_TIME : PM_START_TIME), FORMATTER))
                    .startHalf(HalfDay.valueOf(fields[1]))
                    .endDate(LocalDateTime.parse(fields[2] + (fields[3].equalsIgnoreCase(PM) ? PM_START_TIME : AM_START_TIME), FORMATTER))
                    .endHalf(HalfDay.valueOf(fields[3]))
                    .description(fields[4] + SPC + fields[5])
                    .altDescription(fields[6] + SPC + fields[7])
                    .durationDays(Float.parseFloat(fields[8]))
                    .durationHours(Float.parseFloat(fields[9]))
                    .build();
            return annualLeave;
        } else {
            return AnnualLeave.builder().build();
        }
    }

    private AnnualLeave updateStats(AnnualLeave annualLeave) {
        this.totalBooked = this.totalBooked + annualLeave.getDurationDays();
        if (annualLeave.getStartDate().isBefore(today)) {
            this.totalTaken = this.totalTaken + annualLeave.getDurationDays();
        }
        return annualLeave;
    }

    private Set<String> readLineByLine(String filePath) {
        Set<String> lines = new HashSet<>();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> lines.add(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
