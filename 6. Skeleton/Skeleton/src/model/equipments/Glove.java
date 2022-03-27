package model.equipments;


import model.Virologist;
import model.strategy.NoInjected;
import test.Tester;

/**
 * Védőfelszerelés, amely stratégiát biztosít viselőjén, érinthetetlenné teszi, ágensek felől
 */
public class Glove extends Equipment
{
	/**
	 * TODO
	 */
	public Glove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Alkalmazza az ágensek felől érinthetetlen stratégiát
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.SetInjectedStr(new NoInjected());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
