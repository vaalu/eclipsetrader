/**
 * 
 */
package org.eclipsetrader.ui.internal.markets.wizard;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipsetrader.ui.internal.markets.MarketTimeElement;
import org.eclipsetrader.ui.internal.markets.TimeScheduleEditor;

/**
 * @author mohanavelp
 *
 */
public class Nifty50TimeScheduleEditor extends TimeScheduleEditor {

	public Nifty50TimeScheduleEditor(Composite parent) {
        content = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = gridLayout.marginHeight = 0;
        content.setLayout(gridLayout);
        content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        createViewer(content);
        createButtons(content);
    }
	@Override
	protected List<MarketTimeElement> getInput() {
		return super.getInput();
	}
	@Override
	protected TableViewer getViewer() {
		return super.getViewer();
	}
}
