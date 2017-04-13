/**
 * 
 */
package org.eclipsetrader.ui.internal.signalpanel.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.widgets.Composite;
import org.eclipsetrader.core.feed.IHistory;
import org.eclipsetrader.core.feed.IOHLC;
import org.eclipsetrader.core.feed.TimeSpan;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.repositories.IRepositoryChangeListener;
import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.core.repositories.RepositoryChangeEvent;
import org.eclipsetrader.ui.internal.charts.DataImportJob;
import org.eclipsetrader.ui.internal.signalpanel.MAType;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

/**
 * @author mohanavelp
 *
 */
public abstract class AbstractSignalMomentFactory implements ISignalMoment {

	private List<ISignalView> signalViews = new ArrayList<ISignalView>();
	private Composite parent = null;
	private Map<String, ISignalView> allSignalViews = new HashMap<String, ISignalView>();
	private ISecurity securities[] = null;
	private IRepositoryService repositoryService = null;

	private int kFastPeriod = 7;
    private int kSlowPeriod = 21;
    private MAType kMaType = MAType.EMA;
    private int dPeriod = 14;
    private MAType dMaType = MAType.EMA;
    
    private int timePeriod = 5;
	
	/**
	 * @param view
	 */
	public AbstractSignalMomentFactory(Composite parent) {
		this.parent = parent;
	}
	
	public abstract void init(IRepositoryService repositoryService, ISecurity securities[]);
	
	public void initComponents() {
		for (ISignalView view : signalViews) {
			view.initComponents(repositoryService, securities);
		}
		addRepositoryListener();
		AbstractSignalMomentFactory factory = this;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DataImportThread(securities, factory), 6000, 60000);
		
	}
	public void processMomentum(int timePeriod) {
		this.timePeriod = timePeriod;
		for (ISignalView view : signalViews) {
			view.cleanUp();
		}
		processMomentum();
	}
	public void processMomentum() {
		Core core = new Core();
        int lookback = core.rsiLookback(7);
		for (ISecurity security : securities) {
    		IHistory history = repositoryService.getHistoryFor(security);
    		if (history != null) {
    			history = history.getSubset(null, null, TimeSpan.minutes(timePeriod));
    			IOHLC iohlcData[] = history.getOHLC();
    			if (iohlcData.length >= lookback) {
    				double[] outReal = getRSI(core, iohlcData);
    				double[] stochastic = getStochastic(core, iohlcData);
    				double lastRsi = 0.0;
    				double lastStochastic = 0.0;
    				
    				if (outReal != null && outReal.length > 0) {
    					lastRsi = outReal[outReal.length - 1];
    				}
    				if (stochastic != null && stochastic.length > 0) {
    					lastStochastic = stochastic[stochastic.length - 1];
    				}
    				processMomentumInViews(lastRsi, lastStochastic, security);
    			}
    		}
    	}
		updateViews();
	}
	public void addRepositoryListener() {
		repositoryService.addRepositoryResourceListener(new IRepositoryChangeListener() {
			
			@Override
			public void repositoryResourceChanged(RepositoryChangeEvent event) {
				System.out.println("Repo data changed... updating momentum data");
				processMomentum(timePeriod);
			}
		});
	}
	class DataImportThread extends TimerTask {
		ISecurity securities[] = null;
		AbstractSignalMomentFactory factory = null;
		public DataImportThread(ISecurity[] securities, AbstractSignalMomentFactory factory) {
			this.securities = securities;
			this.factory = factory;
		}
		@Override
		public void run() {
			importData(this.securities);
			if (this.factory != null) {
				factory.updateView();
			}
		}
	}
	
	public void updateView() {
		parent.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				processMomentum(getTimePeriod());
			}
		});
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
	public void processMomentumInViews(final double rsi, final double stochastic, final ISecurity security) {
		for (ISignalView view : signalViews) {
			view.process(rsi, stochastic, security);
		}
	}
	
	public void updateViews() {
		for (ISignalView view : signalViews) {
			view.updateView();
		}
	}
	
	/**
	 * @param view
	 */
	public void addSignalView(ISignalView view) {
		if (!signalViews.contains(view)) {
			signalViews.add(view);
			allSignalViews.put(view.getViewName(), view);
		}
	}
	
	public ISignalView getView(String viewName) {
		//log("All views: " + allSignalViews + " : " + viewName);
		return allSignalViews.get(viewName);
	}
	
	private double[] getRSI(Core core, IOHLC iohlcData[]) {
		int startIdx = 0;
		int endIdx = iohlcData.length - 1;
		int lookback = core.rsiLookback(7);
		double[] inReal = new double[iohlcData.length];
		int i = 0;
		for (IOHLC iohlc : iohlcData) {
			inReal[i++] = iohlc.getClose();
		}
		
		MInteger outBegIdx = new MInteger();
		MInteger outNbElement = new MInteger();
		double[] outReal = new double[iohlcData.length - lookback];
		core.rsi(startIdx, endIdx, inReal, 7, outBegIdx, outNbElement, outReal);
		return outReal;
	}
	
	private double[] getStochastic(Core core, IOHLC iohlcData[]) {
		
		int startIdx = 0;
        int endIdx = iohlcData.length - 1;
        
        double[] inHigh = new double[iohlcData.length];//Util.getValuesForField(values, OHLCField.High);
        double[] inLow = new double[iohlcData.length];//Util.getValuesForField(values, OHLCField.Low);
        double[] inClose = new double[iohlcData.length];//Util.getValuesForField(values, OHLCField.Close);
        
        int lookback = core.stochLookback(kFastPeriod, kSlowPeriod, MAType.getTALib_MAType(kMaType), dPeriod, MAType.getTALib_MAType(dMaType));
        if (iohlcData.length < lookback) {
            return null;
        }
        
        MInteger outBegIdx = new MInteger();
        MInteger outNbElement = new MInteger();
        double[] outK = new double[iohlcData.length - lookback];
        double[] outD = new double[iohlcData.length - lookback];
        
        int i = 0;
        for (IOHLC iohlc : iohlcData) {
        	
        	inHigh[i] = iohlc.getHigh();
        	inLow[i] = iohlc.getLow();
        	inClose[i] = iohlc.getClose();
        	
        	i++;
        }
        
		core.stoch(startIdx, endIdx, inHigh, inLow, inClose, kFastPeriod, kSlowPeriod, MAType.getTALib_MAType(kMaType), dPeriod, MAType.getTALib_MAType(dMaType), outBegIdx, outNbElement, outK, outD);
		/*System.out.println();
		for (double d : outK) {
			System.out.print("\t" + d);
		}*/
		return outK;
		
	}
	
	private Date getStartOfDay() {
	    Calendar calendar = Calendar.getInstance();
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 0, 0, 0);
	    return calendar.getTime();
	}
	
	public static void log(String str) {
		System.out.println(str);
	}
	
	/**
	 * @return
	 */
	public List<ISignalView> getAllViews() {
		return signalViews;
	}

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Composite parent) {
		this.parent = parent;
	}

	/**
	 * @return the securities
	 */
	public ISecurity[] getSecurities() {
		return securities;
	}

	/**
	 * @param securities the securities to set
	 */
	public void setSecurities(ISecurity[] securities) {
		this.securities = securities;
	}

	/**
	 * @return the repositoryService
	 */
	public IRepositoryService getRepositoryService() {
		return repositoryService;
	}

	/**
	 * @param repositoryService the repositoryService to set
	 */
	public void setRepositoryService(IRepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * @return the timePeriod
	 */
	public int getTimePeriod() {
		return timePeriod;
	}
	
}
