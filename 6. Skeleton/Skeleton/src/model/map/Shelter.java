package model.map;


import model.equipments.Equipment;
import control.Tester;

/**
 * Olyan mező, amelyen egy védőfelszerelés gyüjthető
 */
public class Shelter extends Field
{

	/**
	 * Védőfelszerelés hozzáadása
	 * @param e hozzáadandó felszerelés
	 */
	public Shelter(Equipment e) {
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		if (e != null)
			equipments.add(e);
	}
}
