package org.iwb.simulator;
import java.util.*;

/**
 * The route that a data carrier will follow over time.
 * <p>
 * Copyright (c) 2006 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author: information.without.borders $ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision: 10 $
 */
public class Route
{
    /**
     * The Locations that this route will travel through.
     */
    Vector vLocations;

    /**
     * Random number generator.
     */
    Random cRandomizer;

    /**
     * Create a new simulator with no locations.
     */
    public Route()
    {
	cRandomizer = new Random();
	vLocations = new Vector();
    }

    /**
     * Create a new simulator with the provided list of locations.
     */
    public Route(Location[] aLocations)
    {
	cRandomizer = new Random();
	vLocations = new Vector();

	for(int iLocationIndex=0; iLocationIndex < aLocations.length;
	    iLocationIndex++)
	{
	    vLocations.add(aLocations[iLocationIndex]);
	}
    }

    /**
     * Provides the current location during any given time slice for a
     * data carrier, according to the rules for this route.  The
     * default is to simply cycle through the locations in vLocations,
     * with a 50% chance of moving to a new location per time slice.
     * Most simulations will need to override this default behavior by
     * subclassing a customized Route.
     * @see CubaSimulator
     */
    public Location currentLocation(Location locationLast)
    {
	// 50% chance of advancing to a new location.
	if(cRandomizer.nextDouble() < 0.5){ return locationLast; }

	int iLastLocationIndex = vLocations.indexOf(locationLast);
	if(iLastLocationIndex != -1)
	{
	    // Increment to the next location, loop if necessary.
	    return (Location)vLocations.elementAt((iLastLocationIndex + 1) %
						  vLocations.size());
	}
	
	// If the location is off of the route then just stay there.
	return locationLast;
    }
}
