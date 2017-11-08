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
public class EndOfTurnTool extends NullTool {

  private DrawingEditor editor;
  private Game game;

  public EndOfTurnTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);

    boolean correctX = (e.getX() > GfxConstants.TURN_SHIELD_X && e.getX() < GfxConstants.TURN_SHIELD_X+30);
    boolean correctY = (e.getY() > GfxConstants.TURN_SHIELD_Y && e.getY() < GfxConstants.TURN_SHIELD_Y+30);

    if(correctX && correctY) {
      game.endOfTurn();
      editor.showStatus("Ending turn for player "+ game.getPlayerInTurn());
    }
  }
}
