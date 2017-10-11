package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.DeltaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/2/17.
 */
public class DeltaCivFactory implements CivFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new AlphaWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AlphaAgingStrategy();
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
        return new DeltaWorldStrategy();
    }

    @Override
    public UnitProductionStrategy createUnitProductionStrategy() {
        return new AlphaUnitProductionStrategy();
    }
}
