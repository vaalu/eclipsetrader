/**
 * 
 */
package org.eclipsetrader.ui.internal.markets.wizard;

import java.util.Arrays;

import org.eclipse.jface.wizard.Wizard;
import org.eclipsetrader.core.internal.markets.Market;
import org.eclipsetrader.core.internal.markets.MarketService;
import org.eclipsetrader.ui.internal.markets.ConnectorsWizardPage;

/**
 * @author mohanavelp
 *
 */
public class Nifty50Wizard extends Wizard {

	Nifty50WizardPage generalPage;
    Nifty50ScheduleWizardPage schedulePage;
    ConnectorsWizardPage connectorsPage;

    MarketService marketService;

    Market market;

    protected Nifty50Wizard() {
    }

    public Nifty50Wizard(MarketService marketService) {
        this.marketService = marketService;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        setWindowTitle("New Market");
        addPage(generalPage = new Nifty50WizardPage(marketService));
        addPage(schedulePage = new Nifty50ScheduleWizardPage());
        addPage(connectorsPage = new ConnectorsWizardPage());
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        market = new Market(generalPage.getMarketName(), Arrays.asList(schedulePage.getSchedule()), schedulePage.getTimeZone());
        market.setWeekDays(schedulePage.getWeekDays());
        market.setLiveFeedConnector(connectorsPage.getLiveFeedConnector());
        return true;
    }

    public MarketService getMarketService() {
        return marketService;
    }

    public Market getMarket() {
        return market;
    }
}
