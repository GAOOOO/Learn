package com.bs.demo.controller.homeController;


import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.demo.common.Result;
import com.bs.demo.dto.PostsDto;
import com.bs.demo.dto.RankingDto;
import com.bs.demo.entity.*;
import com.bs.demo.service.*;
import com.bs.demo.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/publicCon")
@Slf4j
public class HomeController {


    @Autowired
    private ITeacherService iTeacherService;

    @Autowired
    private IAnswerService iAnswerService;

    @Autowired
    private IUserService iUserService;


    @Autowired
    private ILearnLibraryService iLearnLibraryService;


    @Autowired
    private IPostsService iPostsService;

    @Autowired
    private IUserClassService iUserClassService;


    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ILibraryClassService iLibraryClassService;


    @Autowired
    private ITopicService iTopicService;


    @Autowired
    private ILearnResService iLearnResService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private ICommentService iCommentService;

    @GetMapping(value = "/getLibraryImg")
    public void getLibraryImg(String libraryId, HttpServletResponse response) throws IOException {
        OutputStream os = null;

        String imagePath = iLearnLibraryService.getOne(new QueryWrapper<LearnLibrary>().eq("library_id", libraryId)).getLibraryImg();

        try {
            File file = new File("src/main/library");
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


    @PostMapping(value = "/library_upload")
    public Result libraryUpload(@RequestBody MultipartFile file,String libraryId) throws IOException {
        // 用户信息回填

        InputStream inputStream = file.getInputStream();

        OutputStream os = null;
        try {
            String path = "src/main/library";
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


            UUID uuid = UUID.randomUUID();
            String filePath = tempFile.getPath() + File.separator + uuid + file.getOriginalFilename();

            File files = new File(filePath);
            files.delete();

            os = new FileOutputStream(filePath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            iLearnLibraryService.update(new UpdateWrapper<LearnLibrary>()
                    .eq("library_id",libraryId)
                    .set("library_img",File.separator + uuid + file.getOriginalFilename()));


            return Result.success().message("上传成功");

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


    @PostMapping(value = "/teacher_upload")
    public Result changePhoto(@RequestBody MultipartFile file,String teacherId) throws IOException {
        // 用户信息回填

        InputStream inputStream = file.getInputStream();

        OutputStream os = null;
        try {
            String path = "src/main/teacherImg";
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

            String filePath = tempFile.getPath() + File.separator + file.getOriginalFilename();

            File files = new File(filePath);
            files.delete();

            os = new FileOutputStream(filePath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            iTeacherService.update(new UpdateWrapper<Teacher>()
                    .eq("teacher_id",teacherId)
                    .set("teacher_image",File.separator + file.getOriginalFilename()));

            return Result.success().message("上传成功");

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


    /**
     * 删除帖子
     * @return
     */
    @PostMapping("/delPosts")
    public Result delPosts(@RequestBody JSONObject jsonObject){
        String postsId = jsonObject.getStr("postsId");

        iPostsService.remove(new QueryWrapper<Posts>()
                .eq("posts_id",postsId));

        return Result.success().message("删除成功");
    }


    /**
     * 删除评论
     * @return
     */
    @PostMapping("/delComment")
    public Result delComment(@RequestBody JSONObject jsonObject){
        String comment = jsonObject.getStr("commentId");

        iCommentService.remove(new QueryWrapper<Comment>()
                .eq("comment_id",comment));

        return Result.success().message("删除成功");
    }


    /**
     * 获取个人中心信息
     *
     * @param userId
     * @param current
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserAnswer(@RequestParam String userId, Integer current) {

        QueryWrapper<Answer> answerWrapper = new QueryWrapper<>();
        answerWrapper.eq("user_id", userId);
        Page answerPage = new Page(current, 10);
        Page answerInfo = iAnswerService.page(answerPage, answerWrapper);
        answerInfo.getRecords().forEach(item -> {
            ((Answer) item).setLibraryId(iLearnLibraryService.getOne(new QueryWrapper<LearnLibrary>()
                    .eq("library_id", ((Answer) item).getLibraryId())).getLibraryInfo());
        });

        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("user_id", userId);
        Page commentPage = new Page(current, 10);
        Page commentInfo = iCommentService.page(commentPage, commentWrapper);
        commentInfo.getRecords().forEach(item -> {
            ((Comment)item).setPostsId(iPostsService.getOne(new QueryWrapper<Posts>()
                    .eq("posts_id",((Comment) item).getPostsId())).getPostsTitle());
        });


        QueryWrapper<Posts> postsWrapper = new QueryWrapper<>();
        postsWrapper.eq("user_id", userId);
        Page postsPage = new Page(current, 10);
        Page postsInfo = iPostsService.page(postsPage, postsWrapper);



        return Result.success().data(MapUtil.builder()
                .put("answerInfo", answerInfo)
                .put("commentInfo", commentInfo)
                .put("postsInfo", postsInfo)
                .map());
    }

    /**
     * 发布帖子
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/addPosts")
    public Result addPosts(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getStr("userId");
        String postsTitle = jsonObject.getStr("postsTitle");
        String postsInfo = jsonObject.getStr("postsInfo");

        Posts posts = new Posts();
        posts.setPostsTitle(postsTitle);
        posts.setPostsInfo(postsInfo);
        posts.setUserId(userId);
        posts.setCreateTime(LocalDateTime.now());
        iPostsService.save(posts);
        return Result.success().message("发布成功！");
    }

    /**
     * 添加评论
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/addComment")
    public Result addComment(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getStr("userId");
        String postsId = jsonObject.getStr("postsId");
        String commentInfo = jsonObject.getStr("commentInfo");

        Comment comment = new Comment();
        comment.setCommentInfo(commentInfo);
        comment.setUserId(userId);
        comment.setPostsId(postsId);
        comment.setCreateTime(LocalDateTime.now());
        iCommentService.save(comment);
        return Result.success().message("评论成功");
    }

    /**
     * 获取所有帖子
     *
     * @return
     */
    @GetMapping("/getAllPosts")
    public Result getAllPosts(@RequestParam String postsId) {
        List<PostsDto> postsDtos = new ArrayList<>();
        QueryWrapper<Posts> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (!postsId.equals("0")) {
            queryWrapper.eq("posts_id", postsId);
        }

        iPostsService.list(queryWrapper).forEach(item -> {
            PostsDto postsDto = new PostsDto();
            postsDto.setPostsId(item.getPostsId());
            postsDto.setPostsInfo(item.getPostsInfo());
            postsDto.setPostsTitle(item.getPostsTitle());
            postsDto.setCreateTime(item.getCreateTime());
            postsDto.setUserId(item.getUserId());
            postsDtos.add(postsDto);
        });

        postsDtos.forEach(item -> {
            item.setItems(iCommentService.list(new QueryWrapper<Comment>()
                    .orderByDesc("create_time")
                    .eq("posts_id", item.getPostsId())));
        });

        return Result.success().data(postsDtos);
    }

    /**
     * 获取资源数量信息
     *
     * @return
     */
    @GetMapping("/getResCount")
    public Result getResCount() {
        List<Integer> roleUserIds = iRoleUserService.list(new QueryWrapper<RoleUser>()
                .eq("role_id", 3)).stream().map(RoleUser::getUserId).collect(Collectors.toList());


        Integer studentCount = iLearnResService.list(new QueryWrapper<LearnRes>()
                .in("user_id", roleUserIds)).size();
        Integer teacherCount = iLearnResService.list().size() - studentCount;

        return Result.success().data(MapUtil.builder()
                .put("studentCount", studentCount)
                .put("teacherCount", teacherCount)
                .map());
    }

    /**
     * 获取资源列表
     *
     * @param current
     * @param searchText
     * @return
     */
    @GetMapping("/getRes")
    public Result getRes(@RequestParam Integer current, String searchText) {
        QueryWrapper<LearnRes> queryWrapper = new QueryWrapper<>();
        Page<LearnRes> page = new Page<>(current, 8);
        queryWrapper.like("res_title", searchText);

        return Result.success().data(iLearnResService.page(page, queryWrapper));
    }


    // ResponseEntity<byte[]>封装下载对象
    @RequestMapping(value = "/file_download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, @RequestParam String resId) throws Exception {
        LearnRes resInfo = iLearnResService.getOne(new QueryWrapper<LearnRes>().eq("res_id", resId));

        if (resInfo != null) {
            // 下载文件路径
            String path = resInfo.getResAddress();

            // 创建文件对象
            File file = new File(path);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();

            // 下载显示的文件名, 解决中文乱码的问题
            String downloadFileName = new String((path.split("/")[path.split("/").length - 1]).getBytes("UTF-8"), "ISO-8859-1");

            // 通知浏览器以下载方式(attachment)打开文件
            headers.setContentDispositionFormData("attachment", downloadFileName);

            // 定义以二进制流的方式下载返回文件数据
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // 添加下载次数
            iLearnResService.update(new UpdateWrapper<LearnRes>().eq("res_id", resInfo.getResId())
                    .set("res_download", resInfo.getResDownload() + 1));

            // 使用mvc的ResponseEntity对象封装返回下载数据
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } else {
            return null;
        }
    }


    /**
     * 添加答题记录
     *
     * @return
     */
    @PostMapping("/addAnswer")
    private Result addAnswer(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.get("userId").toString();
        Integer answerNum = Integer.parseInt(jsonObject.get("answerNum").toString());
        String libraryId = jsonObject.getStr("libraryId");
        Answer answer = new Answer();
        answer.setAnswerNum(answerNum);
        answer.setUserId(userId);
        answer.setLibraryId(libraryId);
        answer.setCreateTime(LocalDateTime.now());
        iAnswerService.save(answer);
        return Result.success().message("提交成功");
    }

    /**
     * 获取题目信息
     *
     * @param libraryId
     * @return
     */
    @GetMapping("/answer")
    private Result getAnswer(@RequestParam String libraryId) {
        List<Topic> answers = iTopicService.list(new QueryWrapper<Topic>().eq("library_id", libraryId));
        return Result.success().data(answers);
    }


    /**
     * 获取资源列表
     *
     * @return
     */
    @PostMapping("/allLibrary")
    private Result getLibrary(@RequestBody JSONObject jsonObject) {

        Integer current = Integer.parseInt(jsonObject.get("current").toString());
        List<String> sel = (List<String>) jsonObject.get("sel");
        QueryWrapper<LearnLibrary> queryWrapper = new QueryWrapper<>();
        Page<LearnLibrary> page = new Page<>(current, 8);

        if (sel.size() != 0) {
            queryWrapper.in("library_class", sel);
        }

        return Result.success().data(iLearnLibraryService.page(page, queryWrapper));
    }


    /**
     * 获取所有资源分类
     *
     * @return
     */
    @GetMapping("/libraryClass")
    private Result getLibraryClass() {
        return Result.success().data(iLibraryClassService.list());
    }


    /**
     * 获取竞赛页面排行数据
     *
     * @return
     */
    private Integer num = 0;

    @GetMapping("/ranking")
    public Result getRanking() {
        // 计算用户总分数排行
        List<User> users = iUserService.list();
        List<RankingDto> rank_user = new ArrayList<>();
        users.forEach(userItem -> {
            RankingDto userInfo = new RankingDto();
            List<Answer> answers = iAnswerService.list(new QueryWrapper<Answer>().eq("user_id", userItem.getUserId()));
            num = 0;
            answers.forEach(answerItem -> {
                num += answerItem.getAnswerNum();
            });
            userInfo.setId(userItem.getUserId());
            userInfo.setTitle(userItem.getNickName());
            userInfo.setNum(num);
            rank_user.add(userInfo);
        });
        List<RankingDto> userRanking = num(rank_user);


        // 计算题库总分数排行
        List<RankingDto> rank_library = new ArrayList<>();
        List<LearnLibrary> learnLibraries = iLearnLibraryService.list();
        learnLibraries.forEach(libraryItem -> {
            RankingDto libraryInfo = new RankingDto();
            List<Answer> answers = iAnswerService.list(new QueryWrapper<Answer>().eq("library_id", libraryItem.getLibraryId()));
            num = 0;
            answers.forEach(answerItem -> {
                num += answerItem.getAnswerNum();
            });
            libraryInfo.setId(libraryItem.getLibraryId());
            libraryInfo.setTitle(libraryItem.getLibraryInfo());
            libraryInfo.setNum(num);
            rank_library.add(libraryInfo);
        });
        List<RankingDto> libraryRangking = num(rank_library);


        // 计算班级积分排行
        List<RankingDto> rank_userClass = new ArrayList<>();
        List<UserClass> userClassList = iUserClassService.list();
        userClassList.forEach(userClassItem -> {
            RankingDto userClassInfo = new RankingDto();
            num = 0;
            List<String> userIds = iStudentService.list(new QueryWrapper<Student>().eq("class_id", userClassItem.getClassId())).stream().map(Student::getUserId).collect(Collectors.toList());
            List<Answer> userAnswer = iAnswerService.list(new QueryWrapper<Answer>().in("user_id", userIds));
            userAnswer.forEach(item -> {
                num += item.getAnswerNum();
            });
            userClassInfo.setId(Integer.parseInt(userClassItem.getUserId()));
            userClassInfo.setTitle(userClassItem.getClassInfo());
            userClassInfo.setNum(num);
            rank_userClass.add(userClassInfo);
        });
        List<RankingDto> userClassRanking = num(rank_userClass);


        return Result.success().data(MapUtil.builder()
                .put("userRanking", userRanking.size() > 10 ? userRanking.subList(0, 10) : userRanking)
                .put("libraryRanking", libraryRangking.size() > 10 ? libraryRangking.subList(0, 10) : libraryRangking)
                .put("userClassRanking", userClassRanking.size() > 10 ? userClassRanking.subList(0, 10) : userClassRanking)
                .map()
        );
    }


    /**
     * 从下到小排列
     *
     * @return
     */
    private List<RankingDto> num(List<RankingDto> nums) {
        RankingDto rankingDto = new RankingDto();
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                if (nums.get(i).getNum() < nums.get(j).getNum()) {
                    rankingDto.setId(nums.get(i).getId());
                    rankingDto.setTitle(nums.get(i).getTitle());
                    rankingDto.setNum(nums.get(i).getNum());
                    nums.get(i).setId(nums.get(j).getId());
                    nums.get(i).setTitle(nums.get(j).getTitle());
                    nums.get(i).setNum(nums.get(j).getNum());
                    nums.get(j).setId(rankingDto.getId());
                    nums.get(j).setTitle(rankingDto.getTitle());
                    nums.get(j).setNum(rankingDto.getNum());
                }
            }
        }

        return nums;
    }

    /**
     * 获取所有用户发布帖子
     *
     * @return
     */
    @GetMapping("/posts")
    public Result getPosts() {
        return Result.success().data(iPostsService.list());
    }


    /**
     * 获取展示题库信息
     *
     * @return
     */
    @GetMapping("/library")
    public Result getLibrary() {
        List<Answer> library_id = iAnswerService.list(new QueryWrapper<Answer>()
                .select("library_id")
                .groupBy("library_id"));
        library_id.forEach(item -> {
            item.setAnswerNum(Integer.parseInt(String.valueOf(iAnswerService.count(new QueryWrapper<Answer>()
                    .eq("library_id", item.getLibraryId())))));
        });

        Answer temp = new Answer();
        for (int i = 0; i < library_id.size(); i++) {
            for (int j = i + 1; j < library_id.size(); j++) {
                if (library_id.get(i).getAnswerNum() < library_id.get(j).getAnswerNum()) {
                    temp.setLibraryId(library_id.get(i).getLibraryId());
                    temp.setAnswerNum(library_id.get(i).getAnswerNum());
                    library_id.get(i).setLibraryId(library_id.get(j).getLibraryId());
                    library_id.get(i).setAnswerNum(library_id.get(j).getAnswerNum());
                    library_id.get(j).setLibraryId(temp.getLibraryId());
                    library_id.get(j).setAnswerNum(temp.getAnswerNum());
                }
            }
        }
        if (library_id.size() >= 4) {
            library_id.subList(0, 4).forEach(item -> {
                item.setAnswerId(Integer.parseInt(item.getLibraryId()));
                LearnLibrary learnLibrary = iLearnLibraryService.getOne(new QueryWrapper<LearnLibrary>()
                        .eq("library_id", item.getLibraryId()));

                item.setLibraryId(learnLibrary.getLibraryInfo());
                item.setUserId(iUserService.getOne(new QueryWrapper<User>().eq("user_id", learnLibrary.getUserId())).getNickName());
            });

            return Result.success().data(library_id.subList(0, 4));
        } else {
            for (Answer item : library_id) {
                item.setAnswerId(Integer.parseInt(item.getLibraryId()));
            }
            return Result.success().data(library_id);
        }
    }


    /**
     * 获取平台首页的优秀教师模块数据
     *
     * @return
     */
    @GetMapping("/teacher")
    public Result getTeacher() {
        return Result.success().data(iTeacherService.list());
    }


    /**
     * 获取优秀教师用户头像
     *
     * @param teacherId
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/getTeacherImg")
    @ApiOperation(value = "获取头像")
    public void getTeacherImg(String teacherId, HttpServletResponse response) throws IOException {
        OutputStream os = null;

        String imagePath = iTeacherService.getOne(new QueryWrapper<Teacher>().eq("teacher_id", teacherId)).getTeacherImage();

        try {
            File file = new File("src/main/teacherImg");
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