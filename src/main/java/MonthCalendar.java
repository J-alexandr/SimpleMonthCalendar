import java.time.*;
import java.util.Arrays;
import java.util.List;

public class MonthCalendar {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final Integer FIRST_DAY_OF_MONTH_VALUE = 1;
    private final Integer year;
    private final Integer month;
    private final List<DayOfWeek> holidays = Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY);

    public MonthCalendar(Integer month) {
        this.month = month;
        this.year = Year.now().getValue();
    }

    @Override
    public String toString() {
        return getTableHeader() + "\n" +
                getMonthAsFormattedString();
    }

    private String getTableHeader() {
        return String.format("%-6s%-6s%-6s%-6s%-6s" + ANSI_RED + "%-6s%-6s" + ANSI_RESET,
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
    }

    private String getMonthAsFormattedString() {
        int daysInMonth = MonthDay.of(month, 1).getMonth().length(isLeapYear());
        DayOfWeek firstDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH_VALUE).getDayOfWeek();

        StringBuilder result = new StringBuilder(getBlankDaysOfWeek(firstDayOfMonth));

        int dayOfWeekCounter = firstDayOfMonth.getValue();
        int dayCounter = 1;
        while (dayCounter <= daysInMonth) {
            LocalDate currentLocalDate = LocalDate.of(year, month, dayCounter);
            DayOfWeek ofWeek = LocalDate.of(year, month, dayCounter).getDayOfWeek();
            if (LocalDate.now().equals(currentLocalDate)) {
                result.append(ANSI_YELLOW)
                        .append(String.format("%-6s", dayCounter++))
                        .append(ANSI_RESET);
            } else if (holidays.contains(ofWeek)) {
                result.append(ANSI_RED)
                        .append(String.format("%-6s", dayCounter++))
                        .append(ANSI_RESET);
            } else {
                result.append(String.format("%-6d", dayCounter++));
            }

            if (++dayOfWeekCounter > 7) {
                result.append("\n");
                dayOfWeekCounter = 1;
            }
        }

        return result.toString();
    }

    private boolean isLeapYear() {
        int year = LocalDateTime.now().getYear();
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
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
