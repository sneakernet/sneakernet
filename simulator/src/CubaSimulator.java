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

	// Create a new simulation.
	CubaSimulator simulation = new CubaSimulator();

	// Run the simulation.
	simulation.simulate();
    }

    /**
     * Constructs this simulation.
     */
    public CubaSimulator()
    {
	// This simulation uses hours as a unit of time.
	super("hour", "hours");

	// Set up the Locations in this simulation.
	vLocations.add(new Location("Miami International Airport", 0.1));
	// The two parameters for a location are the location's name,
	// and the chance that two data carriers in that location have
	// of gossiping per time unit.
	vLocations.add(new Location("Havana Airport", 0.1));

	// Set up the Data Carriers in this simulation.
	vDataCarriers.add(new DataCarrier("Alex's PDA"));
	vDataCarriers.add(new DataCarrier("Bob's mobile phone"));
	vDataCarriers.add(new DataCarrier("Dave's digital camera card"));
    }
}