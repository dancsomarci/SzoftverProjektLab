package control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Skeleton tesztesetek megjelölésére alkalmas annotáció.
 * A tesztesetek metódusainak statikusnak kell lennie, és publikusnak a működéshez.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkeletonTestCase {
    /**
     * A teszteset neve.
     * @return A teszteset nevével tér vissza.
     */
    String name();

    /**
     * A teszteset azonosítója.
     * A szemantikus verziózásban megszokott módon pontokkal elválasztott számokból kell álljon. pl.: 1.2.3.4
     * @return A teszteset azonosítójával tér vissza.
     */
    String id();
}
