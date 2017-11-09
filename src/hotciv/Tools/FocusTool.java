package hotciv.Tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/**
 * Created by csdev on 11/7/17.
 */
public class FocusTool extends NullTool {

  private CompositeTool compositeTool;
  private DrawingEditor editor;
  private Position pos;
  private Game game;

  public FocusTool(DrawingEditor editor, Game game, CompositeTool compositeTool) {
    this.compositeTool = compositeTool;
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);

    pos = GfxConstants.getPositionFromXY(x,y);

    boolean productionX = (e.getX() > GfxConstants.CITY_PRODUCTION_X && e.getX() < GfxConstants.CITY_PRODUCTION_X+30);
    boolean productionY = (e.getY() > GfxConstants.CITY_PRODUCTION_Y && e.getY() < GfxConstants.CITY_PRODUCTION_Y+30);

    if(!productionX && !productionY) {
      if(game.getCityAt(pos)!=null) {
        compositeTool.setCityInFocus(pos);
      } else {
        compositeTool.setCityInFocus(null);
      }
      game.setTileFocus(pos);
    }

    editor.showStatus("Focusing at " + pos);
  }
}
