package com.interlink.incamp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MonthCalendar {
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_RESET = "\u001B[0m";
    private final List<DayOfWeek> HOLIDAYS = Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY);
    private final Integer FIRST_DAY_OF_MONTH_VALUE = 1;
    private final Integer year;
    private final Integer month;

    public MonthCalendar(Integer month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException();
        }
        this.month = month;
        this.year = Year.now().getValue();
    }

    @Override
    public String toString() {
        return getTableHeader() + "\n" +
                getMonthAsFormattedString();
    }

    private String getTableHeader() {
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        List<String> shortDaysOfWeek = new ArrayList<>(Arrays.asList(daysOfWeek))
                .stream()
                .map(dayOfWeek -> dayOfWeek.toString().substring(0, 3))
                .collect(toList());

        return String.format("%-6s%-6s%-6s%-6s%-6s" + ANSI_RED + "%-6s%-6s" + ANSI_RESET,
                shortDaysOfWeek.toArray());
    }

    private String getMonthAsFormattedString() {
        DayOfWeek firstDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH_VALUE).getDayOfWeek();
        StringBuilder result = new StringBuilder(getBlankDaysOfWeek(firstDayOfMonth));

        int daysInMonth = MonthDay.of(month, 1).getMonth().length(Year.isLeap(year));
        int dayOfWeekCounter = firstDayOfMonth.getValue();
        int dayCounter = 1;
        while (dayCounter <= daysInMonth) {
            result.append(getStringRepresentationOfMonthDay(dayCounter++));

            if (++dayOfWeekCounter > 7) {
                result.append("\n");
                dayOfWeekCounter = 1;
            }
        }
        return result.toString();
    }

    private String getStringRepresentationOfMonthDay(int day) {
        LocalDate currentLocalDate = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek();
        if (LocalDate.now().equals(currentLocalDate)) {
            return ANSI_YELLOW
                    .concat(String.format("%-6s", day))
                    .concat(ANSI_RESET);
        } else if (HOLIDAYS.contains(dayOfWeek)) {
            return ANSI_RED
                    .concat(String.format("%-6s", day))
                    .concat(ANSI_RESET);
        } else {
            return String.format("%-6d", day);
        }
    }

    private String getBlankDaysOfWeek(DayOfWeek firstDayOfWeek) {
        StringBuilder result = new StringBuilder();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.compareTo(firstDayOfWeek) < 0) {
                result.append(String.format("%-6s", ""));
            } else {
                break;
            }
        }
        return result.toString();
    }
}
