package pe.com.dms.movilasist.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import pe.com.dms.movilasist.R;

public class DateUtils {

    public static final String TAG = DateUtils.class.getSimpleName();

    public static final String PATTERN_DATETIME_API_WITH_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.SSSZZZZZ"; //"yyyy-MM-dd'T'HH:mm:ss.SSSXXX" <- No funciona en < Nougat
    public static final String PATTERN_DATETIME_API = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DATETIME_API_PROFILE = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "dd/MM/yyyy";
    public static final String PATTERN_DATE_2 = "dd-MM-yyyy";
    public static final String PATTERN_DATE_3 = "EEE dd MMM yyyy";
    public static final String PATTERN_DATE_INVERSE_2 = "yyyy-MM-dd";
    public static final String PATTERN_DATE_TIME_IMG = "yyyyMMdd_HHmmss";
    public static final String PATTERN_TIME = "HH:mm";
    public static final String PATTERN_TIME_A = "hh:mm a";
    public static final String PATTERN_TIME_b = "hh:mm:ss";
    public static final String PATTERN_DATETIME = "";
    public static final String PATTERN_FECHAHORA_WS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_LECTURA = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_LECTURA_1 = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_DATE_LECTURA = "yyyy/MM/dd";
    public static final String PATTERN_DATE_TAREO = "yyyyMMdd";
    public static final String PATTERN_TIME_TAREO = "HH:mm:ss";
    public static final String PATTERN_DATE_TIME_TAREO = "yyyyMMdd HH:mm:ss";
    public static final String PATTERN_DATE_RESULTADO = "yyyyMMddHHmmss";

    public static Date parseStringApiToDateLocal(String value)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.PATTERN_DATETIME_API, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            calendar.setTime(format.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static Date fromStringToDate(String value, SimpleDateFormat format)
            throws ParseException {
        return format.parse(value);
    }

    public static String fromDateToString(Date date, SimpleDateFormat format)
            throws ParseException {
        return format.format(date);
    }

    public static Calendar fromDateToCalendar(Date date)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String fromLongToString(long millis, SimpleDateFormat format)
            throws ParseException {
        Date date = new Date(millis);
        return format.format(date);
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static Date currentDate() {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.getTime();
    }

    public static Date yesterdayDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static boolean isDateYesterday(Date date) {
        long yesterday = yesterdayDate().getDay();
        return date.getDay() == yesterday;
    }

    public static String getDisplayDate(long millis, String pattern)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return DateUtils.fromLongToString(millis, format);
    }

    /**
     * Determina la hora local sin formato AM/PM
     *
     * @param millis
     * @return
     * @throws ParseException
     */
    public static String getDisplayTime(long millis)
            throws ParseException {
        return getDisplayTime(millis, null);
    }

    /**
     * Determina la hora local dependiendo de la configuracion de 12 0 24 horas.
     *
     * @param millis
     * @param context
     * @return
     * @throws ParseException
     */
    public static String getDisplayTime(long millis, Context context)
            throws ParseException {
        SimpleDateFormat format;
        if (context != null) {
            if (DateFormat.is24HourFormat(context)) {
                format = new SimpleDateFormat(DateUtils.PATTERN_TIME, Locale.getDefault());
            } else {
                format = new SimpleDateFormat(DateUtils.PATTERN_TIME_A, Locale.getDefault());
            }
        } else {
            format = new SimpleDateFormat(DateUtils.PATTERN_TIME, Locale.getDefault());
        }

        return DateUtils.fromLongToString(millis, format);
    }

    public static String getDisplayTimePayment(long millis, Context context)
            throws ParseException {
        SimpleDateFormat format;
        if (context != null) {
            if (DateFormat.is24HourFormat(context)) {
                format = new SimpleDateFormat(DateUtils.PATTERN_TIME_b, Locale.getDefault());
            } else {
                format = new SimpleDateFormat(DateUtils.PATTERN_TIME_b, Locale.getDefault());
            }
        } else {
            format = new SimpleDateFormat(DateUtils.PATTERN_TIME_b, Locale.getDefault());
        }

        return DateUtils.fromLongToString(millis, format);
    }

    public static String getDisplayDay(long millis, Context context)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(millis));

        String text = String.valueOf(android.text.format.DateUtils.getRelativeTimeSpanString(context,
                new Date(millis).getTime(), true)); //Optional / Helpers
        Resources r = context.getResources();

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                text = r.getString(R.string.time_sunday);
                break;
            case 2:
                text = r.getString(R.string.time_monday);
                break;
            case 3:
                text = r.getString(R.string.time_tuesday);
                break;
            case 4:
                text = r.getString(R.string.time_wednesday);
                break;
            case 5:
                text = r.getString(R.string.time_thursday);
                break;
            case 6:
                text = r.getString(R.string.time_friday);
                break;
            case 7:
                text = r.getString(R.string.time_saturday);
                break;
            default:
                text = DateUtils.getDisplayDate(millis, DateUtils.PATTERN_DATE);
                break;
        }

        return text;
    }

    public static String getDisplayTimeAgo(long millis, Context context)
            throws ParseException {
        return getDisplayTimeAgo(millis, false, context);
    }

    public static String getDisplayTimeAgo(long millis, boolean withSevenDays, Context context)
            throws ParseException {
        boolean isYesterday = isDateYesterday(new Date(millis));
        long now = currentDate().getTime();
        final long diff = now - millis;
        //Dia
        if (!isYesterday && diff < 24 * HOUR_MILLIS) {
            return DateUtils.getDisplayTime(millis, context);
        }
        //Ayer
        else if (isYesterday && diff < 48 * HOUR_MILLIS) {
            return context.getResources().getString(R.string.time_yesterday);
        }
        //Dias de la semana
        //TODO: Falta determinar como "Yesterday" para mostrar los dias de la semana.
        else if (diff < 7 * 48 * HOUR_MILLIS && withSevenDays) {
            return getDisplayDay(millis, context);
        }
        //Otra fecha
        else {
            return DateUtils.getDisplayDate(millis, DateUtils.PATTERN_DATE);
        }
    }


    private static boolean today(Date date) {
        return sameDay(date, new Date(System.currentTimeMillis()));
    }

    public static boolean today(long date) {
        return sameDay(date, System.currentTimeMillis());
    }

    public static boolean yesterday(long date) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        cal2.setTime(new Date(date));
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2
                .get(Calendar.DAY_OF_YEAR);
    }

    public static boolean sameDay(long a, long b) {
        return sameDay(new Date(a), new Date(b));
    }

    private static boolean sameDay(Date a, Date b) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(a);
        cal2.setTime(b);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2
                .get(Calendar.DAY_OF_YEAR);
    }

    public static String timeZone() {
        String timeZone;
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        timeZone = tz.getID();
        Log.e(TAG, "timeZone: " + timeZone);
        return timeZone;
    }

    public static String setDatetimeFormat(Context context, String dateString, String typeFormat) {
        return setDateFormat(dateString, typeFormat) + " " + setTimeFormat(context, dateString);
    }

    public static String setDateFormat(String dateString, String typeFormat) {
        String dates = null;
        try {
            Date date = DateUtils.parseStringApiToDateLocal(dateString);
            dates = DateUtils.getDisplayDate(date.getTime(), typeFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public static String setDateFormat(String dateString, String formatInt, String formatOut) {
        String fechaFormato = null;
        try {
            SimpleDateFormat formatInt1 = new SimpleDateFormat(formatInt);
            SimpleDateFormat formatOut1 = new SimpleDateFormat(formatOut);
            Date date = formatInt1.parse(dateString);
            fechaFormato = formatOut1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fechaFormato;
    }

    public static String setTimeFormat(Context context, String dateString) {
        String time = "";
        Date date;
        try {
            date = DateUtils.parseStringApiToDateLocal(dateString);
            time = DateUtils.getDisplayTime(date.getTime(), context);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String dateToStringFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    public static Date stringToDate(String dateTime) throws ParseException {
        return new SimpleDateFormat(PATTERN_DATE_TAREO).parse(dateTime);
    }

    public static boolean esMenorQue(String formatFecha1, String fecha1, String formatFecha2, String fecha2) {
        if (stringToLongFormat(fecha1, formatFecha1) < stringToLongFormat(fecha2, formatFecha2)) {
            return true;
        }
        return false;
    }

    public static boolean esMayorQue(String formatFecha1, String fecha1, String formatFecha2, String fecha2) {
        if (stringToLongFormat(fecha1, formatFecha1) > stringToLongFormat(fecha2, formatFecha2)) {
            return true;
        }
        return false;
    }

    public static long stringToLongFormat(String dateTime, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(dateTime).getTime();
        } catch (Exception ex) {
            return 0L;
        }
    }

    public static boolean ComprobarFechas(String initial, String end) {
        Log.e(TAG, "ComprobarFechas initial: " + initial + ", end: " + end);
        try {
            Date mInitial = fromStringToDate(initial,
                    new SimpleDateFormat(PATTERN_DATE_RESULTADO));
            Date mEnd = fromStringToDate(end,
                    new SimpleDateFormat(PATTERN_DATE_RESULTADO));

            Log.e(TAG, "ComprobarFechas mInitial: " + mInitial.toString() + ", mEnd: " + mEnd.toString());
            if (mInitial.equals(mEnd))
                return true;

            if (mEnd.before(mInitial))
                return true;

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}