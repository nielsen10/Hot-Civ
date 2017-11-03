package Strategies.WorldStrategy;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by csdev on 10/28/17.
 */
public class FractalAdaptor implements WorldStrategy {
    private ThirdPartyFractalGenerator generator;

    public FractalAdaptor() {
        generator = new ThirdPartyFractalGenerator();
    }

    @Override
    public void buildWorld(GameImpl game, HashMap<Position, UnitImpl> unitMap, HashMap<Position, TileImpl> tileMap, HashMap<Position, CityImpl> cityMap) {
        buildFractal(tileMap);
    }

    public void buildFractal(HashMap<Position, TileImpl> tileMap) {
        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 15; j++) {
                Position pos = new Position(i, j);

                char tileChar = generator.getLandscapeAt(i, j);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }

                tileMap.put(pos, new TileImpl(pos, type));
            }
        }
    }
}