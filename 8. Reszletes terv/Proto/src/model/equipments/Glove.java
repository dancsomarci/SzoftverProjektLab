package model.equipments;


import model.Virologist;
import model.agents.Agent;
import model.strategy.IInjectedStr;

/**
 * Védőfelszerelés, amely stratégiát biztosít viselőjén, érinthetetlenné teszi, ágensek felől
 */
public class Glove extends Equipment implements IInjectedStr
{
	private int useCount = 3;

	/**
	 * Alkalmazza az ágensek felől érinthetetlen stratégiát
	 * @param v viselő virológus
	 */
	public void ApplyStrategy(Virologist v) {
		v.SetInjectedStr(this);
	}

	@Override
	public void Injected(Virologist v, Agent a) {
		if (useCount > 0){
			useCount--;
		} else{
			v.RemoveEquipment(this);
			v.Reset();
			v.TargetedWith(a);
		}
	}

	@Override
	public void Injected(Virologist by, Virologist injected, Agent a) {
		if (useCount > 0){
			useCount--;
			by.TargetedWith(injected, a);
		} else{
			injected.RemoveEquipment(this);
			injected.Reset();
			injected.TargetedWith(by, a);
		}
	}
}
