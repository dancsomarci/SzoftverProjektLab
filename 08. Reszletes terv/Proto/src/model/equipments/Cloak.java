package model.equipments;


import model.Virologist;
import model.strategy.NoInjected;

/**
 * Védőfelszerelés, amely stratégiát biztosít viselőjén bizonyos eséllyel, érinthetetlenné teszi, ágensek felől
 */
public class Cloak extends Equipment
{
	/**
	 * Alkalmazza az ágensek felől érinthetetlen stratégiát bizonyos eséllyel
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v)
	{
		double r = Math.random() ;
		if (r < 0.823)
			v.SetInjectedStr(new NoInjected());
	}
}
