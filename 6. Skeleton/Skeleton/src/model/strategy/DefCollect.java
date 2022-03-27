package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Sikeres anyag gyüjtésért felelős stratégia
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
	 * Sikeres anyag gyüjtés
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
