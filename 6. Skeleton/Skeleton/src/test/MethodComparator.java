package test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Metodusok komparalasara szolgalo osztaly
 * Csak olyan metódusokhoz használható, amin rajta van a SkeletonTestCase annotáció
 * Az annotáció id-je alapján rendez, ez a szemantikus verziózásnál meg
 */
public class MethodComparator implements Comparator<Method> {
    @Override
    public int compare(Method m1, Method m2) {
        List<String> id1 = new ArrayList<>(Arrays.asList(m1.getAnnotation(SkeletonTestCase.class).id().split("\\."))); //listába fejti a verziók egyes elemeit
        List<String> id2 = new ArrayList<>(Arrays.asList(m2.getAnnotation(SkeletonTestCase.class).id().split("\\."))); //szükséges ArrayList-re "castolni", mert az Aslist fix hosszú listát ad vissza

        while (id1.size() < id2.size()) id1.add("0"); //0-ák elhagyhatóak a végéről
        while (id1.size() > id2.size()) id2.add("0");

        for (int i = 0; i < id1.size(); i++){
            if (Integer.parseInt(id1.get(i)) == Integer.parseInt(id2.get(i))){
                continue;
            }
            else if (Integer.parseInt(id1.get(i)) < Integer.parseInt(id2.get(i))){
                return -1;
            }
            else{
                return 1;
            }
        }
        return 0;
    }
}
