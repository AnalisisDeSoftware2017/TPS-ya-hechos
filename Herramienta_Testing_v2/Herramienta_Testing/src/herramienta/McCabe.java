package herramienta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class McCabe {

    public static int calcularCC(String str) {
        String[] matches = new String[] {"if", "while", "for", "case", "\\|\\|", "\\&\\&"};
        int count = 0;
        for (String match : matches) {
            Pattern pat = Pattern.compile(match);
            Matcher mat = pat.matcher(str);
            while (mat.find())
                count++;
        }
        return count + 1;
    }
    
}
