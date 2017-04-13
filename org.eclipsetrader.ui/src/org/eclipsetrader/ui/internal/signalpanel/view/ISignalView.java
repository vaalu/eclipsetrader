/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import in.jeani.n50.utils.bo.StockInfo;

import java.util.Collection;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.repositories.IRepositoryService;

/**
 * @author mohanavelp
 *
 */
public interface ISignalView {
	public void init();
	public String getViewName();
	public void setViewName(String partName);
	public void initComponents(IRepositoryService repositoryService, ISecurity[] securities);
	public void updateMomentum(Collection<StockInfo> stocks);
	public TableViewer getTableViewer();
	public void process(final double rsi, final double stochastic, final ISecurity security);
	public void updateView();
	public void cleanUp();
}
