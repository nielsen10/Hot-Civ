package hotciv.Tools;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/**
 * Created by csdev on 11/7/17.
 */
public class ProductionTool extends NullTool {

  private CompositeTool compositeTool;
  private DrawingEditor editor;
  private Game game;

  public ProductionTool(DrawingEditor editor, Game game, CompositeTool compositeTool) {
    this.compositeTool = compositeTool;
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);

    boolean correctX = (e.getX() > GfxConstants.CITY_PRODUCTION_X && e.getX() < GfxConstants.CITY_PRODUCTION_X+30);
    boolean correctY = (e.getY() > GfxConstants.CITY_PRODUCTION_Y && e.getY() < GfxConstants.CITY_PRODUCTION_Y+30);

    if(correctX && correctY) {
      String producing = game.getCityAt(compositeTool.getCityInFocus()).getProduction();
      if(producing == null) producing = GameConstants.ARCHER;
      else if(producing.equals(GameConstants.ARCHER)) producing = GameConstants.LEGION;
      else if(producing.equals(GameConstants.LEGION)) producing = GameConstants.SETTLER;
      else if(producing.equals(GameConstants.SETTLER)) producing = null;


      game.changeProductionInCityAt(compositeTool.getCityInFocus(),producing);
      editor.showStatus("Changing production at " + compositeTool.getCityInFocus() + " to " + GameConstants.ARCHER);
    }
  }
}
