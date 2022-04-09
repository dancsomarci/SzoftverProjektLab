package model.strategy;



import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Olyan védőfelszerelés felvételének mechanikájáért felelős stratégia, ami nem engedélyezi a felvételt.
 */
public class NoEquip implements IEquipStr
{
	/**
	 * Default ctor, csak a kiíratás miatt.
	 */
	public NoEquip(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * A metódus üres, hiszen nem engedélyezi a felvételt.
	 * @param v A felvételt végző virológus.
	 * @param f Erről a mezőről próbálkozik védőfelszerelés felvételével v.
	 */
	@Override
	public void Equip(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
