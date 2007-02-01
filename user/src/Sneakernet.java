import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.util.test.Test;
//import org.bouncycastle.util.test.TestResult;
//import org.bouncycastle.jce.provider.test.ElGamalTest;

/**
 * This is the J2ME MIDlet from the
 * the <a
 * href="http://informationwithoutborders.endymion.com/index.php?title=User_Code">User
 * Code</a> project.
 * <p>
 * Copyright (c) 2007 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=User_Code">User Code</a>
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
	private StringItem mCryptoVersionString;
	private TextField mLoginEmail;
	private TextField mLoginPassword;
		
	private Form mSubscribeToEmailScreen;
	private StringItem mSubscribeString;
	private TextField mSubscribeServer;
	private TextField mSubscribeUserName;
	private TextField mSubscribePassword;

	private List mYourInformationScreen;

	private List mYourEmailScreen;

	private List mWebFeedScreen;

	public Sneakernet()
	{
	}
	
    public void startApp()
    {	
		if( mLoginScreen == null )
		{
			// Header
			
			mLoginScreen = new Form( "Sneakernet Login" );

			// Form
			
			Security.addProvider(new BouncyCastleProvider());
//			ElGamalTest mElGamalTest = new ElGamalTest();
//			TestResult result = mElGamalTest.perform();
//            if (result.getException() != null)
//            {
//                result.getException().printStackTrace();
//            }
//			mCryptoVersionString = new StringItem(result.toString(), null);
//			mCryptoVersionString = new StringItem("test", "test");
			mCryptoVersionString = new StringItem(
			"Crypto:",
			Security.getProvider("BC").getInfo());
			mLoginScreen.append(mCryptoVersionString);

			mLoginEmail = new TextField(
			"Your Email Address: ", "", 50, TextField.ANY);
			mLoginScreen.append(mLoginEmail);

			mLoginPassword = new TextField(
			"Your Password: ", "", 50, TextField.PASSWORD);
			mLoginScreen.append(mLoginPassword);

			// Comands

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mLoginScreen.addCommand( exitCmd );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mLoginScreen.addCommand( okayCmd );

			mLoginScreen.setCommandListener( this );
		}

		if( mSubscribeToEmailScreen == null )
		{
			// Header
			
			mSubscribeToEmailScreen = new Form( "Subscribe to Email" );
			
			// Form

			mSubscribeString = new StringItem("Enter email account information: ", null);
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
			
			// Commands

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mSubscribeToEmailScreen.addCommand( exitCmd );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mSubscribeToEmailScreen.addCommand( okayCmd );
			
			mSubscribeToEmailScreen.setCommandListener( this );
		}
		
		if( mYourInformationScreen == null )
		{
			// Header & form
			
			mYourInformationScreen = new List( "Your Information",
												Choice.IMPLICIT);

			mYourInformationScreen.append("Your Email", null);
			mYourInformationScreen.append("Web Feed Subscription", null);
			mYourInformationScreen.append("Another Web Feed Subscription", null);
			mYourInformationScreen.append("Yet Another Web Feed", null);

			// Commands

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mYourInformationScreen.addCommand( exitCmd );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mYourInformationScreen.addCommand( okayCmd );

			mYourInformationScreen.setCommandListener( this );
		}

		if( mYourEmailScreen == null )
		{
			// Header & form
			
			mYourEmailScreen = new List( "Your Email",
												Choice.IMPLICIT);

			mYourEmailScreen.append("* Are you okay? >Mom", null);
			mYourEmailScreen.append("* We can email now!! >Sister", null);
			mYourEmailScreen.append("Human Rights Vio- >Amnesty Int-", null);
			mYourEmailScreen.append("Order #3057 >Amazon.com", null);

			// Commands

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mYourEmailScreen.addCommand( exitCmd );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mYourEmailScreen.addCommand( okayCmd );

			mYourEmailScreen.setCommandListener( this );
		}

		if( mWebFeedScreen == null )
		{
			// Header & form
			
			mWebFeedScreen = new List( "Web Feed Subscription",
												Choice.IMPLICIT);

			mWebFeedScreen.append("* Net use in Cuba incr- >1/25/2009", null);
			mWebFeedScreen.append("* African email project >1/24/2009", null);
			mWebFeedScreen.append("* News from North Kor- >1/23/2009", null);
			mWebFeedScreen.append("Status report from tes- >1/22/2009", null);
			mWebFeedScreen.append("Handset donation inf- >12/28/2008", null);
			mWebFeedScreen.append("Software update for - >12/24/2008", null);

			// Commands

			exitCmd = new Command( "Exit", Command.EXIT, 0 );
			mWebFeedScreen.addCommand( exitCmd );

			okayCmd = new Command( "Okay", Command.OK, 0 );
			mWebFeedScreen.addCommand( okayCmd );

			mWebFeedScreen.setCommandListener( this );
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
			else if( mScreen == mSubscribeToEmailScreen )
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mYourInformationScreen );				
			}			
			else if( mScreen == mYourInformationScreen )
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mYourEmailScreen );				
			}			
			else if( mScreen == mYourEmailScreen )
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mWebFeedScreen );				
			}			
			else
			{
				mDisplay = Display.getDisplay( this );
				mDisplay.setCurrent( mLoginScreen );				
			}			
		}
	}
}
