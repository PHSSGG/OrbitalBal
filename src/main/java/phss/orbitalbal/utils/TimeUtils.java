package phss.orbitalbal.utils;

public class TimeUtils {

    public static String getTimeString(long time) {
        int seconds = (int) (time / 1000L % 60);
        int minutes = (int) (time / 60000L % 60L);
        int hours = (int) (time / 3600000L % 24L);
        int days = (int) (time / (60 * 60 * 24 * 1000));

        String secondString = "s";
        String minuteString = "m";
        String hourString = "h";
        String dayString = "d";

        StringBuilder builder = new StringBuilder();
        if (days > 0) builder.append(days).append(dayString).append(" ");
        if (hours > 0) builder.append(hours).append(hourString).append(" ");
        if (minutes > 0) builder.append(minutes).append(minuteString).append(" ");
        builder.append(seconds).append(secondString);

        return builder.toString();
    }

}
