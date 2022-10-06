package pandero.app.utils;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Calendario {
    private static final Logger log = LoggerSingleton.getInstance().getLogger(Calendario.class.getName());
    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final List<String> MESES = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");

    private Calendario() {

    }

    public static Map<String, String> getDate(String fecha) {
        Map<String, String> date = new HashMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatter.parse(fecha));

        } catch (ParseException e) {
            log.warn(e.getMessage());
            formatter = new SimpleDateFormat(DD_MM_YYYY);
            try {
                calendar.setTime(formatter.parse(fecha));
            } catch (ParseException ex) {
                log.warn(ex.getMessage());
            }

        }
        date.put("Mes", MESES.get(calendar.get(Calendar.MONTH)));
        date.put("Dia", String.valueOf(calendar.get(Calendar.DATE)));
        date.put("Anio", String.valueOf(calendar.get(Calendar.YEAR)));
        date.put("Hora", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        date.put("Minuto", String.valueOf(calendar.get(Calendar.MINUTE)));
        return date;
    }

    public static Map<String, String> getDateActual() {
        Map<String, String> date = new HashMap<>();
        TimeZone timeZone = TimeZone.getTimeZone("America/Lima");
        Calendar actual = Calendar.getInstance();
        actual.setTimeZone(timeZone);
        actual.setTime(actual.getTime());
        date.put("Mes", MESES.get(actual.get(Calendar.MONTH)));
        date.put("Dia", String.valueOf(actual.get(Calendar.DATE)));
        date.put("Anio", String.valueOf(actual.get(Calendar.YEAR)));
        date.put("Hora", String.valueOf(actual.get(Calendar.HOUR_OF_DAY)));
        date.put("Minuto", String.valueOf(actual.get(Calendar.MINUTE)));
        return date;
    }

    public static String convertDiaMesAnio(String fecha) {
        DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
        try {
            if (fecha == null || fecha.equals(""))
                return "";
            SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
            return dateFormat.format(formatter.parse(fecha));

        } catch (Exception e) {
            log.warn(e.getMessage());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return dateFormat.format(formatter.parse(fecha));
            } catch (ParseException ex) {
                log.warn(ex.getMessage());
                return "";
            }
        }
    }

    public static String getFechaVersionVehiculo(String fecha) {
        DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
        DateFormat dateFormat1 = new SimpleDateFormat(DD_MM_YYYY);
        if (fecha == null || fecha.equals(""))
            return "";
        SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
        String fechaActual = getFechaActualCompleto();
        String fechaVersion = null;
        String fechaVer = null;
        try {
            fechaVersion = dateFormat.format(formatter.parse(fecha));
            fechaVer = dateFormat1.format(formatter.parse(fecha));
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        int dias = getDiasDosFechas(fechaVersion, fechaActual);
        if (dias > 0) {
            if (dias > 6) return fechaVer;
            else return dias == 1 ? String.format("Hace %s día", dias) : String.format("Hace %s días", dias);
        } else {
            int horas = Integer.parseInt(getDate(fechaActual).get("Hora")) - Integer.parseInt(getDate(fechaVersion).get("Hora"));
            if (horas == 0 || horas == 1) return "Hace 1 hora";
            else return  String.format("Hace %s horas", horas);
        }
    }

    public static String fromYmdToDmy(String fecha) {
        DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(formatter.parse(fecha));
        } catch (ParseException ex) {
            log.warn(ex.getMessage());
            return "";
        }
    }

    public static int getDias(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(DD_MM_YYYY);
            LocalDateTime dateTime = LocalDateTime.parse(fecha, formatter);
            LocalDateTime actual = LocalDateTime.now();
            return Days.daysBetween(dateTime, actual).getDays();

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return 0;
    }

    public static int getDiasYMDHMS(String fecha) {
        return getDias(convertDiaMesAnio(fecha));
    }

    public static String getFechaActual() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
            Calendar actual = Calendar.getInstance();
            TimeZone timeZone = TimeZone.getTimeZone("America/Lima");
            actual.setTimeZone(timeZone);
            actual.setTime(actual.getTime());
            return dateFormat.format(actual.getTime());

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return "";
    }

    public static String getFechaActualCompleto() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
            Calendar actual = Calendar.getInstance();
            TimeZone timeZone = TimeZone.getTimeZone("America/Lima");
            actual.setTimeZone(timeZone);
            actual.setTime(actual.getTime());
            return dateFormat.format(actual.getTime());

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return "";
    }

    public static int getDiasDosFechas(String start, String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(DD_MM_YYYY);
            LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
            LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);
            return Days.daysBetween(dateStart, dateEnd).getDays();

        } catch (Exception e) {
            log.warn(e.getMessage());
            try {
                DateTimeFormatter formatter = DateTimeFormat.forPattern(DD_MM_YYYY_HH_MM_SS);
                LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
                LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);
                return Days.daysBetween(dateStart, dateEnd).getDays();

            } catch (Exception ex) {
                log.warn(ex.getMessage());
            }
        }
        return 0;
    }

}
