package com.bs.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RankingDto implements Serializable {


    private Integer id;

    private String title;

    private Integer num;
}
