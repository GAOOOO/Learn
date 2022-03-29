package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.entity.Notice;
import com.bs.demo.mapper.NoticeMapper;
import com.bs.demo.service.INoticeService;
import com.bs.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author gf
 * @since 2021-12-28
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public Result addNotice(Notice notice) {
        Notice newNotice = new Notice();
        newNotice.setNoticeTitle(notice.getNoticeTitle());
        newNotice.setNoticeContent(notice.getNoticeContent());
        newNotice.setCreateUser(SecurityUtils.getCurrentUsername());
        newNotice.setNoticeImg(notice.getNoticeImg());
        newNotice.setCreateTime(LocalDateTime.now());
        noticeMapper.insert(newNotice);
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateNotice(Notice notice) {
        Notice orNotice = noticeMapper.selectById(notice);
        if (orNotice == null){
            return Result.error().message("修改公告错误：【不存在该公告】").data(notice);
        }else {
            orNotice.setNoticeTitle(notice.getNoticeTitle());
            orNotice.setNoticeContent(notice.getNoticeContent());
            orNotice.setNoticeImg(notice.getNoticeImg());
            orNotice.setUpdateUser(SecurityUtils.getCurrentUsername());
            noticeMapper.updateById(orNotice);
            return Result.success().message("修改成功").data(orNotice);
        }

    }

    @Override
    public Result deleteNotice(List<Notice> noticeList) {
        List<Integer> ids = noticeList.stream().map(Notice::getNoticeId).collect(Collectors.toList());
        noticeMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}