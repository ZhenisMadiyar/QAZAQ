package com.madiyar.ikazak.kazak.traditions;

/**
 * Created by Madiyar on 02.02.2015.
 */
public class TraditionList {
    String traditionName;
    String traditionContent;

    public TraditionList(String traditionName, String traditionContent) {
        this.traditionName = traditionName;
        this.traditionContent = traditionContent;
    }

    public String getTraditionName() {
        return traditionName;
    }

    public void setTraditionName(String traditionName) {
        this.traditionName = traditionName;
    }

    public String getTraditionContent() {
        return traditionContent;
    }

    public void setTraditionContent(String traditionContent) {
        this.traditionContent = traditionContent;
    }
}
