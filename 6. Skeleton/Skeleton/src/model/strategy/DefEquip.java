package model.strategy;



import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Default engedélyezett a tárgyfelvétel (védőfelszerlés), így ezt a stratégiát valósítja meg.
 */
public class DefEquip implements IEquipStr
{
	/**
	 * Default ctor, csak a kiíratás miatt.
	 */
	public DefEquip(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * A parqaméterül kapott virológussal megpróbáltat felvenni egy védőfelszerelést, valamint csökkenti körei számát.
	 * @param v A felvételt végző virológus.
	 * @param f Erről a mezőről próbálkozik védőfelszerelés felvételével v.
	 */
	public void Equip(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		f.PickUpEquipment(v);
		v.DecreaseActions();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
