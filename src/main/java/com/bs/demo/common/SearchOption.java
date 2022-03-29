package com.bs.demo.common;

import com.bs.demo.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "查找选项", description = "")
public class SearchOption implements Serializable {

    private Integer currentPage = 1;

    private Integer pageSize = 10;

    private String searchText;

    private User user;
}
