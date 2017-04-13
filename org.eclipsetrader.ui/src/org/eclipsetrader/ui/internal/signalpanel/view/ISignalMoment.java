/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.repositories.IRepositoryService;

/**
 * @author mohanavelp
 *
 */
public interface ISignalMoment {

	public void addSignalView(ISignalView view);
	public void init(IRepositoryService repositoryService, ISecurity[] securities);
	public ISignalView getView(String viewName);
}
