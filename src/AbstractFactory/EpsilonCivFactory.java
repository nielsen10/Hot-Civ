package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.AttackingStrategies.EpsilonAttackingStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import Strategies.DiceStrategies.RandomDiceStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.WinningStrategies.EpsilonWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/2/17.
 */
public class EpsilonCivFactory implements CivFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new EpsilonWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AlphaAgingStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new EpsilonAttackingStrategy(new FixedDiceStrategy());
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
