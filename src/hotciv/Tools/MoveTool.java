package hotciv.Tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/**
 * Created by csdev on 11/7/17.
 */
public class MoveTool extends SelectionTool {
  private Game game;
  private Unit movingUnit;
  private Position oldPos;

  public MoveTool(DrawingEditor editor, Game game) {
    super(editor);
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    oldPos = calculatePosition(e);
    boolean isUnit = game.getUnitAt(oldPos) != null;
    if(isUnit) {
      super.mouseDown(e, x, y);

      editor.showStatus("Dragging unit at " + oldPos);
      movingUnit = game.getUnitAt(oldPos);
    }


  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    Position newPos = calculatePosition(e);

    if(movingUnit!=null){

      if(game.moveUnit(oldPos,newPos)) {
        editor.showStatus("State change: moved unit from " + oldPos + " to " + newPos);
      } else {
        editor.showStatus("Invalid move from " + oldPos + " to " + newPos);
      }
    }
    movingUnit=null;
    super.mouseUp(e, x, y);
  }

  public Position calculatePosition(MouseEvent e) {
    int posX = (e.getX()- GfxConstants.MAP_OFFSET_X) / GfxConstants.TILESIZE;
    int posY = (e.getY()-GfxConstants.MAP_OFFSET_Y) / GfxConstants.TILESIZE;

    Position position = new Position(posY,posX);
    return position;
  }
}
