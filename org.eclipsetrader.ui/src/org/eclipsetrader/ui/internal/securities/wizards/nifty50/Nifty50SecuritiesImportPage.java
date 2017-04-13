/**
 * 
 */
package org.eclipsetrader.ui.internal.securities.wizards.nifty50;

import in.jeani.n50.exceptions.Nifty50UtilException;
import in.jeani.n50.utils.Nifty50Util;
import in.jeani.n50.utils.bo.StockInfo;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipsetrader.core.feed.FeedIdentifier;
import org.eclipsetrader.core.feed.FeedProperties;
import org.eclipsetrader.core.feed.IFeedIdentifier;
import org.eclipsetrader.core.feed.TimeSpan;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.instruments.Stock;
import org.eclipsetrader.core.markets.IMarket;
import org.eclipsetrader.core.markets.IMarketService;
import org.eclipsetrader.core.repositories.IRepository;
import org.eclipsetrader.core.repositories.IRepositoryRunnable;
import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.ui.internal.UIActivator;
import org.eclipsetrader.ui.internal.charts.DataImportJob;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author mohanavelp
 *
 */
public class Nifty50SecuritiesImportPage extends WizardPage {

	private static final String NIFTY50_SECURITIES_PAGE_TITLE="Nifty50 Securities";
	private static Locale IN_LOCALE = new Locale("en", "IN");
	private static Currency IN_CURRENCY = Currency.getInstance(IN_LOCALE);
	
	private Text text;
	private Composite container;
	
	protected Nifty50SecuritiesImportPage(String pageName) {
		super(pageName);
		setTitle(NIFTY50_SECURITIES_PAGE_TITLE);
		setDescription("Fetching the list of Nifty 50 Securities");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		
		container.setLayout(layout);
		Label label1 = new Label(container, SWT.NONE);
        label1.setText("Nifty 50 Stocks && Symbols");
        
		setControl(container);
		
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
		gridData.horizontalSpan = 2;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		
		TableViewer tableViewer = Nifty50SecuritiesTableViewer.getViewer(container);
		tableViewer.getTable().setLayoutData(gridData);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		Nifty50Util nifty50util = new Nifty50Util();
		List<StockInfo> niftySecurities = new ArrayList<StockInfo>();
		Nifty50SecuritiesConsolidator consolidator = null;
		
		try {
			niftySecurities = nifty50util.fetchNifty50Stocks();
			ISecurity[] prevSecurities = getNSEMarket().getMembers();
			
			consolidator = new Nifty50SecuritiesConsolidator(niftySecurities, prevSecurities);
			consolidator.consolidate();
			
			tableViewer.setInput(consolidator.getAddedNifty50Stocks());
			
			if (!niftySecurities.isEmpty()) {
				addNifty50Securities(consolidator.getAddedNifty50Stocks(), consolidator.getRemovedNifty50Stocks());
			}
		} catch (Nifty50UtilException e) {
			e.printStackTrace();
		}
		container.setLayout(layout);
		setPageComplete(true);
		
	}
	
	protected void addNifty50Securities(List<StockInfo> newNifty50Stocks, List<ISecurity> removedNifty50Stocks) {
		
		ISecurity[] nifty50Arr = null;
		final IRepository repository = getRepositoryService().getRepository("local");
		final IMarket selectedMarket = getNSEMarket();
		
		if (!newNifty50Stocks.isEmpty()) {
			nifty50Arr = new ISecurity[newNifty50Stocks.size()];
			int posn = 0;
			for (StockInfo stockInfo : newNifty50Stocks) {
				final Stock resource = getStock(stockInfo);
				final IRepositoryService service = UIActivator.getDefault().getRepositoryService();
				nifty50Arr[posn++] = resource;
				service.runInService(new IRepositoryRunnable() {
					
					@Override
					public IStatus run(IProgressMonitor monitor) throws Exception {
						service.moveAdaptable(new IAdaptable[] {
								resource
						}, repository);
						selectedMarket.addMembers(new ISecurity[] {
								resource
						});
						return Status.OK_STATUS;
					}
				}, null);
			}
		}
		
		if (removedNifty50Stocks != null && !removedNifty50Stocks.isEmpty()) {
			final IRepositoryService service = UIActivator.getDefault().getRepositoryService();
			for (final ISecurity security : removedNifty50Stocks) {
				service.runInService(new IRepositoryRunnable() {
					
					@Override
					public IStatus run(IProgressMonitor monitor) throws Exception {
						service.moveAdaptable(new IAdaptable[] {
								security
						}, repository);
						selectedMarket.removeMembers(new ISecurity[] {
								security
						});
						return Status.OK_STATUS;
					}
				}, null);
			}
		}
		
		if (selectedMarket != null && selectedMarket.getMembers().length > 0) {
			nifty50Arr = selectedMarket.getMembers();
		} 
		importData(nifty50Arr);
	}
	
	private void importData(ISecurity[] securities) {
		DataImportJob job = new DataImportJob(securities, DataImportJob.INCREMENTAL, null, null, new TimeSpan[] {
				TimeSpan.days(1),
				TimeSpan.minutes(1),
				TimeSpan.minutes(2),
				TimeSpan.minutes(3),
				TimeSpan.minutes(5),
				TimeSpan.minutes(10),
				TimeSpan.minutes(15),
				TimeSpan.minutes(30),
		});
		job.setUser(true);
		job.schedule();
		
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
	
	
	private Stock getStock(StockInfo stockInfo) {

		String yahooIdentifier = stockInfo.getSymbol() + ".NS";
		
		FeedProperties properties = new FeedProperties();
		properties.setProperty("org.eclipsetrader.yahoo.symbol", yahooIdentifier);
		
		IFeedIdentifier feedIdentifier = new FeedIdentifier(yahooIdentifier, properties);
		
		final Stock resource = new Stock(stockInfo.getCompanyName(), feedIdentifier, IN_CURRENCY);
		
		return resource;
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
	
	protected IFeedIdentifier getFeedIdentifierFromSymbol(String s) {
        return UIActivator.getDefault().getRepositoryService().getFeedIdentifierFromSymbol(s);
    }
	
	protected IRepositoryService getRepositoryService() {
		return UIActivator.getDefault().getRepositoryService();
	}

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

}
