package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.BetaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.WinningStrategies.BetaWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/2/17.
 */
public class BetaCivFactory implements CivFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new BetaWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new BetaAgingStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new AlphaAttackingStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return null;
    }

    @Override
    public WorldStrategy createWorldStrategy() {
        return new AlphaWorldStrategy();
    }
}
