package org.iwb.simulator;

/**
 * Encapsulates a simulated Data Carrier.  This is a base class that
 * specific simulations may subclass.
 * <p>
 * Copyright (c) 2006 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author$ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision$
 */
public class DataCarrier
{
    /**
     * The name of this data carrier, for the log.
     */
    String strName;

    /**
     * A description of this data carrier for the log.
     */
    String strDescription;
    
    /**
     * The starting location of this data carrier.
     */
    Location locationLast;

    /**
     * The chance that this data carrier will generate an email message
     * per time unit.
     */
    double dChanceOfComposingNewDatagramPerTimeUnit;

    /**
     * The route that the data carrier follows during the simulation.
     */
    Route routeToFollow;

    /**
     * Initializes a Data Carrier.  Called from a specific Simulator subclass.
     */
    public DataCarrier(String strName,
		       String strDescription,
		       Location locationStart,
		       Route routeToFollow,
		       double dChanceOfComposingNewDatagramPerTimeUnit)
    {
	this.strName = strName;
	this.strDescription = strDescription;
	this.locationLast = locationStart;
	this.routeToFollow = routeToFollow;
	this.dChanceOfComposingNewDatagramPerTimeUnit =
	    dChanceOfComposingNewDatagramPerTimeUnit;
    }
}
