package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.stub.StubGame2;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/** Template code for exercise FRS 36.39.

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
public class ShowMove {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
      new MiniDrawApplication( "Move any unit using the mouse",
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Move units to see Game's moveUnit method being called.");

    // Replace the setting of the tool with your UnitMoveTool implementation.
    editor.setTool( new SelectionToolImpl(editor, game) );
  }
}

class SelectionToolImpl extends SelectionTool {
  private Game game;
  private Unit movingUnit;
  private Position oldPos;

  public SelectionToolImpl(DrawingEditor editor, Game game) {
    super(editor);
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);

    oldPos = calculatePosition(e);

    editor.showStatus("Dragging unit at " + oldPos);
    movingUnit = game.getUnitAt(oldPos);

  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    Position newPos = calculatePosition(e);

    if(movingUnit!=null){

      if(game.moveUnit(oldPos,newPos)) {
        game.moveUnit(oldPos,newPos);
        editor.showStatus("State change: moved unit from " + oldPos + " to " + newPos);
      } else {
        game.moveUnit(oldPos,newPos);
        editor.showStatus("invalid move");
      }
    }
    super.mouseUp(e, x, y);
  }

  public Position calculatePosition(MouseEvent e) {
    int posX = (e.getX()-GfxConstants.MAP_OFFSET_X) / GfxConstants.TILESIZE;
    int posY = (e.getY()-GfxConstants.MAP_OFFSET_Y) / GfxConstants.TILESIZE;

    Position position = new Position(posY,posX);
    return position;
  }
}