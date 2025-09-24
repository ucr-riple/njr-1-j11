package com.sdm.phr;

public class Session {
    private static Session instance = new Session();
    
    private  String patientName;
    private int patientId;
    private String userName;
    private int userId;
    private int patientUserId;
    private String orgnName;
    private int orgnId;
    private String writeToken;
    
    private Session(){      
    }
    
    public static Session getInstance(){
        return instance;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrgnName() {
        return orgnName;
    }

    public void setOrgnName(String orgnName) {
        this.orgnName = orgnName;
    }

    public int getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(int orgnId) {
        this.orgnId = orgnId;
    }

    public int getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(int patientUserId) {
        this.patientUserId = patientUserId;
    }
    
    
    public void clean(){
        this.orgnId = -1;
        this.orgnName = null;
        this.patientId = -1;
        this.patientName = null;
        this.userId = -1;
        this.userName = null;
        this.patientUserId=-1;
        this.writeToken = null;
    }
    
}
