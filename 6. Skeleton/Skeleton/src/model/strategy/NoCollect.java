package model.strategy;

import model.Virologist;
import model.map.Field;
import control.Tester;

/**
 * Sikertelen anyag gyüjtésért felelős stratégia
 */
public class NoCollect implements ICollectStr
{
	/**
	 * Sikertelen anyag gyüjtés stratégia létrehozása
	 */
	public NoCollect(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Sikertelen anyag gyüjtés
	 * @param v Gyüjtő virológus
	 * @param f A mező, amelyen gyüjtődik az anyag
	 */
	@Override
	public void Collect(Virologist v, Field f) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
