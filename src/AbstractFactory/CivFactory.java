package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/2/17.
 */
public interface CivFactory {
    WinningStrategy createWinningStrategy();

    AgingStrategy createAgingStrategy();

    AttackingStrategy createAttackingStrategy();

    UnitActionStrategy createUnitActionStrategy();

    WorldStrategy createWorldStrategy();

    UnitProductionStrategy createUnitProductionStrategy();
}
