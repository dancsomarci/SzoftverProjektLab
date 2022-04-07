package model.map;


import model.Virologist;

import java.util.Random;

/**
 * Olyan mező, amelyen anyag gyüjthető
 */
public class Warehouse extends Field
{

	private int delta = 5;

	/**
	 * Anyag gyüjtése
	 * @param v gyüjtő virológus
	 */
	public void CollectMaterial(Virologist v) {
		Random random = new Random();
		int r = random.nextInt(2) ;

		if (r == 0) {
			v.AddAminoAcid(delta);
		}
		else {
			v.AddNucleotide(delta);
		}

		v.DecreaseActions();
	}

	@Override
	public void DestroyMaterial(){
		delta = 0;
	}
}
