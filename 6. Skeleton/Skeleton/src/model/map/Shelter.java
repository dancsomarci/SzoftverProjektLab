package model.map;


import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

/**
 *
 */
public class Shelter extends Field
{

	/**
	 * TODO
	 * @param e
	 */
	public Shelter(Equipment e) {
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		if (e != null)
			equipments.add(e);
	}
}
