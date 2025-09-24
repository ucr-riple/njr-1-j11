/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.api;

/**
 * Point of Sale Entry Strategy
 *
 * @author wsuetholz
 * @version 1.00
 */
public interface PosEntryStrategy {

    public abstract void processEntry(StoreStrategy store, SaleStrategy sale, SaleOutputStrategy receiptOutput);
}
