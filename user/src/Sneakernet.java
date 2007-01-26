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
	private Command okayCmd;

	private Form mLoginScreen;
	private StringItem stringItem;
	private TextField txtField;
		
	private Form mSubscribeToEmailScreen;
	private StringItem mSubscribeString;
	private TextField mSubscribeServer;
	private TextField mSubscribeUserName;
	private TextField mSubscribePassword;

	public Sneakernet()
	{
	}
	
    public void startApp()
    {	
		if( mLoginScreen == null )
		{
			mLoginScreen = new Form( "Information Without Borders" );

			stringItem = new StringItem("Sneakernet Login", null);
			mLoginScreen.append(stringItem);

			txtField = new TextField(
			"Your Email Address: ", "", 50, TextField.ANY);
			mLoginScreen.append(txtField);

			txtField = new TextField(
			"Your Password: ", "", 50, TextField.PASSWORD);
			mLoginScreen.append(txtField);

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mLoginScreen.addCommand( exitCmd );
			mLoginScreen.setCommandListener( this );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mLoginScreen.addCommand( okayCmd );
			mLoginScreen.setCommandListener( this );
		}

		if( mSubscribeToEmailScreen == null )
		{
			mSubscribeToEmailScreen = new Form( "Information Without Borders" );

			mSubscribeString = new StringItem("Subscribe To Email", null);
			mSubscribeToEmailScreen.append(mSubscribeString);

			mSubscribeServer = new TextField(
			"Incoming Mail Server: ", "", 50, TextField.ANY);
			mSubscribeToEmailScreen.append(mSubscribeServer);

			mSubscribeUserName = new TextField(
			"User Name: ", "", 50, TextField.ANY);
			mSubscribeToEmailScreen.append(mSubscribeUserName);

			mSubscribePassword = new TextField(
			"Password: ", "", 50, TextField.PASSWORD);
			mSubscribeToEmailScreen.append(mSubscribePassword);

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mSubscribeToEmailScreen.addCommand( exitCmd );
			mSubscribeToEmailScreen.setCommandListener( this );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mSubscribeToEmailScreen.addCommand( okayCmd );
			mSubscribeToEmailScreen.setCommandListener( this );
		}

		mDisplay = Display.getDisplay( this );
		mDisplay.setCurrent( mLoginScreen );
	}
	
    public void pauseApp()
    {
    }
	
	
    public void destroyApp( boolean unconditional )
    {
    }
	
	public void commandAction( Command mCommand, Displayable mScreen )
    {   
		int iType = mCommand.getCommandType();

		if( iType == Command.EXIT )
        {
            destroyApp( true );
            notifyDestroyed();
        }
		else if( iType == Command.OK )
		{
			if( mScreen == mLoginScreen )
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mSubscribeToEmailScreen );				
			}
			else
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mLoginScreen );				
			}			
		}
	}
}
