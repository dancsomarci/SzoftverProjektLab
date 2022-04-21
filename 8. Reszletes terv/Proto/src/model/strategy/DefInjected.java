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
	 * Nem számít. hogy ismert-e a felkenő virológus a hatás ugyanaz, tehát delegálja a hívást a kevesebb paraméterű Injected fv-nek.
	 * @param by A felkenő virológus.
	 * @param injected A felkent virológus.
	 * @param a A felkent ágens.
	 */
	@Override
	public void Injected(Virologist by, Virologist injected, Agent a) {
		Injected(injected, a);
	}
}
