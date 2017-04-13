/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author mohanavelp
 * 
 */
public class TimePeriodMenu extends Action implements IMenuCreator {

	private Menu timePeriodMenu;
	SignalPanel panel;
	
	public TimePeriodMenu(String title, SignalPanel panel) {
		setText(title);
		setMenuCreator(this);
		this.panel = panel;
	}

	@Override
	public void dispose() {
		if (timePeriodMenu != null) {
			timePeriodMenu.dispose();
			timePeriodMenu = null;
		}
	}

	public void updatePanel(int period) {
		System.out.println("Updating with " + period);
		panel.momentumFactory.processMomentum(period);
		panel.timePeriodLabel.update(period+" Min");
		/*
		this.setText(period + " : Min");
		ActionContributionItem openViewItem = new ActionContributionItem(this);
		openViewItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);

		panel.toolBarManager.add(openViewItem);*/
	}
	
	@Override
	public Menu getMenu(Control parent) {
		if (timePeriodMenu != null)
			timePeriodMenu.dispose();

		timePeriodMenu = new Menu(parent);
		Action min05Action = new Action("5 min") {
			public void run() {
				updatePanel(5);
				this.setChecked(true);
			}
		};
		Action min15Action = new Action("15 min") {
			public void run() {
				updatePanel(15);
				this.setChecked(true);
			}
		};
		Action min30Action = new Action("30 min") {
			public void run() {
				updatePanel(30);
				this.setChecked(true);
			}
		};
		
		addActionToMenu(timePeriodMenu, min05Action);
		addActionToMenu(timePeriodMenu, min15Action);
		addActionToMenu(timePeriodMenu, min30Action);
		
		new MenuItem(timePeriodMenu, SWT.SEPARATOR);

		return timePeriodMenu;
	}

	protected void addActionToMenu(Menu parent, Action action) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(parent, -1);
	}

	@Override
	public Menu getMenu(Menu arg0) {
		return null;
	}
}
