package com.trade.transmission;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TradeFlow {
	
	private Map<String,Trade> allTrade = new ConcurrentHashMap<String,Trade>();
	
	//check if no trade Exists
	public boolean checkIfTradeEmpty() {
		return getAllTrade().isEmpty();
	}
	
	//Check if the lower version is being received by the store it will reject the trade and throw an exception. 
	//If the version is same it will override the existing record
	public void checkVersion(Trade t, int version) throws Exception {
		if (t.getVersion() < version) {
			throw new Exception(t.getVersion() + " is less than "+ version);
		}
	}
	
	//Check if maturityDate
	public boolean checkMaturityDate(Date maturityDate, Date CurrentDate) {
		if (CurrentDate.compareTo(maturityDate) > 0) {
			return false;
		}
		
		return true;
	}
	
	public void checkExpiredDates() {
		
		Date currentDate = new Date();
		
		for (String strKey : getAllTrade().keySet()) {
		    if (currentDate.compareTo(getAllTrade().get(strKey).getMaturityDate()) > 0) {
	    		Trade t = getAllTrade().get(strKey);
	    		t.setExpired('Y');
	    		getAllTrade().replace(strKey, t);
		    }
		}
	}
	
	//add Trade
	public void addTrade(Trade T) throws Exception {
		if (getAllTrade().containsKey(T.getTradeId())) {
			checkVersion(T, getAllTrade().get(T.getTradeId()).getVersion());
			
			if (checkMaturityDate(T.getMaturityDate(), getAllTrade().get(T.getTradeId()).getMaturityDate())) {
				getAllTrade().replace(T.getTradeId(), T);
				System.out.println(T.getTradeId() + " is added to the Store");
			} else {
				System.out.println("Not able to add " + T.getTradeId()+" in the store as maturity date is lower than current date");
			}
		} else {
			
			if (checkMaturityDate(T.getMaturityDate(), T.getCreatedDate())) {
				getAllTrade().put(T.getTradeId(), T);
				System.out.println(T.getTradeId()+" is added to the Store"); 
			} else {
				System.out.println("Not able to add " + T.getTradeId()+" in the store as maturity date is lower than current date");
			}
		}
	}
	
	//get trade
	public Trade getTrade(String tId) throws Exception {
		if (getAllTrade().containsKey(tId)) {
			return getAllTrade().get(tId);
		}
		
		throw new Exception ("Trade with " + tId + " not Found");
	}

	public Map<String, Trade> getAllTrade() {
		return allTrade;
	}
}
