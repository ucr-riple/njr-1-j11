package main.parser;

/**
 * CSV parsed orders. Non type descriptive fields for each order, bare bones for containing basic information. 
 */
public class SircaOrder {
	
	private String instrument;
	private String date;
	private String time;
	private String recordType;
	private String price;
	private String volume;
	private String undisclosedVolume;
	private String value;
	private String qualifiers;
	private String transactionId;
	private String bidId;
	private String askId;
	private String bidOrAsk;
	private String entryTime;
	private String oldPrice;
	private String oldVolume;
	private String buyerBrokerId;
	private String sellerBrokerId;
	
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(final String instrument) {
		this.instrument = instrument;
	}
	public String getDate() {
		return date;
	}
	public void setDate(final String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(final String time) {
		this.time = time;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(final String recordType) {
		this.recordType = recordType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(final String price) {
		this.price = price;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(final String volume) {
		this.volume = volume;
	}
	public String getUndisclosedVolume() {
		return undisclosedVolume;
	}
	public void setUndisclosedVolume(final String undisclosedVolume) {
		this.undisclosedVolume = undisclosedVolume;
	}
	public String getValue() {
		return value;
	}
	public void setValue(final String value) {
		this.value = value;
	}
	public String getQualifiers() {
		return qualifiers;
	}
	public void setQualifiers(final String qualifiers) {
		this.qualifiers = qualifiers;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBidId() {
		return bidId;
	}
	public void setBidId(final String bidId) {
		this.bidId = bidId;
	}
	public String getAskId() {
		return askId;
	}
	public void setAskId(final String askId) {
		this.askId = askId;
	}
	public String getBidOrAsk() {
		return bidOrAsk;
	}
	public void setBidOrAsk(final String bidOrAsk) {
		this.bidOrAsk = bidOrAsk;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(final String entryTime) {
		this.entryTime = entryTime;
	}
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(final String oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getOldVolume() {
		return oldVolume;
	}
	public void setOldVolume(final String oldVolume) {
		this.oldVolume = oldVolume;
	}
	public String getBuyerBrokerId() {
		return buyerBrokerId;
	}
	public void setBuyerBrokerId(final String buyerBrokerId) {
		this.buyerBrokerId = buyerBrokerId;
	}
	public String getSellerBrokerId() {
		return sellerBrokerId;
	}
	public void setSellerBrokerId(final String sellerBrokerId) {
		this.sellerBrokerId = sellerBrokerId;
	}
	
}
