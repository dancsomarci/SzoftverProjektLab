package model.equipments;


import model.Virologist;

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
		delta = 5;
	}

	/**
	 * Növelia a maximális nukleotid és aminosav tárhelyet
	 * @param v viselő virológus
	 */
	public void Apply(Virologist v) {
		v.IncreaseLimit(delta);
	}

	/**
	 * Megszünteti a növelő hatását
	 * @param v viselő virológus
	 */
	public void Disable(Virologist v) {
		v.DecreaseLimit(delta);
	}

}
