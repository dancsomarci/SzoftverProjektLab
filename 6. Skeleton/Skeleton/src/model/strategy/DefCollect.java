package model.strategy;


import model.Virologist;
import model.map.Field;
import control.Tester;

/**
 * Alapértelmezett aminosav vagy nukleotid gyűjtési stratégia, ami által a virológus aminosavat vagy nukleotidot
 * gyűjt össze a mezőről, ha van ott olyan.
 */
public class DefCollect implements ICollectStr
{
	/**
	 * Anyag gyüjtés stratégia létrehozása
	 */
	public DefCollect(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Az aminosav gyűjtés stratégia alkalmazásakor hívott metódus.
	 * Adott mennyiségű aminosavat ad a virológusnak, ha van a mezőn.
	 * @param v gyüjtő virológus
	 * @param f a mező, amelyen gyüjtődik az anyag
	 */
	@Override
	public void Collect(Virologist v, Field f) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		f.CollectMaterial(v);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
