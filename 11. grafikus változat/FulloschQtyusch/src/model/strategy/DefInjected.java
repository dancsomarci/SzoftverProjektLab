package model.strategy;



import model.Virologist;
import model.agents.Agent;

/**
 * A felkenés default stratégiája, mikor ténylegesen megtörténik a felkenés.
 */
public class DefInjected implements IInjectedStr
{
	/**
	 * A stratégia alkalmazásakor hívott metódus.
	 * @param v A virológus, akire felkenték az ágenst.
	 * @param a A felkent ágens.
	 */
	@Override
	public void Injected(Virologist v, Agent a)
	{
		a.Apply(v);
		v.AddAgent(a);
		a.ApplyStrategy(v);
	}

	/**
	 * Egy virológus beolt egy másikat a megadott ágenssel.
	 * @param by A beoltó virológus
	 * @param injected A beoltott virológus
	 * @param a Az ágens
	 */
	@Override
	public void Injected(Virologist by, Virologist injected, Agent a) {
		Injected(injected, a);
	}
}
