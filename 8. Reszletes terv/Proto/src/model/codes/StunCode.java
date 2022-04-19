package model.codes;


import model.Virologist;
import model.agents.Agent;
import model.agents.Stun;

/**
 * Olyan genetikai kód, ami egy bénító (Stun) típusú ágenst tud előállítani.
 */
public class StunCode extends GeneticCode
{
	/**
	 * Konstruktor, ami beállítja a kódhoz a megfelelő költségeket és
	 * a jövendőbeli ágens élettartamát a bénító ágens legyártásához.
	 */
	public StunCode(){
			aminoAcidPrice = 7;
			nucleotidePrice = 2;
			turnsLeft = 1;
	}

	/**
	 * Létrehoz egy bénító (Stun) ágenst.
	 * @param v a virológus, aki szeretne ágenst készíteni
	 * @return a létrehozott bénító ágens
	 * @throws Exception ha nem hozható létre az ágens, mert nincs hozzá elég anyag a paraméterül kapott virológusnak.
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
		return new Stun(turnsLeft*playerCount);
	}

}
