package com.java.excelJsonUtility.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParentChildDTO {

    private String parentCode;
    private List<String> childCodes=new ArrayList<>();

    public String getParentCode() {
        return parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setChildCodes(List<String> childCodes) {
        this.childCodes = childCodes;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParentChildDTO that = (ParentChildDTO) o;
        return Objects.equals(parentCode, that.parentCode) && Objects.equals(childCodes, that.childCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentCode, childCodes);
    }

    @Override
    public String toString() {
        return "ParentChildDTO{" +
                "parentCode='" + parentCode + '\'' +
                ", childCodes=" + childCodes +
                '}';
    }

    public List<String> getChildCodes() {
        return childCodes;
    }



    public ParentChildDTO(String parentCode, List<String> childCodes) {
        this.parentCode = parentCode;
        this.childCodes = childCodes;
    }

    public ParentChildDTO() {
    }



}
