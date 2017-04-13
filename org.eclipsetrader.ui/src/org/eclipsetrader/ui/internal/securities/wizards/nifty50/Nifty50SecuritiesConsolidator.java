/**
 * 
 */
package org.eclipsetrader.ui.internal.securities.wizards.nifty50;

import in.jeani.n50.utils.bo.StockInfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipsetrader.core.instruments.ISecurity;

/**
 * @author mohanavelp
 *
 */
public class Nifty50SecuritiesConsolidator {

	private List<StockInfo> currentNifty50Stocks = null;
	private ISecurity[] nifty50StocksAlreadyPresent = null;
	private List<StockInfo> addedNifty50Stocks = new ArrayList<StockInfo>();
	private List<ISecurity> removedNifty50Stocks = new ArrayList<ISecurity>();
	
	public Nifty50SecuritiesConsolidator(List<StockInfo> currentNifty50Stocks, ISecurity[] nifty50StocksAlreadyPresent) {
		this.currentNifty50Stocks = currentNifty50Stocks;
		this.nifty50StocksAlreadyPresent = nifty50StocksAlreadyPresent;
	}
	
	public void consolidate() {
		List<String> currentStockNames = new ArrayList<String>();
		List<String> prevStockNames = new ArrayList<String>();

		for (StockInfo stockInfo : currentNifty50Stocks) {
			currentStockNames.add(stockInfo.getCompanyName());
		}
		for (ISecurity stockInfo : nifty50StocksAlreadyPresent) {
			prevStockNames.add(stockInfo.getName());
		}
		
		for (String currStock : currentStockNames) {
			if (!prevStockNames.contains(currStock)) {
				int currIndex = currentStockNames.indexOf(currStock);
				StockInfo currentlyAddedN50Stock = currentNifty50Stocks.get(currIndex);
				addedNifty50Stocks.add(currentlyAddedN50Stock);
			}
		}
		
		for (ISecurity security : nifty50StocksAlreadyPresent) {
			if (!currentStockNames.contains(security.getName())) {
				removedNifty50Stocks.add(security);
			}
		}
		
		
	}
	
	/**
	 * @return the currentNifty50Stocks
	 */
	public List<StockInfo> getCurrentNifty50Stocks() {
		return currentNifty50Stocks;
	}
	/**
	 * @param currentNifty50Stocks the currentNifty50Stocks to set
	 */
	public void setCurrentNifty50Stocks(List<StockInfo> currentNifty50Stocks) {
		this.currentNifty50Stocks = currentNifty50Stocks;
	}
	/**
	 * @return the nifty50StocksAlreadyPresent
	 */
	public ISecurity[] getNifty50StocksAlreadyPresent() {
		return nifty50StocksAlreadyPresent;
	}
	/**
	 * @param nifty50StocksAlreadyPresent the nifty50StocksAlreadyPresent to set
	 */
	public void setNifty50StocksAlreadyPresent(
			ISecurity[] nifty50StocksAlreadyPresent) {
		this.nifty50StocksAlreadyPresent = nifty50StocksAlreadyPresent;
	}

	/**
	 * @return the addedNifty50Stocks
	 */
	public List<StockInfo> getAddedNifty50Stocks() {
		return addedNifty50Stocks;
	}

	/**
	 * @param addedNifty50Stocks the addedNifty50Stocks to set
	 */
	public void setAddedNifty50Stocks(List<StockInfo> addedNifty50Stocks) {
		this.addedNifty50Stocks = addedNifty50Stocks;
	}

	/**
	 * @return the removedNifty50Stocks
	 */
	public List<ISecurity> getRemovedNifty50Stocks() {
		return removedNifty50Stocks;
	}

	/**
	 * @param removedNifty50Stocks the removedNifty50Stocks to set
	 */
	public void setRemovedNifty50Stocks(List<ISecurity> removedNifty50Stocks) {
		this.removedNifty50Stocks = removedNifty50Stocks;
	}
	
}
