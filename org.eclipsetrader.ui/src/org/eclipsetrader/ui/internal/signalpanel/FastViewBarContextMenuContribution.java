/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.internal.IChangeListener;
import org.eclipse.ui.internal.IntModel;
import org.eclipse.ui.internal.RadioMenu;
import org.eclipse.ui.internal.WorkbenchMessages;

/**
 * @author mohanavelp
 *
 */
public class FastViewBarContextMenuContribution extends ContributionItem {
    private MenuItem orientationItem;
    private MenuItem restoreItem;
    private MenuItem closeItem;
    private RadioMenu radioButtons;
    private IViewReference selectedView;
    private IntModel currentOrientation = new IntModel(SWT.VERTICAL);
    
    private IChangeListener orientationChangeListener = new IChangeListener() {
        public void update(boolean changed) {
        }
    };
    
    public FastViewBarContextMenuContribution() {
        currentOrientation.addChangeListener(orientationChangeListener);
    }

    public void fill(Menu menu, int index) {
        // TODO Auto-generated method stub
        super.fill(menu, index);
        

        orientationItem = new MenuItem(menu, SWT.CASCADE, index++);
        {
            orientationItem.setText(WorkbenchMessages.FastViewBar_view_orientation);

            Menu orientationSwtMenu = new Menu(orientationItem);
            RadioMenu orientationMenu = new RadioMenu(orientationSwtMenu,
                    currentOrientation);
            orientationMenu
                    .addMenuItem(
                            WorkbenchMessages.FastViewBar_horizontal, new Integer(SWT.HORIZONTAL)); 
            orientationMenu
                    .addMenuItem(
                            WorkbenchMessages.FastViewBar_vertical, new Integer(SWT.VERTICAL)); 

            orientationItem.setMenu(orientationSwtMenu);
        }

        restoreItem = new MenuItem(menu, SWT.CHECK, index++);
        restoreItem.setText(WorkbenchMessages.ViewPane_fastView);
        restoreItem.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            }
        });

        closeItem = new MenuItem(menu, SWT.NONE, index++);
        closeItem.setText(WorkbenchMessages.WorkbenchWindow_close); 
        closeItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                if (selectedView != null) {
                }
            }
        });

       
        // Set menu item enablement etc based on whether a view is selected
        
        if (selectedView != null) {
        } else {
        	restoreItem.setEnabled(false);
        }
        restoreItem.setSelection(true);
        
        if (selectedView != null) {
		} else {
			closeItem.setEnabled(false);
		}
        
        orientationItem.setEnabled(selectedView != null);
        if (selectedView != null) {
        }
    }
    
    public void setTarget(IViewReference selectedView) {
        this.selectedView = selectedView;
    }

    public boolean isDynamic() {
        return true;
    }
    
    public void dispose() {
        if (radioButtons != null) {
            radioButtons.dispose();
        }
        super.dispose();
    }
}
