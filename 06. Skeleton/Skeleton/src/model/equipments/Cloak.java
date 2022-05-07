package model.equipments;


import model.Virologist;
import model.strategy.NoInjected;
import test.Tester;

/**
 * Védőfelszerelés, amely stratégiát biztosít viselőjén bizonyos eséllyel, érinthetetlenné teszi, ágensek felől
 */
public class Cloak extends Equipment
{
	/**
	 * Köpeny védőfelszerelés létrehozása
	 */
	public Cloak(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Alkalmazza az ágensek felől érinthetetlen stratégiát bizonyos eséllyel
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		double r = Math.random() ;
		if (r < 0.823)
			v.SetInjectedStr(new NoInjected());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
