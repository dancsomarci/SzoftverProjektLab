package model.strategy;


import model.Virologist;
import model.codes.GeneticCode;

/**
 * A default felkenés stratégia, ami engedélyezi a felkenést a kenőnek.
 */
public class DefInject implements IInjectStr
{
	/**
	 * A felkenést végző függvény, ami elvégzi a felkenést a célpontra, valamint csökkenti v leléphető köreinek számát.
	 * @param v A virológus aki a kenést akarja végezni.
	 * @param target A célpont akit fel akar kenni.
	 * @param gc A genetikai kód, ami a felkenendő ágenst gyártja.
	 */
	@Override
	public void Inject(Virologist v, Virologist target, GeneticCode gc) {
		try {
			target.TargetedWith(gc.Create(v));
		} catch (Exception e) {
			//Nincs elég anyag a készítéshez, /vencel: sztem itt is kene csokkenteni az actions-t
		} finally{
			v.DecreaseActions();
		}
	}
}
