package model.equipments;


import model.Virologist;
import control.Tester;

/**
 * Védőfelszerelés, amely hatással van a viselőjére vagy/és stratégiát biztosít számára
 */
public abstract class Equipment
{

	/**
	 * Megszakítja a hatását a viselőjén
	 * @param v viselő virológus
	 */
	public void Disable(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Alkalmazza hatását viselőjén
	 * @param v viselő virológus
	 */
	public void Apply(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Alkalmazza stratégiáját viselőjén
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
