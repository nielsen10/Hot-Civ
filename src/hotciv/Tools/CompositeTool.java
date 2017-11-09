package hotciv.Tools;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/**
 * Created by csdev on 11/7/17.
 */
public class CompositeTool extends NullTool {
    private final ProductionTool productionTool;
    private DrawingEditor editor;
    private Game game;
    private MoveTool moveTool;
    private FocusTool focusTool;
    private EndOfTurnTool endOfTurnTool;
    private ActionTool actionTool;
    private Position cityInFocus;

    public CompositeTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;

        actionTool = new ActionTool(editor,game);
        endOfTurnTool = new EndOfTurnTool(editor,game);
        focusTool = new FocusTool(editor,game, this);
        moveTool = new MoveTool(editor,game);
        productionTool = new ProductionTool(editor,game, this);
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);

        productionTool.mouseDown(e,x,y);
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

    public Position getCityInFocus() {
        return cityInFocus;
    }

    public void setCityInFocus(Position cityInFocus) {
        this.cityInFocus = cityInFocus;
    }
}
