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
public class Nifty50ColumnLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		if (element != null) {
			StockInfo info = (StockInfo)element;
			
		}
		return super.getText(element);
	}
}
