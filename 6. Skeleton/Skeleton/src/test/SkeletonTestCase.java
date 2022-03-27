package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Skeleton tesztesetek megjelölésére alkalmas annotáció.
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
     * @return A teszteset azonosítójával tér vissza.
     */
    String id();
}
