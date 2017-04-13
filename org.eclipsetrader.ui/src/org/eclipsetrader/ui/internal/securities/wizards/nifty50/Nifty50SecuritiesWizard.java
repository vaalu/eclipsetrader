/**
 * 
 */
package org.eclipsetrader.ui.internal.securities.wizards.nifty50;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author mohanavelp
 *
 */
public class Nifty50SecuritiesWizard extends Wizard implements INewWizard {

	protected Nifty50SecuritiesImportPage importPage;
	private static final String NIFTY50_SECURITIES_PAGE_TITLE="Get Nifty50 Securities";
	
	private Image image;
	private boolean actionEnabled;
	
	public Nifty50SecuritiesWizard() {
		super();
		setNeedsProgressMonitor(true);
		/*
		ImageDescriptor descriptor = ImageDescriptor.createFromURL(UIActivator.getDefault().getBundle().getResource("icons/eview16/N5016x16.png"));
        image = descriptor.createImage();*/
	}
	
	@Override
	public void setWindowTitle(String newTitle) {
		super.setWindowTitle(NIFTY50_SECURITIES_PAGE_TITLE);
	}
	
	@Override
	public void addPages() {
		importPage = new Nifty50SecuritiesImportPage(NIFTY50_SECURITIES_PAGE_TITLE);
		addPage(importPage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		System.out.println("Data entered for Nifty50" + importPage.getText());
		actionEnabled = false;
		return true;
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
	}

	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#getDefaultPageImage()
     */
    @Override
    public Image getDefaultPageImage() {
        return image;
    }
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#dispose()
     */
    @Override
    public void dispose() {
        if (image != null) {
            image.dispose();
        }
        super.dispose();
    }

	/**
	 * @return the actionEnabled
	 */
	public boolean isActionEnabled() {
		return actionEnabled;
	}

	/**
	 * @param actionEnabled the actionEnabled to set
	 */
	public void setActionEnabled(boolean actionEnabled) {
		this.actionEnabled = actionEnabled;
	}
}
