package UI.Constantes;

import java.time.format.DateTimeFormatter;

public class Formats {
    public final static DateTimeFormatter FORMAT_HEURE_COURANTE = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public static String formatterDoubleSansDecimal(double d)
    {
        return String.format("%1$.0f", d);
    }
}
