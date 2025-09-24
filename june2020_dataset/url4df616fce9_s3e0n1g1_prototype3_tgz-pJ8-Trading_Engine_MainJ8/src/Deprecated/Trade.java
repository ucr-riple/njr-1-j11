package Deprecated;

import java.sql.Date;

/**
 * Class defining a trade order.
 * @author Dhruv
 *
 */
public class Trade {

	private String instrument;
	private Date date;
	private String recordType;
	private double price;
	private double volume;
	private double undisclosedVolume;
	private double oldVolume;
	private double value;
	private long transId;
	private long bidId;
	private long askId;
	private String bidAsk;
	private  double oldPrice;
	private Date entryTime;
	private String buyerBrokerId;
	private String sellerBrokerId;
	
	public Trade (String instrument,
		Date date,
		String recordType,
		double price,
		double volume,
		double undisclosedVolume,
		double oldVolume,
		double value,
		long transId,
		long bidId,
		long askId,
		String bidAsk,
		double oldPrice,
		Date entryTime,
		String buyerBrokerId,
		String sellerBrokerId) {
		
					setInstrument(instrument);
					setDate(date);
					setRecordType(recordType);
					setPrice(price);
					setVolume(volume);
					setUndisclosedVolume(undisclosedVolume);
					setValue(value);
					setTransId(transId);
					setBidId(bidId);
					setAskId(askId);
					setBidAsk(bidAsk);
					setEntryTime(entryTime);
					setOldPrice(oldPrice);
					setOldVolume(oldVolume);
					setBuyerBrokerId(buyerBrokerId);
					setSellerBrokerId(sellerBrokerId);
					
	}
	
	
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getUndisclosedVolume() {
		return undisclosedVolume;
	}
	public void setUndisclosedVolume(double undisclosedVolume) {
		this.undisclosedVolume = undisclosedVolume;
	}
	public double getOldVolume() {
		return oldVolume;
	}
	public void setOldVolume(double oldVolume) {
		this.oldVolume = oldVolume;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
	public long getBidId() {
		return bidId;
	}
	public void setBidId(long bidId) {
		this.bidId = bidId;
	}
	public long getAskId() {
		return askId;
	}
	public void setAskId(long askId) {
		this.askId = askId;
	}
	public String getBidAsk() {
		return bidAsk;
	}
	public void setBidAsk(String bidAsk) {
		this.bidAsk = bidAsk;
	}
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getBuyerBrokerId() {
		return buyerBrokerId;
	}
	public void setBuyerBrokerId(String buyerBrokerId) {
		this.buyerBrokerId = buyerBrokerId;
	}
	public String getSellerBrokerId() {
		return sellerBrokerId;
	}
	public void setSellerBrokerId(String sellerBrokerId) {
		this.sellerBrokerId = sellerBrokerId;
	}
}
