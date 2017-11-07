package hotciv.Tools;

import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/**
 * Created by csdev on 11/7/17.
 */
public class CompositeTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private MoveTool moveTool;
    private FocusTool focusTool;
    private EndOfTurnTool endOfTurnTool;
    private ActionTool actionTool;

    public CompositeTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;

        actionTool = new ActionTool(editor,game);
        endOfTurnTool = new EndOfTurnTool(editor,game);
        focusTool = new FocusTool(editor,game);
        moveTool = new MoveTool(editor,game);

    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);

        actionTool.mouseDown(e,x,y);
        endOfTurnTool.mouseDown(e,x,y);
        focusTool.mouseDown(e,x,y);
        moveTool.mouseDown(e,x,y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        super.mouseUp(e,x,y);

        moveTool.mouseUp(e,x,y);
    }
}
