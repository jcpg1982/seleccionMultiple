package pe.com.dms.movilasist.util;

public final class PatternUtils {

    public static String WHITESPACE_CHARS =
            ""
                    + "\\u0009" // CHARACTER TABULATION
                    + "\\u000A" // LINE FEED (LF)
                    + "\\u000B" // LINE TABULATION
                    + "\\u000C" // FORM FEED (FF)
                    + "\\u000D" // CARRIAGE RETURN (CR)
                    + "\\u0020" // SPACE
                    + "\\u0085" // NEXT LINE (NEL)
                    + "\\u00A0" // NO-BREAK SPACE
                    + "\\u1680" // OGHAM SPACE MARK
                    + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
                    + "\\u2000" // EN QUAD
                    + "\\u2001" // EM QUAD
                    + "\\u2002" // EN SPACE
                    + "\\u2003" // EM SPACE
                    + "\\u2004" // THREE-PER-EM SPACE
                    + "\\u2005" // FOUR-PER-EM SPACE
                    + "\\u2006" // SIX-PER-EM SPACE
                    + "\\u2007" // FIGURE SPACE
                    + "\\u2008" // PUNCTUATION SPACE
                    + "\\u2009" // THIN SPACE
                    + "\\u200A" // HAIR SPACE
                    + "\\u2028" // LINE SEPARATOR
                    + "\\u2029" // PARAGRAPH SEPARATOR
                    + "\\u202F" // NARROW NO-BREAK SPACE
                    + "\\u205F" // MEDIUM MATHEMATICAL SPACE
                    + "\\u3000" // IDEOGRAPHIC SPACE
            ;

    //Sólo letras, minuscula, mayuscula, ñ, tildes en may. y min. espacios en blanco.
    public static final String PATTERN_ONLY_LETTERS = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ" + WHITESPACE_CHARS + "]*$";
    public static final String PATTERN_ONLY_NUMBERS = "^[0-9]+$";
    public static final String PATTERN_NO_NUMBERS = "^[^0-9]*$";
    public static final String PATTERN_LETTERS_NUMBERS = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ" + WHITESPACE_CHARS + "0-9 ]*$";
    public static final String PATTERN_LETTERS_NUMBERS_WHITHOUT_SPACE = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\d._]*$";
    //Debe iniciar con un Alfanumérico, tener un "@" seguido de un alfanumérico, y luego del punto al menos dos letras y máximo 4.
    //public static final String PATTERN_EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //Minimo 1 Mayúscula, 1 Minúscula y 1 número
    public static final String PATTERN_PASSWORD = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\d._]{6,}$";


    //solo permite numeros y letras mayusculas/minusculas
    public static final String PATTERN_ALPHANUMERIC = "^[0-9a-zA-Z]+$";
}
