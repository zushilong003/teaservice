package com.lingtian.pmrApi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class ResultPageListInfo<T> implements Serializable {

    @JsonProperty("count")
    private long count; // 总数

    @JsonProperty("list")
    private List<T> list = new LinkedList<>();

    @JsonIgnore
    private int pageNumber;

    public ResultPageListInfo(List<T> info) {
        PageInfo<T> pageInfo = new PageInfo<>(info);
        count = pageInfo.getTotal();
        list = pageInfo.getList();
    }

    public ResultPageListInfo() {
    }
}
