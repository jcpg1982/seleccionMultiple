package pe.com.dms.movilasist.ui.filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternInputFilter implements InputFilter {

    private Pattern mPattern;

    public PatternInputFilter(String pattern) {
        this(Pattern.compile(pattern));
    }

    public PatternInputFilter(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException(PatternInputFilter.class.getSimpleName()
                    + " requires a regex.");
        }
        mPattern = pattern;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source == null) return null;
        Matcher matcher = mPattern.matcher(source);
        if (!matcher.matches()) {
            return "";
        }
        return source;
    }
}