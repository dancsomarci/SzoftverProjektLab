package model.strategy;


import model.Virologist;
import model.agents.Agent;

/**
 * A virológus azon stratégiáját reprezentálja, mikor felkennek rá valamilyen ágenst.
 */
public interface IInjectedStr
{
	/**
	 * A stratégia alakalmazásakor hívott metódus.
	 * @param v A virológus, akire felkenték az ágenst.
	 * @param a A felkent ágens.
	 */
	void Injected(Virologist v, Agent a);
}
