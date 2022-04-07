package model.agents;

import model.Virologist;
import model.map.Field;
import model.strategy.BearMove;
import model.strategy.IMoveStr;

public class Bear extends Agent {
    @Override
    public void ApplyStrategy(Virologist v){
        v.SetMoveStr(new BearMove());
    }

    @Override
    public void Update(Virologist v){ //Sosem évül el
    }
}
