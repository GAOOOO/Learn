package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.demo.common.Common;
import com.bs.demo.common.Result;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.User;
import com.bs.demo.entity.vo.PasswordVo;
import com.bs.demo.service.ITeacherService;
import com.bs.demo.service.IUserService;
import com.bs.demo.utils.RedisUtils;
import com.bs.demo.utils.SecurityUtils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@Api(tags = {"公用请求"})
@Slf4j
public class CommonController {


    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IUserService iUserService;


    @Autowired
    private ITeacherService iTeacherService;

//    todo：平台首页功能接口

    /**
     * 获取平台首页的优秀教师模块数据
     * @return
     */
    @GetMapping("/teacher")
    public Result getTeacher(){
        return Result.success().data(iTeacherService.list());
    }


    @GetMapping("/captcha")
    @ApiOperation(value = "验证码")
    public Result captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        String verCode = captcha.text().toLowerCase();
        String key ="captcha:"+ UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(key, verCode, 30, TimeUnit.MINUTES);
        Map<String,String> map=new HashMap<>();
        map.put("key", key);
        map.put("image", captcha.toBase64());
        return Result.success().code(200).message("请求验证码成功").data(map);
    }



    @PostMapping("/personalEdit")
    @ApiOperation(value = "个人基础信息修改")
    public Result personalEdit(@RequestBody User user) throws Exception {
        Result result = iUserService.personalEdit(user);
        return result;
    }



    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码")
    public Result updatePassword(@RequestBody PasswordVo passwordVo) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User currentUser = SecurityUtils.getCurrentUser().getUser();
        if (!bCryptPasswordEncoder.matches(passwordVo.getOldPass(),currentUser.getPassword())){
            return Result.error().message("修改失败，旧密码错误");
        }
        if(bCryptPasswordEncoder.matches(passwordVo.getNewPass(), currentUser.getPassword())){
            return Result.error().message("新密码不能与旧密码相同");
        }
        currentUser.setPassword(bCryptPasswordEncoder.encode(passwordVo.getNewPass()));
        iUserService.updateById(currentUser);
        return Result.success().message("修改成功");
    }


    @PostMapping(value = "/uploadAvatar")
    @ApiOperation(value = "更改头像")
    public Result changePhoto(@RequestBody MultipartFile file) throws IOException {
        // 用户信息回填

        InputStream inputStream = file.getInputStream();

        OutputStream os = null;
        try {
            String path = "src/main/avatar";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            User user = iUserService.getById(SecurityUtils.getCurrentUserId());
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID() + suffix;
            user.setAvatar(filename);

            File files = new File(tempFile.getPath() + File.separator + filename);
            files.delete();

            os = new FileOutputStream(tempFile.getPath() + File.separator + filename);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            iUserService.updateById(user);
            SecurityUtils.getCurrentUser().getUser().setAvatar(filename);
            return Result.success().message("修改成功").data(filename);

        } catch (IOException e) {
            e.printStackTrace();
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
        return Result.success().message("修改失败");
    }


    @GetMapping(value = "/getAvatar")
    @ApiOperation(value = "获取头像")
    public void getImage(String userId, HttpServletResponse response) throws IOException {
        OutputStream os = null;

        String imagePath = iUserService.getOne(new QueryWrapper<User>().eq("user_id",userId)).getAvatar();

        try {
            File file = new File("src/main/avatar");
            String filePath = file.getCanonicalPath();

            BufferedImage image = ImageIO.read(new FileInputStream(new File(filePath + File.separator + imagePath)));
            response.setContentType("image/gif");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "gif", os);
            }
        } catch (IOException e) {
            log.error("获取图片异常{}", e.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

}
