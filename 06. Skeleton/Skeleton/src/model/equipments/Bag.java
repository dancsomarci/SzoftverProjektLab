package model.equipments;


import model.Virologist;
import test.Tester;

/**
 * Olyan felszerelés, amely növeli a maximális nukleotid és aminosav tárhelyet
 */
public class Bag extends Equipment
{
	/**
	 * Növelendő mennyiség
	 */
	private int delta;

	/**
	 * Zsák felszerelés létrehozása
	 * Beállítja a növelő mennyiséget
	 */
	public Bag(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		delta = 5;
	}

	/**
	 * Növelia a maximális nukleotid és aminosav tárhelyet
	 * @param v viselő virológus
	 */
	public void Apply(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.IncreaseLimit(delta);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Megszünteti a növelő hatását
	 * @param v viselő virológus
	 */
	public void Disable(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.DecreaseLimit(delta);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
