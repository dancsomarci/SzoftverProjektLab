package model.equipments;


import model.Virologist;
import model.agents.Agent;
import model.strategy.IInjectedStr;
import model.strategy.NoInjected;
import control.Tester;

/**
 * Védőfelszerelés, amely stratégiát biztosít viselőjén, érinthetetlenné teszi, ágensek felől
 */
public class Glove extends Equipment implements IInjectedStr
{
	/**
	 * Létrehozza a kesztyű védőfelszerelést
	 */
	public Glove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Alkalmazza az ágensek felől érinthetetlen stratégiát
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v) {
		v.SetInjectedStr(this);
	}

	@Override
	public void Injected(Virologist v, Agent a) {
		//szekvencia alapján implementálni
	}

	@Override
	public void Injected(Virologist by, Virologist injected, Agent a) {
		//szekvencia alapján implementálni
	}
}
