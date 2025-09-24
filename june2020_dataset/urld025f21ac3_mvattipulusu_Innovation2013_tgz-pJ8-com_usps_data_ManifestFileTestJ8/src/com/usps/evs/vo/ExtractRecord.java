/*
 * ExtractRecord.java
 *
 * Author: Nat Meo
 *
 * Extract record.
 */

package com.usps.evs.vo;
import java.util.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.*;
import java.util.*;

public class ExtractRecord
{
    private String picCode;
    private String fileNumber;
    private String mailerId;
    private String mailerName;
    private String destinationZip;
    private String destinationZipPlus4;
    private String eventZip;
    private String scanningFacilityName;
    private String eventCode;
    private String eventName;
    private Calendar eventDateTime;
    private String clientDuns;
    private String corpDuns;
    private double weight;
    private String weightIndicator;
    private String packageDuns;
    private String dunsPkgidDzip;
    private String checkDigit;
    private String packageId;
    private String serviceTypeCode;
    private Calendar fileDate;
    private int fileSequenceNumber;
    private String fiscalMonth;
    private String fiscalYear;
    private int sequenceNumber;

    /** Creates a new instance of ExtractRecord */
    public ExtractRecord() {
    }

    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getMailerId() {
        return mailerId;
    }

    public void setMailerId(String mailerId) {
        this.mailerId = mailerId;
    }

    public String getMailerName() {
        return mailerName;
    }

    public void setMailerName(String mailerName) {
        this.mailerName = mailerName;
    }

    public String getDestinationZip() {
        return destinationZip;
    }

    public void setDestinationZip(String destinationZip) {
        this.destinationZip = destinationZip;
    }

    public String getDestinationZipPlus4() {
        return destinationZipPlus4;
    }

    public void setDestinationZipPlus4(String destinationZipPlus4) {
        this.destinationZipPlus4 = destinationZipPlus4;
    }

    public String getEventZip() {
        return eventZip;
    }

    public void setEventZip(String eventZip) {
        this.eventZip = eventZip;
    }

    public String getScanningFacilityName() {
        return scanningFacilityName;
    }

    public void setScanningFacilityName(String scanningFacilityName) {
        this.scanningFacilityName = scanningFacilityName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Calendar getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Calendar eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getClientDuns() {
        return clientDuns;
    }

    public void setClientDuns(String clientDuns) {
        this.clientDuns = clientDuns;
    }

    public String getCorpDuns() {
        return corpDuns;
    }

    public void setCorpDuns(String corpDuns) {
        this.corpDuns = corpDuns;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightIndicator() {
        return weightIndicator;
    }

    public void setWeightIndicator(String weightIndicator) {
        this.weightIndicator = weightIndicator;
    }

    public String getDunsPkgidDzip() {
        return dunsPkgidDzip;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }

    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public String getPackageDuns() {
        return packageDuns;
    }

    public void setPackageDuns(String packageDuns) {
        this.packageDuns = packageDuns;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Calendar getFileDate() {
        return fileDate;
    }

    public void setDunsPkgidDzip(String dunsPkgidDzip)
    {
        this.dunsPkgidDzip = dunsPkgidDzip;
    }
    
    public void setFileDate(Calendar fileDate) {
        this.fileDate = fileDate;
    }

    public int getFileSequenceNumber() {
        return fileSequenceNumber;
    }

    public void setFileSequenceNumber(int fileSequenceNumber) {
        this.fileSequenceNumber = fileSequenceNumber;
    }

    public String getFiscalMonth() {
        return fiscalMonth;
    }

    public void setFiscalMonth(String fiscalMonth) {
        this.fiscalMonth = fiscalMonth;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}
