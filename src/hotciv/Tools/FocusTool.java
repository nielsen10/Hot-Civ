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

  private DrawingEditor editor;
  private Position pos;
  private Game game;

  public FocusTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);

    pos = calculatePosition(e);

    game.setTileFocus(pos);
    editor.showStatus("Focusing at " + pos);

  }

  public Position calculatePosition(MouseEvent e) {
    int posX = (e.getX()- GfxConstants.MAP_OFFSET_X) / GfxConstants.TILESIZE;
    int posY = (e.getY()-GfxConstants.MAP_OFFSET_Y) / GfxConstants.TILESIZE;

    Position position = new Position(posY,posX);
    return position;
  }
}
