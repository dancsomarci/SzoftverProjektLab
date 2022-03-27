package model.map;


import model.Virologist;
import test.Tester;

import java.util.Random;

/**
 * Olyan mező, amelyen anyag gyüjthető
 */
public class Warehouse extends Field
{
	/**
	 * Anyag gyüjtése
	 * @param v gyüjtő virológus
	 */
	public void CollectMaterial(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Random random = new Random();
		int r = random.nextInt(2) ;

		if (r == 0) {
			v.AddAminoAcid(5);
		}
		else {
			v.AddNucleotide(5);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
