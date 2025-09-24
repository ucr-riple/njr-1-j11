package main.implementations.order;

import java.util.Date;

/**
 * Descriptive Order class with type meaningful fields. 
 */
public class Order {
	
	private String instrument;
	private Date dateTime;
	private String recordType;
	private double price;
	private int volume;
	private int undisclosedVolume;
	private double value;
	private String qualifiers;
	private String transactionId;
	private String bidId;
	private String askId;
	private boolean isBid;
	private Date entryTime;
	private double oldPrice;
	private int oldVolume;
	private String buyerBrokerId;
	private String sellerBrokerId;
	
	/**
	 * Constructor that takes in all required attributes to create a valid order.
	 */
	public Order(final String instrument,
					final Date dateTime,
					final String recordType,
					final double price,
					final int volume,
					final int undisclosedVolume,
					final double value,
					final String qualifiers,
					final String transactionId,
					final String bidId,
					final String askId,
					final boolean isBid,
					final Date entryTime,
					final double oldPrice,
					final int oldVolume,
					final String buyerBrokerId,
					final String sellerBrokerId) {
		
		setInstrument(instrument);
		setDateTime(dateTime);
		setRecordType(recordType);
		setPrice(price);
		setVolume(volume);
		setUndisclosedVolume(undisclosedVolume);
		setValue(value);
		setQualifiers(qualifiers);
		setTransactionId(transactionId);
		setBidId(bidId);
		setAskId(askId);
		setBid(isBid);
		setEntryTime(entryTime);
		setOldPrice(oldPrice);
		setOldVolume(oldVolume);
		setBuyerBrokerId(buyerBrokerId);
		setSellerBrokerId(sellerBrokerId);
		
	}
	
	/**
	 * Getters and setters for all attributes.
	 */
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getUndisclosedVolume() {
		return undisclosedVolume;
	}
	public void setUndisclosedVolume(int undisclosedVolume) {
		this.undisclosedVolume = undisclosedVolume;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getQualifiers() {
		return qualifiers;
	}
	public void setQualifiers(String qualifiers) {
		this.qualifiers = qualifiers;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getAskId() {
		return askId;
	}
	public void setAskId(String askId) {
		this.askId = askId;
	}
	public boolean isBid() {
		return isBid;
	}
	public void setBid(boolean isBid) {
		this.isBid = isBid;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public int getOldVolume() {
		return oldVolume;
	}
	public void setOldVolume(int oldVolume) {
		this.oldVolume = oldVolume;
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
	
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o != null) {
			Order order = (Order) o;
			if (this.isBid == order.isBid
					&& this.askId == order.askId
					&& this.bidId == order.bidId
					&& this.buyerBrokerId == order.buyerBrokerId
					&& this.dateTime == order.dateTime
					&& this.entryTime == order.entryTime
					&& this.instrument == order.instrument
					&& this.oldPrice == order.oldPrice
					&& this.oldVolume == order.oldVolume
					&& this.price == order.price
					&& this.qualifiers == order.qualifiers
					&& this.recordType == order.recordType
					&& this.sellerBrokerId == order.sellerBrokerId
					&& this.transactionId == order.transactionId
					&& this.undisclosedVolume == order.undisclosedVolume
					&& this.value == order.value
					&& this.volume == order.volume) {
				equals = true;
			}
		}
		
		return equals;
	}
	
}
