package model.codes;


import model.Virologist;
import model.agents.Agent;
import model.agents.Block;
import test.Tester;


/**
 * Blokkoló hatású ágenshez tartozó genetikai kód
 */
public class BlockCode extends GeneticCode
{
	/**
	 * Konstruktor, mely beállítja a kreálható ágens költségeit és
	 * hatásánakl időtartamát
	 */
	public BlockCode() {
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingMethod());
		aminoAcidPrice = 4;
		nucleotidePrice = 3;
		turnsLeft = 2;
	}

	/**
	 * Létrehoz egy Block Agent-t (ágens), és visszatér vele.
	 * Ha nem hozható létre az Agent, mert nincs hozzá elég anyag a paraméterül kapott virológusnak,
	 * akkor kivételt dob.
	 * @param v a virológus, aki szeretne ágenst készíteni
	 * @return az elkészített ágens
	 * @throws Exception ha a virológusnak nem volt elég anyaga az ágenskészítéshez
	 */
	public Agent Create(Virologist v) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.RemoveNucleotide(nucleotidePrice);
		try{
			v.RemoveAminoAcid(aminoAcidPrice);
		}
		catch(Exception e){
			v.AddNucleotide(nucleotidePrice);
			throw e;
		}
		Block b = new Block(turnsLeft);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return b;
	}

}
