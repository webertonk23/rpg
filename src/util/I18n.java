package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resources.messages", Locale.ENGLISH);

    public static String t(String key) {
        return bundle.getString(key);
    }
}
