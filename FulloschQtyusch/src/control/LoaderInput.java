package control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Loader bemeneti parancsait megvalósító függvények jelölésére alkalmas annotáció.
 * A függvények nincs elvárt fejléce, azt leszámítva, hogy nem várhat paramétereket.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoaderInput {
    /**
     * A parancsot ezalapján lehet azonosítani. (a bemeneti nyelvben a parancs nevei)
     * @return A megadott név.
     */
    String name();
}


