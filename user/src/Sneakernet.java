import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * This is the J2ME MIDlet from the
 * the <a
 * href="http://informationwithoutborders.endymion.com/index.php?title=User_Code">User
 * Code</a> project.
 * <p>
 * Copyright (c) 2007 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author: information.without.borders $ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision: 14 $
 */
public class Sneakernet extends MIDlet implements CommandListener
{
    private Display mDisplay;
    private Command exitCmd;
    private Form mForm;
	
    public void startApp()
    {
		if ( mForm == null )
		{
			mForm = new Form( "IWB Sneakernet" );
			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mForm.addCommand( exitCmd );
			mForm.setCommandListener( this );
			mDisplay = Display.getDisplay( this );
		}
		
		mDisplay.setCurrent( mForm );
	}
	
    public void pauseApp()
    {
    }
	
	
    public void destroyApp( boolean unconditional )
    {
    }
	
	public void commandAction( Command c, Displayable s )
    {   
        if ( c == exitCmd )
        {
            destroyApp( true );
            notifyDestroyed();
        }
	}
}
