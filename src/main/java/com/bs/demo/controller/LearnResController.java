package com.bs.demo.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.entity.User;
import com.bs.demo.service.ILearnResService;
import com.bs.demo.entity.LearnRes;


import com.bs.demo.service.IUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.bs.demo.annotation.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author gf
 * @since 2022-03-10
 */
@Controller
@RestController
@RequestMapping("/learnRes")
@Api(tags = "")
public class LearnResController {

    @Autowired
    public ILearnResService iLearnResService;


    @Autowired
    public IUserService iUserService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping("/upload_file")
    public Result uploadFile(MultipartFile file, @RequestParam String userId) throws IOException, SQLException {

        InputStream inputStream = file.getInputStream();

        OutputStream os = null;
        try {
            String path = "src/main/res";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024 * 10];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            String filePath = tempFile.getPath() + File.separator + file.getOriginalFilename();
            os = new FileOutputStream(filePath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            LearnRes learnRes = new LearnRes();
            learnRes.setResAddress(filePath);
            learnRes.setUserId(userId);
            learnRes.setResTitle(file.getOriginalFilename());
            learnRes.setResDownload(0);

            iLearnResService.save(learnRes);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error().message("上传失败!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Result.success().message("上传成功!");
    }


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('res:list')")
    public Result learnResList(@RequestBody SearchOption searchOption) {
        List<LearnRes> list = iLearnResService.list();

        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(list);
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('res:del')")
    @Transactional
    public Result deleteLearnRes(@RequestBody JSONObject jsonObject) {
        String resId = jsonObject.getStr("resId");

        iLearnResService.remove(new QueryWrapper<LearnRes>()
                .eq("res_id",resId));
        return Result.success().data("删除成功！");
    }
}
