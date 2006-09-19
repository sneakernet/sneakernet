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
    String strName;

    /**
     * Initializes a Data Carrier.  Called from a specific Simulator subclass.
     */
    public DataCarrier(String strName)
    {
	this.strName = strName;
    }
}
