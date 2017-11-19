package hotciv.visual;

import AbstractFactory.SemiCivFactory;
import hotciv.GameImpl;
import hotciv.GameObserverImpl;
import hotciv.Tools.CompositeTool;
import hotciv.framework.Game;
import hotciv.stub.StubGame2;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Factory;
import minidraw.standard.MiniDrawApplication;

/** Template code for exercise FRS 36.44.

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
public class ShowSemi {
  
  public static void main(String[] args) {
    Game game = new GameImpl(new SemiCivFactory());
    Game gameObserver = new GameObserverImpl(new SemiCivFactory(),game);

    DrawingEditor editor =
      new MiniDrawApplication( "Play your favorite SemiCivGame",
                               new HotCivFactory4(gameObserver) );

    editor.open();
    editor.showStatus("Fantastic Semi Civ Game");

    editor.setTool( new CompositeTool(editor, gameObserver) );
  }
}
