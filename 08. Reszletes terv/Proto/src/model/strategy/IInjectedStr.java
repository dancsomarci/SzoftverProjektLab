package model.strategy;


import model.Virologist;
import model.agents.Agent;

/**
 * A virológus azon stratégiáját reprezentálja, mikor felkennek rá valamilyen ágenst.
 */
public interface IInjectedStr
{
	/**
	 * A stratégia alakalmazásakor hívott metódus, olyan esetben, ha a felkenő nem egy virológus.
	 * @param v A virológus, akire felkenték az ágenst.
	 * @param a A felkent ágens.
	 */
	void Injected(Virologist v, Agent a);

	/**
	 * A stratégia alakalmazásakor hívott metódus, olyan esetben, ha a felkenő ismert virológus.
	 * @param by A felkenő virológus.
	 * @param injected A felkent virológus.
	 * @param a A felkent ágens.
	 */
	void Injected(Virologist by, Virologist injected, Agent a);
}
