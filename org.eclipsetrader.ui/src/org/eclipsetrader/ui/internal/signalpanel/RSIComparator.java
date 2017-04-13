/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel;

import in.jeani.n50.utils.bo.StockInfo;

import java.util.Comparator;

/**
 * @author mohanavelp
 *
 */
public class RSIComparator implements Comparator<StockInfo> {

	@Override
	public int compare(StockInfo left, StockInfo right) {
		Double comp = right.getRsi() - left.getRsi();
		int retVal = (comp > 0.0) ? 1 : (comp < 0.0) ? -1 : 0;
		return retVal;
	}

}
