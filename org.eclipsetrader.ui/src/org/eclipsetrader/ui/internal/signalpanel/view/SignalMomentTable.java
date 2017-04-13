/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipsetrader.ui.internal.securities.wizards.nifty50.Nifty50Columns;

/**
 * @author mohanavelp
 * 
 */
public class SignalMomentTable {
	/** The viewer itself. */
	private static TableViewer viewer = null;

	public static TableViewer getViewer(Composite parent) {

		createViewer(parent);
		return viewer;
	}
	public static void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setContentProvider(new ArrayContentProvider());
		
		final Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		addColumnHeader(Nifty50Columns.COMPANY_NAME);
		addColumnHeader(Nifty50Columns.SYMBOL);
		addColumnHeader(Nifty50Columns.RSI);
		addColumnHeader(Nifty50Columns.STOCHASTIC);
		
		viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
	}
	
	public static void addColumnHeader(Nifty50Columns nifty50Column) {
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn(); 
		column.setText(nifty50Column.getHeader());
		column.setWidth(100);
		column.setResizable(true);
		viewerColumn.setLabelProvider(nifty50Column.getProvider());
	}
}
