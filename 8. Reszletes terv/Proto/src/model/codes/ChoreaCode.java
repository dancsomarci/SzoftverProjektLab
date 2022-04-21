package model.codes;


import model.Virologist;

import model.agents.Agent;
import model.agents.Chorea;

import static model.Game.playerCount;

/**
 * Olyan genetikai kód, ami egy vitustánc (Chorea) típusú ágenst tud előállítani.
 */
public class ChoreaCode extends GeneticCode
{
	/**
	 * Konstruktor, ami beállítja a kódhoz a megfelelő költségeket és
	 * a jövendőbeli ágens élettartamát a vitustánc ágens legyártásához.
	 */
	public ChoreaCode(){
		aminoAcidPrice = 5;
		nucleotidePrice = 6;
		turnsLeft = 1;
	}

	/**
	 * Létrehoz egy vitustánc (Chorea) ágenst.
	 * @param v a virológus, aki szeretne ágenst készíteni
	 * @return a létrehozot vitustánc ágens
	 * @throws Exception ha nem hozható létre az Agent, mert nincs hozzá elég anyaga a paraméterül kapott virológusnak.
	 */
	public Agent Create(Virologist v) throws Exception
	{
		try{
			v.RemoveAminoAcid(aminoAcidPrice);
			v.RemoveNucleotide(nucleotidePrice);
		}
		catch(Exception e){
			throw e;
		}
		System.out.println("Code: " + turnsLeft*playerCount);
		return new Chorea(turnsLeft*playerCount);
	}

}
