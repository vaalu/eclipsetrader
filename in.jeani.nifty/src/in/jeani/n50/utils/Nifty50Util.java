/**
 * 
 */
package in.jeani.n50.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import in.jeani.n50.exceptions.Nifty50UtilException;
import in.jeani.n50.utils.bo.StockInfo;

/**
 * @author mohanavelp
 *
 */
public class Nifty50Util {

	private static String NIFTY_50_FILE_URL = "https://www.nseindia.com/content/indices/ind_nifty50list.csv";
	
	public List<StockInfo> fetchNifty50Stocks() throws Nifty50UtilException {
		InputStream n50Stream = null;
    	InputStreamReader inputReader = null;
    	BufferedReader buffReader = null;
    	CSVReader csvReader = null;
    	StockInfo stockInfo = null;
    	
    	String [] nextLine;
    	List<StockInfo> nifty50List = new ArrayList<StockInfo>();
    	try {
    		n50Stream = getN50File();
    		inputReader = new InputStreamReader(n50Stream);
    		buffReader = new BufferedReader(inputReader);
    		
			csvReader = new CSVReader(buffReader);
			csvReader.readNext();
			while ((nextLine = csvReader.readNext()) != null) {
				stockInfo = getStockInfo(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4]);
				nifty50List.add(stockInfo);
			}
		} catch (FileNotFoundException e) {
			Nifty50UtilException.get("Unable to find Nifty50.", e);
		} catch (IOException e) {
			Nifty50UtilException.get("Error occurred while fetching Nifty50.", e);
		} finally {
			try {
				if (csvReader!=null) {
					csvReader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return nifty50List;
	}
	
	private StockInfo getStockInfo(String companyName, String industry, String symbol, String series, String isinCode) {
		
		StockInfo stockInfo = new StockInfo();
		
		stockInfo.setCompanyName(companyName);
		stockInfo.setIndustry(industry);
		stockInfo.setIsinCode(isinCode);
		stockInfo.setSeries(series);
		stockInfo.setSymbol(symbol);
		
		return stockInfo;
	}
	
	public static InputStream getN50File() throws Nifty50UtilException {
    	URL fileUrl = null;
    	HttpURLConnection httpConn = null;
    	int responseCode = HttpURLConnection.HTTP_NOT_FOUND;
    	InputStream n50CSVStream = null;
    	
    	try {
    		System.out.println("Fetching N50 Stream");
			fileUrl = new URL(NIFTY_50_FILE_URL);
			httpConn = (HttpURLConnection) fileUrl.openConnection();
			responseCode = httpConn.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				n50CSVStream = httpConn.getInputStream();
			}
			System.out.println("Finished fetching N50 Stream");
		} catch (MalformedURLException e) {
			Nifty50UtilException.get("Invalid url for fetching Nifty50.", e);
		} catch (IOException e) {
			Nifty50UtilException.get("Error occurred while fetching Nifty50.", e);
		}
    	return n50CSVStream;
    }
	
}
