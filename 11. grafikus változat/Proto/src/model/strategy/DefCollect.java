package model.strategy;

import model.Virologist;
import model.map.Field;

/**
 * Alapértelmezett aminosav vagy nukleotid gyűjtési stratégia, ami által a virológus aminosavat vagy nukleotidot
 * gyűjt össze a mezőről, ha van ott olyan.
 */
public class DefCollect implements ICollectStr {

    /**
	 * Anyag gyűjtés stratégia alkalmazásakor hívott metódus.
	 * Adott mennyiségű anyagot ad a virológusnak, ha van a mezőn, és csökkenti eggyel az akcióinak számát.
	 * @param v gyüjtő virológus
	 * @param f a mező, amelyen gyüjtődik az anyag
	 */
	@Override
	public void Collect(Virologist v, Field f) {
		f.CollectMaterial(v);
		v.DecreaseActions();
	}

}
