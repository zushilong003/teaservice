package com.lingtian.pmrApi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;


@ToString
public class PageRequestDto implements Serializable {
    @JsonProperty("pageSize")
    private int pageSize; // 每页的条数

    @JsonProperty("pageNumber")
    private int pageNumber; // 当前页

    public int getPageSize() {
        if (pageSize <= 0) {
            setPageSize(20);
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        if (pageNumber <= 0) {
            setPageNumber(1);
        }
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
