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
public class RSIStochastic8080PanelView extends AbstractSignalView {

	public static final String VIEW_TITLE="80:80 RSI:80 Stoch:80";
	public RSIStochastic8080PanelView(ISignalMoment moment, Composite parent) {
		super(moment, parent, VIEW_TITLE);
	}
	@Override
	public void process(final double rsi, final double stochastic, final ISecurity security) {
		if (rsi >= 80.0 && stochastic >= 80.0) {
			StockInfo info = getStockInfo(security, rsi, stochastic);
			getFilteredStocks().add(info);
		}
	}
}
