/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel;

import in.jeani.n50.utils.bo.StockInfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.markets.IMarket;
import org.eclipsetrader.core.markets.IMarketService;
import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.ui.internal.UIActivator;
import org.eclipsetrader.ui.internal.charts.views.SelectionProvider;
import org.eclipsetrader.ui.internal.signalpanel.view.SignalMomentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author mohanavelp
 *
 */
public class SignalPanel extends ViewPart {


	public static final String K_ID = "id"; //$NON-NLS-1$
    public static final String K_NAME = "name"; //$NON-NLS-1$
    public static final String K_DESCRIPTION = "description"; //$NON-NLS-1$
    public static final String K_ICON = "icon"; //$NON-NLS-1$
    public static final String K_CATEGORY = "category"; //$NON-NLS-1$
    
    protected LabelControlContribution timePeriodLabel = null;
    protected SignalMomentFactory momentumFactory = null;
    protected IToolBarManager toolBarManager = null;
    
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
        content.setLayout(layout);
        setTitleToolTip(getPartName());
        
        IRepositoryService repositoryService = getRepositoryService();
        final IMarket selectedMarket = getNSEMarket();
        if (selectedMarket != null && selectedMarket.getMembers() != null) {
        	ISecurity securities[] = selectedMarket.getMembers();
        	momentumFactory = new SignalMomentFactory(content);
            momentumFactory.init(repositoryService, securities);
        	
        }
		
		content.setVisible(true);
		parent.setVisible(true);
		parent.pack();
	}
	
	private IMarket getNSEMarket() {
		
		IMarket nseMarket = null;
		IMarket[] allMarkets = getMarkets();
		for (IMarket market : allMarkets) {
			String marketName = market.getName();
			if (marketName != null && marketName.equalsIgnoreCase("NSE-Nifty50")) {
				nseMarket = market;
				break;
			}
		}
		return nseMarket;
	}
	protected IMarket[] getMarkets() {
        BundleContext context = UIActivator.getDefault().getBundle().getBundleContext();
        ServiceReference serviceReference = context.getServiceReference(IMarketService.class.getName());
        if (serviceReference != null) {
            IMarketService marketService = (IMarketService) context.getService(serviceReference);
            return marketService.getMarkets();
        }
        return new IMarket[0];
    }
	
	protected IRepositoryService getRepositoryService() {
        IRepositoryService service = null;
        BundleContext context = UIActivator.getDefault().getBundle().getBundleContext();
        ServiceReference serviceReference = context.getServiceReference(IRepositoryService.class.getName());
        if (serviceReference != null) {
            service = (IRepositoryService) context.getService(serviceReference);
            context.ungetService(serviceReference);
        }
        return service;
    }
	
	private static List<StockInfo> getSampleStocks() {
		List<StockInfo> stocks = new ArrayList<StockInfo>();
		
		StockInfo info = new StockInfo();
		info.setCompanyName("ACC");
		info.setSymbol("ACC.BO");
		info.setIndustry("Infra");
		stocks.add(info);
		
		info = new StockInfo();
		info.setCompanyName("BSNL");
		info.setSymbol("BSNL.BO");
		info.setIndustry("Infra");
		stocks.add(info);
		
		return stocks;
	}
	
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		site.setSelectionProvider(new SelectionProvider());
		UIActivator.getDefault().getBundle().getBundleContext();
        toolBarManager = site.getActionBars().getToolBarManager();
        timePeriodLabel = new LabelControlContribution("5 mins");
        Action menu = getMenu("Period");
        
        toolBarManager.add(timePeriodLabel);
        toolBarManager.add(menu);
	}
	
	private Action getMenu(String title) {
		return new TimePeriodMenu(title, this);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}
	
	public class LabelControlContribution extends ControlContribution
	{

		private Label label = null;
	    protected LabelControlContribution(String id)
	    {
	        super(id);
	    }
	    
	    @Override
	    protected Control createControl(Composite parent)
	    {
	    	label = new Label(parent, SWT.BEGINNING);
	    	label.setText("Period: 5 mins  ");
	        return label;
	    }
	    
	    public void update(String title) {
	    	title = "Period: " + title + "\t\t";
	    	label.setText(title);
	    }

	}
}
