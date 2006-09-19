package org.iwb.simulator;

/**
 * Encapsulates a simulated physical Location, where two Data Carriers
 * have a given percentage chance of having an opportunity to attempt
 * a gossip operation per unit of time that they are both at the
 * Location.  Data Carriers may also have their own rules that govern
 * what other Data Carriers they will gossip with.
 * <p>
 * Copyright (c) 2006 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author$ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision$
 */
public class Location
{
    /**
     * The name of this location, for the output log.
     */
    public String strName;

    /**
     * The probability that two Data Carriers at this location will
     * have an opportunity to attempt a gossip operation per time
     * unit that they both spend here.
     */
    public double fProbability;

    /**
     * Initializes a location.  Called from a specific Simulator subclass.
     */
    public Location(String strName, double fProbability)
    {
	this.strName = strName;
	this.fProbability = fProbability;
    }
}
