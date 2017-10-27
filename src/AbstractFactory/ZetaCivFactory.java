package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.AttackingStrategies.EpsilonAttackingStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
import Strategies.WinningStrategies.BetaWinningStrategy;
import Strategies.WinningStrategies.EpsilonWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WinningStrategies.ZetaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/2/17.
 */
public class ZetaCivFactory implements CivFactory{
    @Override
    public WinningStrategy createWinningStrategy() {
        return new ZetaWinningStrategy(new EpsilonWinningStrategy(), new BetaWinningStrategy());
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
        return new AlphaWorldStrategy();
    }

    @Override
    public UnitProductionStrategy createUnitProductionStrategy() {
        return new AlphaUnitProductionStrategy();
    }
}
