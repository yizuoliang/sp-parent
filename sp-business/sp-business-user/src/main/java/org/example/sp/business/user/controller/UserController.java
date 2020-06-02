package org.example.sp.business.user.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.example.sp.common.entity.User;
import org.example.sp.business.user.listener.UserDataListener;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.PageQuery;
import org.example.sp.common.exception.BusinessException;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: yizl
 * @Date: 2020/5/19
 * @Description: user控制类
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户控制类")
public class UserController {

    @Autowired
    private IUserService service;

    //@RequiresPermissions("user:get")
    @GetMapping("/getUserById")
    @ApiOperation("通过id获取用户")
    public Result<User> getUserById(String id) {
        User user = service.getUserById(id);
        return new Result<>(ResultEnum.SUCCESS,user);
    }

    @RequiresPermissions("user:insert")
    @PostMapping("/insertUser")
    @ApiOperation("添加用户")
    public Result<User> insertUser(@RequestBody User user) {
        service.insertUser(user);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @RequiresPermissions("user:update")
    @PostMapping("/updateUserById")
    @ApiOperation("通过id更新用户")
    public Result<User> updateUserById(@RequestBody User user) {
        service.updateUserById(user);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @RequiresPermissions("user:list")
    @PostMapping("/queryUserList")
    @ApiOperation("通过传入的条件分页查询")
    public Result<IPage<User>> queryUserList(@RequestBody PageQuery<User> pageUser ) {
        IPage<User> userPage=service.queryUserPage(pageUser);
        return new Result<>(ResultEnum.SUCCESS,userPage);
    }

    /**
     * 功能描述: <br> 下载用户
     * @Param: [pageUser, response]
     * @Return: void
     * @Author: yizl
     * @Date: 2020/5/22 13:41
     */
    @RequiresPermissions("user:download")
    @PostMapping("/download/user")
    @ApiOperation("下载用户Excel列表")
    public void downloadUserExcel(@RequestBody PageQuery<User> pageUser, HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            IPage<User> userPage=service.queryUserPage(pageUser);
            List<User> userList = userPage.getRecords();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("sp-用户列表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), User.class).autoCloseStream(Boolean.FALSE).sheet("用户列表")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(userList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            throw new BusinessException(ResultEnum.SYSTEM_EXECUTION_ERROR);
        }
    }

    @RequiresPermissions("user:upload")
    @PostMapping("/upload/user")
    @ApiOperation("批量上传保存用户")
    public Result<?> upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), User.class, new UserDataListener(service)).sheet().doRead();
        return new Result<>(ResultEnum.SUCCESS);
    }


}
