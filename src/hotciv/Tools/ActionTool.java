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
public class ActionTool extends NullTool {


    private Game game;
    private DrawingEditor editor;
    private Position pos;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);

        pos = GfxConstants.getPositionFromXY(x,y);
        if(e.isShiftDown() && game.getUnitAt(pos) != null ) {
            pos = GfxConstants.getPositionFromXY(e.getX(),e.getY());
            game.performUnitActionAt(pos);
            editor.showStatus("Performing unit action at: " + pos);
        }

    }
}
