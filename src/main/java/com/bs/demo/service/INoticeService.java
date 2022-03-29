package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface INoticeService extends IService<Notice> {

    Result addNotice(Notice notice);

    Result updateNotice(Notice notice);

    Result deleteNotice(List<Notice> noticeList);
}
