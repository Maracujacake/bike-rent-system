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
}
