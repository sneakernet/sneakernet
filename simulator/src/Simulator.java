package org.iwb.simulator;
import java.util.*;

/**
 * Provides functionality that allows a derived class to act as a
 * sneakernet simulator, according to the simple model specified by
 * the <a
 * href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator
 * Code</a> project.
 * <p>
 * Copyright (c) 2006 <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a><br>
 * Licensed under the <a href="http://www.gnu.org/copyleft/gpl.html">GNU General Public License</a>.
 *
 * @see <a href="http://informationwithoutborders.endymion.com/index.php?title=Simulator_Code">Simulator Code</a>
 *
 * @author $Author$ at <a href="http://informationwithoutborders.endymion.com">Information Without Borders</a>
 * @version $Revision$
 */
public class Simulator
{
    /**
     * The name of this simulation scenario.
     */
    String strName;

    /**
     * A background description for this simulation scenario.
     */
    String strDescription;

    /**
     * Definition of a unit in time, for the logs.  Singular.
     */
    String strTimeUnit;

    /**
     * Definintion of a unit in time in plural, for the logs.  These two
     * values are the only thing that you need to change in order to change
     * the unit of time that a simulation uses.
     */
    String strTimeUnits;

    /**
     * How many iterations of the time unit to run for this simulation.
     * The defaults value is 10 iterations.
     */
    int iIterations = 10;

    /**
     * The Locations covered in this simulator, where Data Carriers reside
     * and potentially gossip with one another.
     */
    Vector vLocations;

    /**
     * The Data Carriers that are moving from Location to Location in this
     * simulation.
     */
    Vector vDataCarriers;

    /**
     * Create a new simulator.
     */
    public Simulator(String strName, String strDescription,
		     String strTimeUnit, String strTimeUnits)
    {
	this.strName = strName;
	this.strDescription = strDescription;
	this.strTimeUnit = strTimeUnit;
	this.strTimeUnits = strTimeUnits;

	vLocations = new Vector();
	vDataCarriers = new Vector();
    }

    /**
     * Run the simulation.
     */
    public void simulate()
    {
        System.out.println(strName);
	System.out.println();

        System.out.println(strDescription);
	System.out.println();

	// Log the unit of time for this simulation.
	System.out.println("Time unit: " + strTimeUnits);
	System.out.println();

	// Print a list of locations to the log.
	System.out.println("Locations:");
	for(int index=0;index < vLocations.size();index++)
	{
	    Location location = (Location) vLocations.elementAt(index);
	    System.out.println("    " + location.strName);
	}
	System.out.println();

	// Print a list of locations to the log.
	System.out.println("Data Carriers:");
	for(int index=0;index < vDataCarriers.size();index++)
	{
	    DataCarrier dataCarrier =
		(DataCarrier) vDataCarriers.elementAt(index);
	    System.out.println("    " + dataCarrier.strName +
			       ", " + dataCarrier.strDescription + ", ");
	    System.out.println("        is starting at " +
			       dataCarrier.locationLast.strName + ".");
	}

	// Loop through the specified number of time slice iterations.
	int iIterationIndex = 0;
	for(;iIterationIndex<iIterations; iIterationIndex++)
	{
	    System.out.println();
	    System.out.println(strTimeUnit + " #" +
			       (iIterationIndex + 1) + ":");

	    // For each data carrier in the simulation, find out
	    // whether it has moved along a route to a new location or
	    // not.
	    int iDataCarrierIndex = 0;
	    for(;iDataCarrierIndex < vDataCarriers.size();
		iDataCarrierIndex++)
	    {
		DataCarrier cDataCarrier = (DataCarrier)
		    vDataCarriers.elementAt(iDataCarrierIndex);

		Location locationLast =
		    cDataCarrier.locationLast;
		if(cDataCarrier.routeToFollow != null)
		{
		    cDataCarrier.locationLast =
			cDataCarrier.routeToFollow.currentLocation(locationLast);
		}

		System.out.println(cDataCarrier.strName +
				   " is at location: " +
				   cDataCarrier.locationLast.strName);
	    }
	}
    }
}