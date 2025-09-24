package org.vit.unsafeutils.api;

import org.vit.unsafeutils.serializer.UnsafeBuffer;

/*
Copyright 2014 Vitaly Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
public class MarketData implements UnsafeSerializable {

    protected long lastTickTime = System.currentTimeMillis();
    protected long lastPublishTime = 0;

    protected boolean hideLast = true;

    protected String alias;

    boolean etf = false;
    protected String ric;
    protected String ticker;
    protected String alterRic;
    protected double last;
    protected double bid;
    protected double ask;
    protected double open;
    protected double close;
    protected double close2 = Double.NaN;
    protected double close5 = Double.NaN;
    protected double close21 = Double.NaN;
    protected double close20 = Double.NaN;

    protected double close6m = Double.NaN;
    protected double close1y = Double.NaN;
    // close px for first day of new calendar year
    protected double closeYtd = Double.NaN;
    protected long compositeVolume1W = 0;

    protected long volume;
    protected long compositeVolume;
    protected double avgVolume20 = 0;
    protected double avgVolume1 = Double.NaN;
    protected double avgVolume2 = Double.NaN;
    protected double avgVolume5 = Double.NaN;
    protected double avgVolume21 = Double.NaN;
    protected double avgVolume60 = Double.NaN;
    protected double avgCompVolume20 = 0;
    protected double avgCompVolume1 = Double.NaN;
    protected double avgCompVolume2 = Double.NaN;
    protected double avgCompVolume5 = Double.NaN;
    protected double avgCompVolume14 = Double.NaN;
    protected double avgCompVolume21 = Double.NaN;
    protected double avgCompVolume60 = Double.NaN;

    protected double avgVolumeTillNow20 = Double.NaN;
    protected double avgCompVolumeTillNow20 = Double.NaN;

    protected double avgVwap20;
    protected double avgSpread20;

    protected double avgClose50d = Double.NaN;
    protected double avgClose100d = Double.NaN;
    protected double avgClose200d = Double.NaN;

    protected double low;
    protected double low26w;
    protected double low52w;
    protected double high;
    protected double high26w;
    protected double high52w;
    protected double vwap;
    protected long numTrades;
    protected double turnover;

    protected double maxVolumeLastMinute = Double.NaN;
    protected double avgMaxVolumeLastMinute20 = Double.NaN;

    protected double vwap5 = Double.NaN;

    protected String source;
    protected String currency;

    public MarketData() {
    }

    MarketData(long lastTickTime, long lastPublishTime, boolean hideLast, boolean etf, String ric, String alterRic, double last, double bid, double ask, double open, double close, double close2, double close5, double close21, double close20, double close6m, double close1y, double closeYtd, long compositeVolume1W, long volume, long compositeVolume, double avgVolume20, double avgVolume1, double avgVolume2, double avgVolume5, double avgVolume21, double avgVolume60, double avgCompVolume20, double avgCompVolume1, double avgCompVolume2, double avgCompVolume5, double avgCompVolume14, double avgCompVolume21, double avgCompVolume60, double avgVolumeTillNow20, double avgCompVolumeTillNow20, double avgVwap20, double avgSpread20, double avgClose50d, double avgClose100d, double avgClose200d, double low, double low26w, double low52w, double high, double high26w, double high52w, double vwap, long numTrades, double turnover, double maxVolumeLastMinute, double avgMaxVolumeLastMinute20, double vwap5) {
        this.lastTickTime=lastTickTime;
        this.lastPublishTime=lastPublishTime;
        this.hideLast=hideLast;
        this.etf=etf;
        this.ric=ric;
        this.alterRic=alterRic;
        this.last=last;
        this.bid=bid;
        this.ask=ask;
        this.open=open;
        this.close=close;
        this.close2=close2;
        this.close5=close5;
        this.close21=close21;
        this.close20=close20;
        this.close6m=close6m;
        this.close1y=close1y;
        this.closeYtd=closeYtd;
        this.compositeVolume1W=compositeVolume1W;
        this.volume=volume;
        this.compositeVolume=compositeVolume;
        this.avgVolume20=avgVolume20;
        this.avgVolume1=avgVolume1;
        this.avgVolume2=avgVolume2;
        this.avgVolume5=avgVolume5;
        this.avgVolume21=avgVolume21;
        this.avgVolume60=avgVolume60;
        this.avgCompVolume20=avgCompVolume20;
        this.avgCompVolume1=avgCompVolume1;
        this.avgCompVolume2=avgCompVolume2;
        this.avgCompVolume5=avgCompVolume5;
        this.avgCompVolume14=avgCompVolume14;
        this.avgCompVolume21=avgCompVolume21;
        this.avgCompVolume60=avgCompVolume60;
        this.avgVolumeTillNow20=avgVolumeTillNow20;
        this.avgCompVolumeTillNow20=avgCompVolumeTillNow20;
        this.avgVwap20=avgVwap20;
        this.avgSpread20=avgSpread20;
        this.avgClose50d=avgClose50d;
        this.avgClose100d=avgClose100d;
        this.avgClose200d=avgClose200d;
        this.low=low;
        this.low26w=low26w;
        this.low52w=low52w;
        this.high=high;
        this.high26w=high26w;
        this.high52w=high52w;
        this.vwap=vwap;
        this.numTrades=numTrades;
        this.turnover=turnover;
        this.maxVolumeLastMinute=maxVolumeLastMinute;
        this.avgMaxVolumeLastMinute20=avgMaxVolumeLastMinute20;
        this.vwap5=vwap5;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isHideLast() {
        return hideLast;
    }

    public void setHideLast(boolean hideLast) {
        this.hideLast = hideLast;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getCompositeVolume() {
        return compositeVolume;
    }

    public void setCompositeVolume(long compositeVolume) {
        this.compositeVolume = compositeVolume;
    }

    public double getAvgCompVolume20() {
        return coalesceZero(avgCompVolume20, avgVolume20);
    }

    public void setAvgCompVolume20(double avgCompVolume20) {
        this.avgCompVolume20 = avgCompVolume20;
    }

    public double getAvgCompVolume1() {
        return avgCompVolume1;
    }

    public void setAvgCompVolume1(double avgCompVolume1) {
        this.avgCompVolume1 = avgCompVolume1;
    }

    public double getAvgCompVolume2() {
        return avgCompVolume2;
    }

    public void setAvgCompVolume2(double avgCompVolume2) {
        this.avgCompVolume2 = avgCompVolume2;
    }

    public double getAvgCompVolume5() {
        return avgCompVolume5;
    }

    public void setAvgCompVolume5(double avgCompVolume5) {
        this.avgCompVolume5 = avgCompVolume5;
    }

    public double getAvgCompVolume14() {
        return avgCompVolume14;
    }

    public void setAvgCompVolume14(double avgCompVolume14) {
        this.avgCompVolume14 = avgCompVolume14;
    }

    public double getAvgCompVolume21() {
        return avgCompVolume21;
    }

    public void setAvgCompVolume21(double avgCompVolume21) {
        this.avgCompVolume21 = avgCompVolume21;
    }

    public double getAvgVolume60() {
        return coalesceZero(avgVolume60, avgCompVolume60);
    }

    public void setAvgVolume60(double avgVolume60) {
        this.avgVolume60 = avgVolume60;
    }

    public double getAvgCompVolume60() {
        return coalesceZero(avgCompVolume60, avgVolume60);
    }

    public void setAvgCompVolume60(double avgCompVolume60) {
        this.avgCompVolume60 = avgCompVolume60;
    }

    public double getAvgVolumeTillNow20() {
        return coalesceZero(avgVolumeTillNow20, avgCompVolumeTillNow20);
    }

    public void setAvgVolumeTillNow20(double avgVolumeTillNow20) {
        this.avgVolumeTillNow20 = avgVolumeTillNow20;
    }

    public double getAvgCompVolumeTillNow20() {
        return coalesceZero(avgCompVolumeTillNow20, avgVolumeTillNow20);
    }

    public void setAvgCompVolumeTillNow20(double avgCompVolumeTillNow20) {
        this.avgCompVolumeTillNow20 = avgCompVolumeTillNow20;
    }

    public String getAlias() {
        return alias;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getPrice(int sideType) {
/*        switch(sideType){
            case TradingData.BUY:
                return getAskPrc();
            case TradingData.SELL:
                return getBidPrc();
            default:
                return getLast();
        }*/
        return getLast();
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getBidPrc() {
        return (bid == 0 ? getLast() : bid);
    }

    public void setBidPrc(double bid) {
        this.bid = bid;
    }

    public double getAskPrc() {
        return (ask == 0 ? getLast() : ask);
    }

    public void setAskPrc(double ask) {
        this.ask = ask;
    }

    public float completeness() {
        //TODO will need to check if we can get rid of it
        return 1;
    }

    public String getAlterRic() {
        return alterRic;
    }

    public void setAlterRic(String alterRic) {
        this.alterRic = alterRic;
    }

    public MarketData(String ric) {
        this.ric = ric;
    }

    public void copy(MarketData m) {
        this.setAvgVolume(m.getAvgVolume());
        this.setClose(m.getClose());
        this.setClose2(m.getClose2());
        this.setLast(m.getLast());
        this.setRic(m.getRic());
        this.setSource(m.getSource());
        this.setVolume(m.getVolume());
    }

    public void updateTickTime() {
        lastTickTime = System.currentTimeMillis();
    }

    public long getLastTickTime() {
        return lastTickTime;
    }

    public void setLastTickTime(long lastTickTime) {
        this.lastTickTime = lastTickTime;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public double getLast(boolean ignoreVwap) {
        if (!ignoreVwap) return getVwap();
        if (isNanOrZero(last)) last = getClose(true);
        return last;
    }

    public double getLast() {
        return getLast(!isHideLast());
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getClose() {
        return close;
    }

    public double getClose(boolean flag) {
        if (flag && isNanOrZero(close))
            return getClose2();
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getClose2() {
        return close2;
    }

    public void setClose2(double close2) {
        this.close2 = close2;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getAvgVolume() {
        return coalesceZero(avgVolume20, getAvgCompVolume20());
    }

    public double getAvgCompVolume() {
        return coalesceZero(avgCompVolume20, avgVolume20);
    }

    public double getExchangeParticipationKoef(){
        if (isNanOrZero(avgVolume20) || isNanOrZero(avgCompVolume20)) return 1;
        return avgVolume20 / avgCompVolume20;
    }

    public void setAvgVolume(double avgVolume) {
        this.avgVolume20 = avgVolume;
    }

    public double getAvgVolume20() {
        return getAvgVolume();
    }

    public void setAvgVolume20(double avgVolume20) {
        if (!isNanOrZero(avgVolume20)) this.avgVolume20 = avgVolume20;
    }

    public double getAvgVolume1() {
        return coalesceZero(avgVolume1, getAvgCompVolume1());
    }

    public void setAvgVolume1(double avgVolume1) {
        this.avgVolume1 = avgVolume1;
    }

    public double getAvgVolume2() {
        return coalesceZero(avgVolume2, getAvgCompVolume2());
    }

    public void setAvgVolume2(double avgVolume2) {
        this.avgVolume2 = avgVolume2;
    }

    public double getAvgVolume5() {
        return coalesceZero(avgVolume5, getAvgCompVolume5());
    }

    public void setAvgVolume5(double avgVolume5) {
        this.avgVolume5 = avgVolume5;
    }

    public double getAvgVolume21() {
        return coalesceZero(avgVolume21, getAvgCompVolume21());
    }

    public void setAvgVolume21(double avgVolume21) {
        this.avgVolume21 = avgVolume21;
    }

    public double getAvgClose50d() {
        return avgClose50d;
    }

    public void setAvgClose50d(double avgClose50d) {
        this.avgClose50d = avgClose50d;
    }

    public double getAvgClose100d() {
        return avgClose100d;
    }

    public void setAvgClose100d(double avgClose100d) {
        this.avgClose100d = avgClose100d;
    }

    public double getAvgClose200d() {
        return avgClose200d;
    }

    public void setAvgClose200d(double avgClose200d) {
        this.avgClose200d = avgClose200d;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isEtf() {
        return etf;
    }

    public void setEtf(boolean etf) {
        this.etf = etf;
    }

    public double getChgPrc() {
        if (close != 0 && last != 0) return ((last - close) / close * 100);
        if (close != 0 && close2 != 0) return ((close - close2) / close2 * 100);
        return 0;
    }

    public double getClose5() {
        return close5;
    }

    public void setClose5(double close5) {
        this.close5 = close5;
    }

    public double getClose21() {
        return close21;
    }

    public void setClose21(double close21) {
        this.close21 = close21;
    }

    public double getClose20() {
        return close20;
    }

    public void setClose20(double close20) {
        this.close20 = close20;
    }

    public double getClose6m() {
        return close6m;
    }

    public void setClose6m(double close6m) {
        this.close6m = close6m;
    }

    public double getClose1y() {
        return close1y;
    }

    public void setClose1y(double close1y) {
        this.close1y = close1y;
    }

    public double getCloseYtd() {
        return closeYtd;
    }

    public void setCloseYtd(double closeYtd) {
        this.closeYtd = closeYtd;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getLow26w() {
        return low26w;
    }

    public void setLow26w(double low26w) {
        this.low26w = low26w;
    }

    public double getLow52w() {
        return low52w;
    }

    public void setLow52w(double low52w) {
        this.low52w = low52w;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getHigh26w() {
        return high26w;
    }

    public void setHigh26w(double high26w) {
        this.high26w = high26w;
    }

    public double getHigh52w() {
        return high52w;
    }

    public void setHigh52w(double high52w) {
        this.high52w = high52w;
    }

    public double getVwap() {
        return coalesceZero(vwap, getClose());
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public long getNumTrades() {
        return numTrades;
    }

    public void setNumTrades(long numTrades) {
        this.numTrades = numTrades;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    @Override
    public String toString() {
        return "org.vit.unsafeutils.api.MarketData{" +
                "lastTickTime=" + lastTickTime +
                ", lastPublishTime=" + lastPublishTime +
                ", hideLast=" + hideLast +
                ", alias='" + alias + '\'' +
                ", etf=" + etf +
                ", ric='" + ric + '\'' +
                ", ticker='" + ticker + '\'' +
                ", alterRic='" + alterRic + '\'' +
                ", last=" + last +
                ", bid=" + bid +
                ", ask=" + ask +
                ", open=" + open +
                ", close=" + close +
                ", close2=" + close2 +
                ", close5=" + close5 +
                ", close21=" + close21 +
                ", close20=" + close20 +
                ", close6m=" + close6m +
                ", close1y=" + close1y +
                ", closeYtd=" + closeYtd +
                ", compositeVolume1W=" + compositeVolume1W +
                ", volume=" + volume +
                ", compositeVolume=" + compositeVolume +
                ", avgVolume20=" + avgVolume20 +
                ", avgVolume1=" + avgVolume1 +
                ", avgVolume2=" + avgVolume2 +
                ", avgVolume5=" + avgVolume5 +
                ", avgVolume21=" + avgVolume21 +
                ", avgVolume60=" + avgVolume60 +
                ", avgCompVolume20=" + avgCompVolume20 +
                ", avgCompVolume1=" + avgCompVolume1 +
                ", avgCompVolume2=" + avgCompVolume2 +
                ", avgCompVolume5=" + avgCompVolume5 +
                ", avgCompVolume14=" + avgCompVolume14 +
                ", avgCompVolume21=" + avgCompVolume21 +
                ", avgCompVolume60=" + avgCompVolume60 +
                ", avgVolumeTillNow20=" + avgVolumeTillNow20 +
                ", avgCompVolumeTillNow20=" + avgCompVolumeTillNow20 +
                ", avgVwap20=" + avgVwap20 +
                ", avgSpread20=" + avgSpread20 +
                ", avgClose50d=" + avgClose50d +
                ", avgClose100d=" + avgClose100d +
                ", avgClose200d=" + avgClose200d +
                ", low=" + low +
                ", low26w=" + low26w +
                ", low52w=" + low52w +
                ", high=" + high +
                ", high26w=" + high26w +
                ", high52w=" + high52w +
                ", vwap=" + vwap +
                ", numTrades=" + numTrades +
                ", turnover=" + turnover +
                ", maxVolumeLastMinute=" + maxVolumeLastMinute +
                ", avgMaxVolumeLastMinute20=" + avgMaxVolumeLastMinute20 +
                ", vwap5=" + vwap5 +
                ", source='" + source + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public void updateLastPublishTime() {
        lastPublishTime = System.currentTimeMillis();
    }

    public long getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(long lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getAvgVwap20() {
        return avgVwap20;
    }

    public void setAvgVwap20(double avgVwap20) {
        this.avgVwap20 = avgVwap20;
    }

    public double getAvgSpread20() {
        return avgSpread20;
    }

    public void setAvgSpread20(double avgSpread20) {
        this.avgSpread20 = avgSpread20;
    }

    public static boolean hasAvgVolume20(MarketData md) {
        return md != null && !isNanOrZero(md.getAvgVolume20());
    }

    private static boolean isNanOrZero(Double value) {
        return value == 0 || Double.isNaN(value);
    }

    protected double coalesceZero(Double val1, Double val2) {
        if (val1 != 0 && !Double.isNaN(val1)) return val1;
        return val2;
    }

    protected double getZeroIfNaN(Double val) {
        return Double.isNaN(val) ? 0 : val;
    }

    public long getCompositeVolume1W() {
        return compositeVolume1W;
    }

    public void setCompositeVolume1W(long compositeVolume1W) {
        this.compositeVolume1W = compositeVolume1W;
    }

    public double getMaxVolumeLastMinute() {
        return maxVolumeLastMinute;
    }

    public void setMaxVolumeLastMinute(double maxVolumeLastMinute) {
        this.maxVolumeLastMinute = maxVolumeLastMinute;
    }

    public double getAvgMaxVolumeLastMinute20() {
        return avgMaxVolumeLastMinute20;
    }

    public void setAvgMaxVolumeLastMinute20(double avgMaxVolumeLastMinute20) {
        this.avgMaxVolumeLastMinute20 = avgMaxVolumeLastMinute20;
    }

    public double getVwap5() {
        return vwap5;
    }

    public void setVwap5(double vwap5) {
        this.vwap5 = vwap5;
    }

    @Override
    public int getDefaultSize() {
        return 1024;
    }

    @Override
    public String getObjectId() {
        return ric;
    }

    @Override
    public void write(UnsafeBuffer buffer) {
        buffer.putInt(getClass().getName().hashCode());
        buffer.putLong(lastTickTime);
        buffer.putLong(lastPublishTime);
        buffer.putBoolean(hideLast);
        //buffer.putString(alias);
        buffer.putBoolean(etf);
        buffer.putString(ric);
        //buffer.putString(ticker);
        buffer.putString(alterRic);
        buffer.putDouble(last);
        buffer.putDouble(bid);
        buffer.putDouble(ask);
        buffer.putDouble(open);
        buffer.putDouble(close);
        buffer.putDouble(close2);
        buffer.putDouble(close5);
        buffer.putDouble(close21);
        buffer.putDouble(close20);
        buffer.putDouble(close6m);
        buffer.putDouble(close1y);
        buffer.putDouble(closeYtd);
        buffer.putLong(compositeVolume1W);
        buffer.putLong(volume);
        buffer.putLong(compositeVolume);
        buffer.putDouble(avgVolume20);
        buffer.putDouble(avgVolume1);
        buffer.putDouble(avgVolume2);
        buffer.putDouble(avgVolume5);
        buffer.putDouble(avgVolume21);
        buffer.putDouble(avgVolume60);
        buffer.putDouble(avgCompVolume20);
        buffer.putDouble(avgCompVolume1);
        buffer.putDouble(avgCompVolume2);
        buffer.putDouble(avgCompVolume5);
        buffer.putDouble(avgCompVolume14);
        buffer.putDouble(avgCompVolume21);
        buffer.putDouble(avgCompVolume60);
        buffer.putDouble(avgVolumeTillNow20);
        buffer.putDouble(avgCompVolumeTillNow20);
        buffer.putDouble(avgVwap20);
        buffer.putDouble(avgSpread20);
        buffer.putDouble(avgClose50d);
        buffer.putDouble(avgClose100d);
        buffer.putDouble(avgClose200d);
        buffer.putDouble(low);
        buffer.putDouble(low26w);
        buffer.putDouble(low52w);
        buffer.putDouble(high);
        buffer.putDouble(high26w);
        buffer.putDouble(high52w);
        buffer.putDouble(vwap);
        buffer.putLong(numTrades);
        buffer.putDouble(turnover);
        buffer.putDouble(maxVolumeLastMinute);
        buffer.putDouble(avgMaxVolumeLastMinute20);
        buffer.putDouble(vwap5);
    }

    @Override
    public UnsafeSerializable read(UnsafeBuffer in) {
        long lastTickTime             = in.getLong();
        long lastPublishTime          = in.getLong();
        boolean hideLast                 = in.getBoolean();
        //String alias                    = in.getString();
        boolean etf                      = in.getBoolean();
        String ric                      = in.getString();
        //String ticker                   = in.getString();
        String alterRic                 = in.getString();
        double last                     = in.getDouble();
        double bid                      = in.getDouble();
        double ask                      = in.getDouble();
        double open                     = in.getDouble();
        double close                    = in.getDouble();
        double close2                   = in.getDouble();
        double close5                   = in.getDouble();
        double close21                  = in.getDouble();
        double close20                  = in.getDouble();
        double close6m                  = in.getDouble();
        double close1y                  = in.getDouble();
        double closeYtd                 = in.getDouble();
        long compositeVolume1W        = in.getLong();
        long volume                   = in.getLong();
        long compositeVolume          = in.getLong();
        double avgVolume20              = in.getDouble();
        double avgVolume1               = in.getDouble();
        double avgVolume2               = in.getDouble();
        double avgVolume5               = in.getDouble();
        double avgVolume21              = in.getDouble();
        double avgVolume60              = in.getDouble();
        double avgCompVolume20          = in.getDouble();
        double avgCompVolume1           = in.getDouble();
        double avgCompVolume2           = in.getDouble();
        double avgCompVolume5           = in.getDouble();
        double avgCompVolume14          = in.getDouble();
        double avgCompVolume21          = in.getDouble();
        double avgCompVolume60          = in.getDouble();
        double avgVolumeTillNow20       = in.getDouble();
        double avgCompVolumeTillNow20   = in.getDouble();
        double avgVwap20                = in.getDouble();
        double avgSpread20              = in.getDouble();
        double avgClose50d              = in.getDouble();
        double avgClose100d             = in.getDouble();
        double avgClose200d             = in.getDouble();
        double low                      = in.getDouble();
        double low26w                   = in.getDouble();
        double low52w                   = in.getDouble();
        double high                     = in.getDouble();
        double high26w                  = in.getDouble();
        double high52w                  = in.getDouble();
        double vwap                     = in.getDouble();
        long numTrades                  = in.getLong();
        double turnover                 = in.getDouble();
        double maxVolumeLastMinute      = in.getDouble();
        double avgMaxVolumeLastMinute20 = in.getDouble();
        double vwap5                    = in.getDouble();
        byte[] tmp                      = in.getByteArray();
        return new MarketData(lastTickTime, lastPublishTime, hideLast, etf, ric, alterRic, last, bid, ask, open, close, close2, close5, close21, close20, close6m, close1y, closeYtd, compositeVolume1W, volume, compositeVolume, avgVolume20, avgVolume1, avgVolume2, avgVolume5, avgVolume21, avgVolume60, avgCompVolume20, avgCompVolume1, avgCompVolume2, avgCompVolume5, avgCompVolume14, avgCompVolume21, avgCompVolume60, avgVolumeTillNow20, avgCompVolumeTillNow20, avgVwap20, avgSpread20, avgClose50d, avgClose100d, avgClose200d, low, low26w, low52w, high, high26w, high52w, vwap, numTrades, turnover, maxVolumeLastMinute, avgMaxVolumeLastMinute20, vwap5);
    }
}
