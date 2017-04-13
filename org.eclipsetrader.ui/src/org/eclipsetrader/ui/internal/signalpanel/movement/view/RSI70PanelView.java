/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.movement.view;

import in.jeani.n50.utils.bo.StockInfo;

import org.eclipse.swt.widgets.Composite;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.ui.internal.signalpanel.view.AbstractSignalView;
import org.eclipsetrader.ui.internal.signalpanel.view.ISignalMoment;

/**
 * @author mohanavelp
 *
 */
public class RSI70PanelView extends AbstractSignalView {

	public static final String VIEW_TITLE="70 RSI:70";
	public RSI70PanelView(ISignalMoment moment, Composite parent) {
		super(moment, parent, VIEW_TITLE);
	}
	@Override
	public void process(final double rsi, final double stochastic, final ISecurity security) {
		if (rsi >= 70.0) {
			//System.out.println("Security name: " + security.getName() + " : RSI : " + lastRsi);
			StockInfo info = getStockInfo(security, rsi, stochastic);
			getFilteredStocks().add(info);
		}
	}
}
