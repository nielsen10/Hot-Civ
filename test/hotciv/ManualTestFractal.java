package hotciv;

import Strategies.WorldStrategy.FractalAdaptor;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;

import java.util.HashMap;

/**
 * Created by csdev on 11/3/17.
 */
public class ManualTestFractal {

    public static HashMap<Position, TileImpl> tileMap = new HashMap<>();

    public static void main(String[] args) {
        new FractalAdaptor().buildFractal(tileMap);

        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 15; j++) {
                char tile = 'X'; //x for error, or x marks the spot
                String type = tileMap.get(new Position(i,j)).getTypeString();

                if (type == GameConstants.OCEANS) {
                    tile = '.';
                }
                if (type == GameConstants.PLAINS) {
                    tile = 'o';
                }
                if (type == GameConstants.MOUNTAINS) {
                    tile = 'M';
                }
                if (type == GameConstants.FOREST) {
                    tile = 'f';
                }
                if (type == GameConstants.HILLS) {
                    tile = 'h';
                }

                System.out.print(tile);
            }
            System.out.println("");
        }
    }
}

