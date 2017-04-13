/**
 * 
 */
package in.jeani.n50.utils;

import in.jeani.n50.exceptions.Nifty50UtilException;
import in.jeani.n50.utils.bo.StockInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mohanavelp
 *
 */
public class Nifty50UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Nifty50Util nifty50util = new Nifty50Util();
		List<StockInfo> niftySecurities = new ArrayList<StockInfo>();
		try {
			niftySecurities = nifty50util.fetchNifty50Stocks();
			StringBuffer sbStocks = new StringBuffer();
			for (StockInfo stockInfo : niftySecurities) {
				sbStocks.append(stockInfo.getCompanyName()).append("\r\n");
			}
			System.out.println("Nifty50 Securities List: " + sbStocks.toString());
			
		} catch (Nifty50UtilException e) {
			e.printStackTrace();
		}
	}

}
