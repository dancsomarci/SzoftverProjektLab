package model.agents;


import model.Virologist;
import test.Tester;

/**
 * Egy ágens, amely valamilyen hatással van a virológusra és ez időben elévül.
 * Élettartamát periodikusan frissíteni kell, ilyenkor mindig eggyel csökken.
 * Ha egyszer lejárt már többé nem lesz hatása.
 */
public abstract class Agent
{
	/**
	 * Az ágens hátra lévő élettartama
	 */
	protected int timeToLive;

	/**
	 * Öregíti egy egységgel az Agent-et.
	 * @param v a tulajdonos virológus
	 */
	public void Update(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		timeToLive--;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Default implementációban üres függvény, de azt reprezentálja, hogy az ágenst elhelyezik a paraméterül kapott virológuson
	 * és esetlegesen ennek vannak direkt/azonnali hatásai rá.
	 * @param v a célzott virológus
	 */
	public void Apply(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az ágens hatásait hajtja végre a virológuson,
	 * átállítja bizonyos cselekvéseinek a működését,
	 * alapértelmezetten üres, amikor nincs az ágensnek hosszútávú hatása, csak azonnali.
	 * @param v a célzott virológus
	 */
	public void ApplyStrategy(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
