package AbstractFactory;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AgingStrategies.BetaAgingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.AttackingStrategies.EpsilonAttackingStrategy;
import Strategies.DiceStrategies.DiceStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import Strategies.UnitActionStrategies.GammaUnitActionStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.ThetaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
import Strategies.WinningStrategies.EpsilonWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.DeltaWorldStrategy;
import Strategies.WorldStrategy.WorldStrategy;

/**
 * Created by csdev on 10/11/17.
 */
public class SemiCivFactory implements CivFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new EpsilonWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new BetaAgingStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new EpsilonAttackingStrategy(new FixedDiceStrategy());
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaUnitActionStrategy();
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
