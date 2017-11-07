package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.stub.StubGame2;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/** Template code for exercise FRS 36.40.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Computer Science Department
 Aarhus University

 This source code is provided WITHOUT ANY WARRANTY either
 expressed or implied. You may study, use, modify, and
 distribute it for non-commercial purposes. For any
 commercial use, see http://www.baerbak.com/
 */
public class ShowSetFocus {

  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
            new MiniDrawApplication( "Click any tile to set focus",
                    new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click a tile to see Game's setFocus method being called.");

    // Replace the setting of the tool with your SetFocusTool implementation.
    editor.setTool( new FocusTool(editor, game) );
  }
}

class FocusTool extends NullTool {

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
