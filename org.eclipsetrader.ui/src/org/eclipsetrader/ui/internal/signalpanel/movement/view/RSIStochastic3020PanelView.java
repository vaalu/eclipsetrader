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
public class RSIStochastic3020PanelView extends AbstractSignalView {

	public static final String VIEW_TITLE="30:20 RSI:30 Stoch:20";
	public RSIStochastic3020PanelView(ISignalMoment moment, Composite parent) {
		super(moment, parent, VIEW_TITLE);
	}
	@Override
	public void process(final double rsi, final double stochastic, final ISecurity security) {
		if (rsi <= 30.0 && stochastic <= 20.0) {
			StockInfo info = getStockInfo(security, rsi, stochastic);
			getFilteredStocks().add(info);
		}
	}
}
