package com.sdm.phr;

import java.io.File;

public class KeyConfig {
    private static KeyConfig instance = new KeyConfig();
    private String patientReadMasterKeyPath = null;
    private String patientWriteMasterKeyPath = null;
    private String patientReadPublicKeyPath = null;
    private String patientWritePublicKeyPath = null;
    private String patientReadSecretKeyPath = null;
    private String patientWriteSecretKeyPath = null;
    private String userReadSecretKeyPath = null;
    private String userWriteSecretKeyPath = null;
    private int patientId;
    private int userId;
    
    private KeyConfig(){ 
        //setup default paths
        String keyPath = System.getProperty("user.home") + File.separator + ".phr";
        patientReadMasterKeyPath = keyPath + File.separator + "master_read.key";
        patientWriteMasterKeyPath = keyPath + File.separator + "master_write.key";
        patientReadPublicKeyPath = keyPath + File.separator + "public_read.key";
        patientWritePublicKeyPath = keyPath + File.separator + "public_write.key";
        patientWritePublicKeyPath = keyPath + File.separator + "public_write.key";
        patientReadSecretKeyPath = keyPath + File.separator + "secret_read.key";
        patientWriteSecretKeyPath = keyPath + File.separator + "secret_write.key";
    }
    public static KeyConfig getInstance(){
        return instance;
    }

    public String getPatientReadMasterKeyPath() {
        return patientReadMasterKeyPath;
    }

    public void setPatientReadMasterKeyPath(String patientMasterKeyPath) {
        this.patientReadMasterKeyPath = patientMasterKeyPath;
    }

    public String getPatientReadPublicKeyPath() {
        return patientReadPublicKeyPath;
    }

    public void setPatientReadPublicKeyPath(String patientPublicKeyPath) {
        this.patientReadPublicKeyPath = patientPublicKeyPath;
    }

    public String getUserReadSecretKeyPath() {
        return userReadSecretKeyPath;
    }

    public void setUserSecretReadKeyPath(String userSecretKeyPath) {
        this.userReadSecretKeyPath = userSecretKeyPath;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPatientWriteMasterKeyPath() {
        return patientWriteMasterKeyPath;
    }

    public void setPatientWriteMasterKeyPath(String patientWriteMasterKeyPath) {
        this.patientWriteMasterKeyPath = patientWriteMasterKeyPath;
    }

    public String getPatientWritePublicKeyPath() {
        return patientWritePublicKeyPath;
    }

    public void setPatientWritePublicKeyPath(String patientWritePublicKeyPath) {
        this.patientWritePublicKeyPath = patientWritePublicKeyPath;
    }

    public String getUserWriteSecretKeyPath() {
        return userWriteSecretKeyPath;
    }

    public void setUserWriteSecretKeyPath(String userWriteSecretKeyPath) {
        this.userWriteSecretKeyPath = userWriteSecretKeyPath;
    }

    public String getPatientReadSecretKeyPath() {
        return patientReadSecretKeyPath;
    }

    public void setPatientReadSecretKeyPath(String patientReadSecretKeyPath) {
        this.patientReadSecretKeyPath = patientReadSecretKeyPath;
    }

    public String getPatientWriteSecretKeyPath() {
        return patientWriteSecretKeyPath;
    }

    public void setPatientWriteSecretKeyPath(String patientWriteSecretKeyPath) {
        this.patientWriteSecretKeyPath = patientWriteSecretKeyPath;
    }
    
    
}
