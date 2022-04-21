package model.strategy;



import model.Virologist;
import model.map.Field;

/**
 * Olyan védőfelszerelés felvételének mechanikájáért felelős stratégia, ami nem engedélyezi a felvételt.
 */
public class NoEquip implements IEquipStr
{
	/**
	 * A metódus üres, hiszen nem engedélyezi a felvételt, de természetesen levonódik egy akciópont.
	 * @param v A felvételt végző virológus.
	 * @param f Erről a mezőről próbálkozik védőfelszerelés felvételével v.
	 */
	@Override
	public void Equip(Virologist v, Field f)
	{
		v.DecreaseActions();
	}
}
