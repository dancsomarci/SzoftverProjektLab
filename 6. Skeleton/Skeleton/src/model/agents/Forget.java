package model.agents;


import model.Virologist;

/**
 * Olyan ágens, ami felkenéskor elfelejteti a virológussal az összes megtanult genetikai kódját.
 */
public class Forget extends Agent
{
	/**
	 * Konstruktor, amely beállítja az ágens hatásának hátralévő idejét.
	 * @param tL a beállítandó hatásidő
	 */
	public Forget(int tL){
		timeToLive = tL;
	}

	/**
	 * Alkalmazza az ágenst a paraméterként kapott virológuson,
	 * aki elfelejti az összes megtanult GeneticCode-ját.
	 * @param v a célzott virológus
	 */
	public void Apply(Virologist v)
	{
		v.RemoveGeneticCodes();
	}
}
