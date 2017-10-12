package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.UnitActionStrategies.ThetaUnitActionStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.ThetaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.DeltaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/11/17.
 */
public class ThetaCivFactory implements CivFactory {
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
        return new ThetaUnitActionStrategy();
    }

    @Override
    public WorldStrategy createWorldStrategy() {
        return new DeltaWorldStrategy();
    }

    @Override
    public UnitProductionStrategy createUnitProductionStrategy() {
        return new ThetaUnitProductionStrategy();
    }
}
