package com.java.excelJsonUtility.dto;

import java.util.Objects;

public class GoodsLocationCode {

    private String en;

    public GoodsLocationCode() {
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public void setCyVal(String cy) {
        this.cy = cy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GoodsLocationCode that = (GoodsLocationCode) o;
        return Objects.equals(en, that.en) && Objects.equals(cy, that.cy) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(en, cy, code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCy() {
        return cy;
    }

    public String getCode() {
        return code;
    }

    private String cy;
    private String code;

    public GoodsLocationCode(String en, String cy, String code) {
        this.en = en;
        this.cy = cy;
        this.code = code;
    }

}
