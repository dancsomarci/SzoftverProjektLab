package control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A prototípus bemeneti parancsait megvalósító függvények jelölésére alkalmas annotáció.
 * A függvények elvárt fejléce: public void fvNev(String[] params)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProtoInput {
    /**
     * A parancsot ezalapján lehet azonosítani.
     * Vagyis a console-ban a name-ként megadott karaktersorozat begépelésével lehet futtatni a parancsot.
     * @return A megadott név.
     */
    String name();
}


