package model.agents;


import model.Virologist;
import model.strategy.NoInjected;
import test.Tester;

/**
 * Olyan ágens, ami hatástalanítja az összes aktuálisan aktív ágenst a felkent virológuson
 */
public class Block extends Agent
{
	/**
	 * Konstruktor, amely beállítja az ágens hatásának hátralévő idejét.
	 * @param tL a beállítandó hatásidő
	 */
	public Block(int tL){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingMethod());
		timeToLive = tL;
	}

	/**
	 * Törli az összes jelenleg hatással bíró ágenst és hatásait a virológusból.
	 * @param v a célzott virológus
	 */
	public void Apply(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		v.RemoveAgents();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológuson, hogy blokkolja majd az eljövendő felkenéseket
	 * @param v a célzott virológus
	 */
	public void ApplyStrategy(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		NoInjected ni = new NoInjected();
		v.SetInjectedStr(ni);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
