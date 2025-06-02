package com.java.excelJsonUtility.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocCode {

    private String parentCode;
    private List<String> childCodes = new ArrayList<>();

    public String getParentCode() {
        return parentCode;
    }

    public DocCode(String parentCode, List<String> childCodes) {
        this.parentCode = parentCode;
        this.childCodes = childCodes;
    }

    public DocCode() {
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setChildCodes(List<String> childCodes) {
        this.childCodes = childCodes;
    }

    public List<String> getChildCodes() {
        return childCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DocCode docCode = (DocCode) o;
        return Objects.equals(parentCode, docCode.parentCode) && Objects.equals(childCodes, docCode.childCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentCode, childCodes);
    }

    @Override
    public String toString() {
        return "DocCode{" +
                "parentCode='" + parentCode + '\'' +
                ", childCodes=" + childCodes +
                '}';
    }
}
