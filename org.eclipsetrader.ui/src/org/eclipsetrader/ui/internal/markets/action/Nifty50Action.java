/**
 * 
 */
package org.eclipsetrader.ui.internal.markets.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipsetrader.core.internal.markets.MarketService;
import org.eclipsetrader.ui.internal.UIActivator;
import org.eclipsetrader.ui.internal.markets.wizard.Nifty50Wizard;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author mohanavelp
 *
 */
public class Nifty50Action  extends Action {

	private Shell shell;
	public Nifty50Action(Shell shell) {
		super("Nifty50");
        this.shell = shell;
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        BundleContext context = UIActivator.getDefault().getBundle().getBundleContext();
        ServiceReference serviceReference = context.getServiceReference(MarketService.class.getName());
        MarketService marketService = (MarketService) context.getService(serviceReference);
        
        Nifty50Wizard wizard = new Nifty50Wizard(marketService);
        
        //MarketWizard wizard = new MarketWizard(marketService);
        WizardDialog dlg = new WizardDialog(shell, wizard);
        dlg.setMinimumPageSize(450, 300);
        if (dlg.open() == Window.OK) {
            marketService.addMarket(wizard.getMarket());
        }

        context.ungetService(serviceReference);
    }
}
