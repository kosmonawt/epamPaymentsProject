package locale;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Languages supported by Application
 */

public enum SupportedLocale {
    EN(new Locale("en", "EN")),

    UA(new Locale("uk", "UA"));

    private final static Locale DEFAULT_LOCALE = EN.getLocale();

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }


    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public static Locale getDefault(){
        return DEFAULT_LOCALE;
    }

    public static Locale getDefaultOrLocale(String lang){
        return Arrays.stream(SupportedLocale.values())
                .map(SupportedLocale::getLocale)
                .filter(x -> x.getLanguage().equals(lang))
                .findFirst()
                .orElse(getDefault());
    }

    public static List<String> getSupportedLanguages(){
        return Arrays.stream(SupportedLocale.values())
                .map(x -> x.getLocale().getLanguage())
                .collect(Collectors.toList());
    }

}
