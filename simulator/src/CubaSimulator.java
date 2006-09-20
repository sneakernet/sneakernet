package org.iwb.simulator;
import org.iwb.simulator.*;

/**
 * Simulates a sneakernet to Cuba.
 * <p>
 * Copyright (c) 2006 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author$ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision$
 */
class CubaSimulator
    extends Simulator
{
    /**
     * Runs this simulator.  You can run this using with "java
     * org.iwb.CubaSimulator" on the command line from the "build"
     * directory after you compile with "ant compile" from the
     * "simulator" directory.
     */
    public static void main(String[] args)
    {
        System.out.println("Cuba Simulator");
	System.out.println();

	// Create a new simulation.
	CubaSimulator simulation = new CubaSimulator();

	// Run the simulation.
	simulation.simulate();
    }

    /**
     * Sets up this simulation, with information specific to this
     * simulation scenario.
     */
    public CubaSimulator()
    {
	/**
	 * This simulation uses hours as a unit of time.
	 */
	super("hour", "hours");

	////////////////////////////////////////////////////////////
	// Set up the Locations in this simulation.

	// The two parameters for a location are the location's name,
	// and the chance that two data carriers in that location have
	// of gossiping per time unit.

	// Miami International Airport, 30% chance of two data
	// carriers gossiping.  If two carriers in the airport then
	// they will probably take around three hours to find each
	// other.
	Location locationMIA =
	    new Location("Miami International Airport", 0.3);
	vLocations.add(locationMIA);

	// Eighth Street in Miami.
	Location locationCalleOcho =
	    new Location("Calle Ocho, Miami", 0.25);

	// Havana Airport.
	Location locationHAV =
	    new Location("Havana Airport", 0.25);
	vLocations.add(locationHAV);

	// An avenue that runs along the sea wall in Havana where
	// people can meet and interact.  Gossip shouldn't take more
	// than two hours.
	Location locationMalecon =
	    new Location("the Malecon", 0.5);

	////////////////////////////////////////////////////////////
	// Set up the routes that the Data Carriers will follow.

	Location[] aLocationsMaleconToHAV = {locationMalecon, locationHAV};
	Route routeMaleconToHAVcirculation =
	    new Route(aLocationsMaleconToHAV);

	Location[] aLocationsMIAtoHAV = {locationMIA, locationHAV};
	Route routeMIAtoHAVcirculation =
	    new Route(aLocationsMIAtoHAV);

	Location[] aLocationsCalleOchoToMIA = {locationCalleOcho, locationMIA};
	Route routeCalleOchoToMIAcirculation =
	    new Route(aLocationsCalleOchoToMIA);

	////////////////////////////////////////////////////////////
	// Set up the Data Carriers in this simulation.

	// Alex is an email user in Cuba.
	vDataCarriers.add(new DataCarrier("Alex's PDA",
					  locationMalecon,
					  // Does not travel.
					  null,
					  // 10% chance of generating
					  // an email per hour.
					  0.1));

	// Bob is a courier who visits the Havana airport frequently.
	vDataCarriers.add(new DataCarrier("Bob's mobile phone",
					  locationMalecon,
					  routeMaleconToHAVcirculation,
					  // Does not generate datagrams
					  // of his own.
					  0));

	// Chuck is a traveler who travels between the Miami and the
	// Havana airports sometimes.
	vDataCarriers.add(new DataCarrier("Chuck's digital camera card",
					  locationMIA,
					  routeMIAtoHAVcirculation,
					  0));

	// Eddie is a free person in Miami who has an Internet connection.
	vDataCarriers.add(new DataCarrier("Eddie's laptop computer",
					  locationCalleOcho,
					  routeCalleOchoToMIAcirculation,
					  0));
    }
}