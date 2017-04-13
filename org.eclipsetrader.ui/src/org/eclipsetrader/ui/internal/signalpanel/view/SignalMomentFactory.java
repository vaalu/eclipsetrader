/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.ui.internal.signalpanel.movement.view.RSI70PanelView;
import org.eclipsetrader.ui.internal.signalpanel.movement.view.RSIStochastic3020PanelView;
import org.eclipsetrader.ui.internal.signalpanel.movement.view.RSIStochastic7080PanelView;
import org.eclipsetrader.ui.internal.signalpanel.movement.view.RSIStochastic8080PanelView;

/**
 * @author mohanavelp
 *
 */
public class SignalMomentFactory extends AbstractSignalMomentFactory {

	public SignalMomentFactory(Composite parent) {
		super(parent);
	}

	@Override
	public void init(IRepositoryService repositoryService, ISecurity securities[]) {
		new RSI70PanelView(this, getParent());
		new RSIStochastic8080PanelView(this, getParent());
		new RSIStochastic7080PanelView(this, getParent());
		new RSIStochastic3020PanelView(this, getParent());
		
		setSecurities(securities);
		setRepositoryService(repositoryService);
		
		initComponents();
		processMomentum();
	}
}
