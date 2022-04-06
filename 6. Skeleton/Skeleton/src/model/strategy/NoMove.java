package model.strategy;



import model.Virologist;
import model.map.Field;
import control.Tester;

/**
 * Sikertelen mozgásért felelős stratégia
 */
public class NoMove implements IMoveStr
{
	/**
	 * Sikertelen mozgás stratégia létrehozása
	 */
	public NoMove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Sikertelen mozgás
	 * @param v Mozgó virológus
	 * @param from Virológus aktuális mezője
	 * @param to Új mező, amelyre lépni szeretne
	 */
	@Override
	public void Move(Virologist v, Field from, Field to)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
