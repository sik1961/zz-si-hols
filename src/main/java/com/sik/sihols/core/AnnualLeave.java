package com.sik.sihols.core;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Builder
@ToString
public class AnnualLeave implements Comparable<AnnualLeave> {
    private LocalDateTime startDate;
    private HalfDay startHalf;
    private LocalDateTime endDate;
    private HalfDay endHalf;
    private String description;
    private String altDescription;
    private Float durationDays;
    private Float durationHours;

    @Override
    public int compareTo(AnnualLeave that) {

        if (this.startDate.isBefore(that.startDate)) {
            return -1;
        } else if (this.startDate.isAfter(that.startDate)) {
            return 1;
        } else if (this.startHalf.getValue() < that.startHalf.getValue()) {
            return -1;
        } else if (this.startHalf.getValue() > that.startHalf.getValue()) {
            return 1;
        } else if (this.endDate.isBefore(that.endDate)) {
            return -1;
        } else if (this.endDate.isAfter(that.endDate)) {
            return 1;
        } else if (this.endHalf.getValue() < that.endHalf.getValue()) {
            return -1;
        } else if (this.endHalf.getValue() > that.endHalf.getValue()) {
            return 1;
        } else {
            return this.description.hashCode() - that.description.hashCode();
        }
    }
}
