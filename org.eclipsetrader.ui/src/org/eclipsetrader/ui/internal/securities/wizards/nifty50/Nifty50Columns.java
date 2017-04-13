/**
 * 
 */
package org.eclipsetrader.ui.internal.securities.wizards.nifty50;

import in.jeani.n50.utils.bo.StockInfo;

import org.eclipse.jface.viewers.ColumnLabelProvider;

/**
 * @author mohanavelp
 *
 */
public enum Nifty50Columns {
	COMPANY_NAME("Company Name", 0), 
	INDUSTRY("Industry", 1),
	SYMBOL("Symbol", 2),
	SERIES("Series", 3),
	ISIN("ISIN Code", 4), 
	RSI("RSI", 5), 
	STOCHASTIC("Stochastic", 6);
	private int columnKey = -1;
	private String columnHeader = "";
	
	private Nifty50Columns(String columnHeader, int columnKey) {
		this.columnKey = columnKey;
		this.columnHeader = columnHeader;
	}
	public String getHeader() {
		return columnHeader;
	}
	public ColumnLabelProvider getProvider() {
		ColumnLabelProvider labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String columnValue = "";
				if(element != null) {
					StockInfo stockInfo = (StockInfo)element;
					switch (columnKey) {
					case 0:
						columnValue = stockInfo.getCompanyName();
						break;
					case 1:
						columnValue = stockInfo.getIndustry();
						break;
					case 2:
						columnValue = stockInfo.getSymbol();
						break;
					case 3:
						columnValue = stockInfo.getSeries();
						break;
					case 4:
						columnValue = stockInfo.getIsinCode();
						break;
					case 5:
						columnValue = ""+stockInfo.getRsi();
						break;
					case 6:
						columnValue = ""+stockInfo.getStochastic();
						break;
					default:
						columnValue = "";
					}
				}
				return columnValue;
			}
		};
		return labelProvider;
	}
}
