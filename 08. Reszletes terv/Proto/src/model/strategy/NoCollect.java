package model.strategy;

import model.Virologist;
import model.map.Field;

/**
 * Anyag sikertelen begyüjtéséért felelős stratégia.
 */
public class NoCollect implements ICollectStr {

    /**
	 * Anyag sikertelen begyüjtését implementáló függvény.
	 * @param v Gyüjtő virológus
	 * @param f A mező, amelyen gyüjtődik az anyag
	 */
	@Override
	public void Collect(Virologist v, Field f) {
		// /vencel: sztem itt is csokkentsuk az actions-t, megprobalta csinalni, csak nem hagytak, ez van
		v.DecreaseActions();
	}

}
