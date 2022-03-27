package model.strategy;



import model.Virologist;
import model.agents.Agent;
import test.Tester;

/**
 * A felkenés default stratégiája, mikor ténylegesen megtörténik a felkenés.
 */
public class DefInjected implements IInjectedStr
{
	/**
	 * Default ctor, csak a kiíratás miatt.
	 */
	public DefInjected(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * A stratégia alkalmazásakor hívott metódus.
	 * @param v A virológus, akire felkenték az ágenst.
	 * @param a A felkent ágens.
	 */
	public void Injected(Virologist v, Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		a.Apply(v);
		v.AddAgent(a);
		a.ApplyStrategy(v);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
