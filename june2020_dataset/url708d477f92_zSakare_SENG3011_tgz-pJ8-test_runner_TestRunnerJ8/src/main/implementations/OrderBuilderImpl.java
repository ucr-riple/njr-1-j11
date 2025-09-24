 package main.implementations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.implementations.order.Order;
import main.interfaces.OrderBuilder;
import main.parser.SircaOrder;

/**
 * Order helper class to build orders based on given parameters and parsed orders from csv files. 
 */
public class OrderBuilderImpl implements OrderBuilder {
	
	private String security;	
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
	
	public OrderBuilderImpl(String security, Date date, double price, String volume, 
				String isBid, String brokerId) {

		setSecurity(security);
		setBid(isBid);
		if (("B").equals(isBid)) {
			setBuyerBrokerId(brokerId);
			setSellerBrokerId(DEFAULT_SELLER_BROKER_ID);
		} else {
			setSellerBrokerId(brokerId);
			setBuyerBrokerId(DEFAULT_BUYER_BROKER_ID);
		}
		setDateTime(date);
		setPrice(price);
		setVolume(volume);
		setValue(this.price*this.volume);
		setRecordType(DEFAULT_RECORD_TYPE);
		setBidId(DEFAULT_BIDID);
		setAskId(DEFAULT_ASKID);
		setEntryTime(DEFAULT_ENTRY_TIME);
		setOldPrice(DEFAULT_OLD_PRICE);
		setOldVolume(DEFAULT_OLD_VOLUME);
		setUndisclosedVolume(DEFAULT_UNDISCLOSED_VOLUME);
		setQualifiers(DEFAULT_QUALIFIERS);
		
	}
	
	public OrderBuilderImpl(SircaOrder order) {
		setSecurity(order.getInstrument());
		setBid(order.getBidOrAsk());
		setBuyerBrokerId(order.getBuyerBrokerId());
		setSellerBrokerId(order.getSellerBrokerId());
		setDateTime(order.getDate(), order.getTime());
		if (!order.getPrice().isEmpty()) {
			setPrice(order.getPrice());
		} else {
			setPrice(DEFAULT_PRICE);
		}
		if (!order.getVolume().isEmpty()) {
			setVolume(order.getVolume());
		} else {
			setVolume(DEFAULT_VOLUME);
		}
		setRecordType(order.getRecordType());
		setUndisclosedVolume(DEFAULT_UNDISCLOSED_VOLUME);
		setQualifiers(order.getQualifiers());
		setTransactionId(order.getTransactionId());
		setBidId(order.getBidId());
		setAskId(order.getAskId());
		setEntryTime(DEFAULT_ENTRY_TIME);
		setOldPrice(DEFAULT_OLD_PRICE);
		setOldVolume(DEFAULT_OLD_VOLUME);
		setValue(this.price*this.volume);
	}
	
	public OrderBuilderImpl (String security, Date date, double price, String volume,
				String brokerID) {
		
		setSecurity(security);
		setDateTime(date);
		setPrice(price);
		setVolume(volume);
		
	}
	
	public Order build() {
		return new Order(security, 
							dateTime, 
							recordType, 
							price, 
							volume, 
							undisclosedVolume, 
							value, 
							qualifiers, 
							transactionId, 
							bidId, 
							askId,
							isBid,
							entryTime,
							oldPrice,
							oldVolume,
							buyerBrokerId,
							sellerBrokerId);
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public void setBuyerBrokerId(String brokerId) {
		this.buyerBrokerId = brokerId;
	}
	
	public void setSellerBrokerId(String brokerId) {
		this.sellerBrokerId = brokerId;
	}

	public void setDateTime(String date, String time) {
		// Date and time format provided is 2013020500:00:00.000
		SimpleDateFormat sicraOrderDateFormat = new SimpleDateFormat("yyyyMMddHH:mm:ss.SSS");
		
		// Combine the date and time strings.
		String dateTime = date + time;
		
		try {
			// Save the newly created date object.
			this.dateTime = sicraOrderDateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setPrice(String price) {
		this.price = Double.parseDouble(price);
	}

	public void setBid(String bidOrAsk) {
		if (("A").equals(bidOrAsk)) {
			this.isBid = false;
		} else if (("B").equals(bidOrAsk)) {
			this.isBid = true;
		}
	}
	
	public void setVolume(String volume) {
		this.volume = Integer.parseInt(volume);
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public void setUndisclosedVolume(String undisclosedVolume) {
		this.undisclosedVolume = Integer.parseInt(undisclosedVolume);
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setQualifiers(String qualifiers) {
		this.qualifiers = qualifiers;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public void setAskId(String askId) {
		this.askId = askId;
	}

	public void setBid(boolean isBid) {
		this.isBid = isBid;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = Double.parseDouble(oldPrice);
	}

	public void setOldVolume(String oldVolume) {
		this.oldVolume = Integer.parseInt(oldVolume);
	}

}