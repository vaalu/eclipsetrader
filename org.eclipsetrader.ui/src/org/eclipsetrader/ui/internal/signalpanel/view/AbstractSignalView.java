/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import in.jeani.n50.utils.bo.StockInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.ui.internal.signalpanel.RSIComparator;

/**
 * @author mohanavelp
 *
 */
public abstract class AbstractSignalView implements ISignalView {

	private Composite parent = null;
	private Group group = null;
	private String viewName = null;
	private Composite currentPanel = null;
    private TableViewer tableViewer = null;
    private final List<StockInfo> filteredStocks = new ArrayList<StockInfo>();
    
	public AbstractSignalView(ISignalMoment view, Composite parent, String viewName) {
		this.parent = parent;
		this.setViewName(viewName);
		view.addSignalView(this);
		init();
	}
	
	public void init() {
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		parent.setLayout(layout);
		
		group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout gridLayout = new GridLayout ();
		group.setLayout(gridLayout);
		
		group.setText(viewName);
	}
	
	public void initComponents(IRepositoryService repositoryService, ISecurity[] securities) {
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		
		tableViewer = SignalMomentTable.getViewer(getGroup());
		tableViewer.getTable().setLayoutData(gridData);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
	}
	
	public abstract void process(double rsi, double stochastic, ISecurity security);
	
	@Override
	public void cleanUp() {
		this.filteredStocks.clear();
	}

	@Override
	public void updateView() {
		Collections.sort(filteredStocks, new RSIComparator());
		tableViewer.setInput(filteredStocks);
	}
	
	protected StockInfo getStockInfo(ISecurity security, double rsiValue, double stochVal) {
		StockInfo stock = new StockInfo();
		stock.setCompanyName(security.getName());
		stock.setSymbol(security.getIdentifier().getSymbol());
		stock.setRsi(rsiValue);
		stock.setStochastic(stochVal);
		return stock;
	}
	
	@Override
	public void updateMomentum(Collection<StockInfo> stocks) {
		tableViewer.setInput(null);
		tableViewer.setInput(stocks);
	}

	/**
	 * @return the currentPanel
	 */
	public Composite getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * @param currentPanel the currentPanel to set
	 */
	public void setCurrentPanel(Composite currentPanel) {
		this.currentPanel = currentPanel;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * @return the filteredStocks
	 */
	public List<StockInfo> getFilteredStocks() {
		return filteredStocks;
	}
}
