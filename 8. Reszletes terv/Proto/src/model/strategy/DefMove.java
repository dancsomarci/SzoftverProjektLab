package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * Alapértelmezett lépési stratégia, ami átlépteti a virológust egy másik mezőre.
 */
public class DefMove implements IMoveStr
{
	/**
	 * Sikeres mozgás stratégia létrehozása
	 */
	public DefMove(){
	}

	/**
	 * A stratégia alkalmazásakor hívott metódus.
	 * Átlépteti a virológust a megadott mezőre, és csökkenti eggyel az akcióinak számát.
	 * @param v Mozgó virológus
	 * @param from Virológus aktuális mezője
	 * @param to Új mező, amelyre lépni szeretne
	 */
	@Override
	public void Move(Virologist v, Field from, Field to)
	{
		from.RemoveVirologist(v);
		to.AddVirologist(v);
		v.DecreaseActions();
	}
}
