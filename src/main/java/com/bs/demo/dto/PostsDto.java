package com.bs.demo.dto;

import com.bs.demo.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostsDto implements Serializable {

    private Integer postsId;

    private String userId;

    private String postsTitle;

    private String postsInfo;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Object items;

}
