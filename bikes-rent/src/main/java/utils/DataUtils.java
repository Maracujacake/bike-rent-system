package utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static String formatLocalDateTime(LocalDateTime time) {
        String dataHoraFormatada = time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return dataHoraFormatada;
    }

    public static LocalDateTime formatTime(String time) {

        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public static String getformatDate(Date date) {
        String dataFormatada = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return dataFormatada;
    }

    public static boolean checkFullHour(LocalDateTime registro) {
        if (registro.getMinute() != 0 || registro.getSecond() != 0) {
            // throw new IllegalArgumentException("O registro deve estar na hora cheia (ex:
            // 13:00, 15:00).");
            return false;
        }
        return true;
    }

    public static String parseLocalDate(LocalDateTime date) {
        // 2024-07-16T18:11:21.073532 -> 2024-06-30T16:30
        String data = date.toString();
        data.lastIndexOf(":");
        data = data.substring(0, data.lastIndexOf(":"));
        return data;
    }

    // 2024-07-23T10:00 -> 23/07/2024 [10:00]
    public static String parseEmailData(String data) {
        String[] dataHora = data.split("T");
        String[] dataSplit = dataHora[0].split("-");
        return dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0] + " [" + dataHora[1] + "]";
    }
}
