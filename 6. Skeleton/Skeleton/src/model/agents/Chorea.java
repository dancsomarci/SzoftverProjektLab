package model.agents;


import model.Virologist;

/**
 * Olyan ágens, ami random lépésekre készteti a virológust a felkenés pillanatában.
 * Számszerűen háromra. Persze ezt nyilván csak akkor, ha képes a lépésre a virológus.
 */
public class Chorea extends Agent
{


	/**
	 * Konstruktor, amely beállítja az ágens hatásának hátralévő idejét.
	 * @param tL a beállítandó hatásidő
	 */
	public Chorea(int tL){
		super(tL);
	}

	/**
	 * Háromszor lépteti a virológust random irányban, ha az képes a lépésekre.
	 * @param v a célzott virológus
	 */
	public void Apply(Virologist v)
	{
		v.Move();
		v.Move();
		v.Move();
	}
}
