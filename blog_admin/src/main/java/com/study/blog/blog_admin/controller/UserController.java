package com.study.blog.blog_admin.controller;

import static com.study.blog.blog_core.constant.Constants.JWT_TOKEN_PREFIX;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.blog_admin.utils.JedisUtil;
import com.study.blog.blog_admin.utils.JwtUtil;
import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_admin.utils.UserUtil;
import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_core.constant.ResponseCodeEnum;
import com.study.blog.blog_core.constant.UserStatusEnum;
import com.study.blog.blog_core.utils.AesCipherUtil;
import com.study.blog.blog_core.utils.EmailUtil;
import com.study.blog.blog_core.utils.MobileUtil;
import com.study.blog.blog_model.common.PageInfo;
import com.study.blog.blog_model.common.ResponseBean;
import com.study.blog.blog_model.dto.UserDto;
import com.study.blog.blog_model.dto.UserListResponse;
import com.study.blog.blog_model.exception.CustomUnauthorizedException;
import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_service.service.IUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/api/user")
@PropertySource("classpath:config.properties")
public class UserController {

    private final UserUtil     userUtil;
    private final IUserService userService;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    public UserController(UserUtil userUtil, IUserService userService) {
        this.userUtil    = userUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestBody Map<String, String> params, HttpServletResponse response) {
        String username = params.get("username");
        String password = params.get("password");
        if (StringUtils.isAnyBlank(username, password)) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USERNAME_OR_PASSWORD_NOT_BLANK_ERROR);
        }
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new CustomUnauthorizedException("该账号不存在(the account does not exists)");
        }
        AesCipherUtil aesCipherUtil = new AesCipherUtil(username);
        String encryptPassword = aesCipherUtil.encrypt(password);
        if (user.getPassword().equals(encryptPassword)) {
            String token = JwtUtil.genToken(user.getUsername());
            response.setHeader("Authorization", JWT_TOKEN_PREFIX + token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return ResponseBeanUtil.buildSuccessResponse();
        } else {
            throw new CustomUnauthorizedException("帐号或密码错误");
        }
    }

    @PostMapping("/add")
    @RequiresPermissions(value = {"user:add"})
    public ResponseBean add(@RequestBody UserDto userDto) {
        ResponseCodeEnum codeEnum = preCheckUserInfo(userDto);
        if (ResponseCodeEnum.SUCCESS.equals(codeEnum)) {
            return ResponseBeanUtil.buildResponseByCode(codeEnum);
        }
        User user = userService.getByUsername(userDto.getUsername());
        if (user != null) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USER_REPEATED_ERROR);
        }
        User newUser = UserDto.transformUserDto(userDto);
        AesCipherUtil aesCipherUtil = new AesCipherUtil(newUser.getUsername());
        newUser.setPassword(aesCipherUtil.encrypt(newUser.getPassword()));
        newUser.setStatus(UserStatusEnum.OPEN.getStatus());
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());
        userService.save(newUser);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @PostMapping("/update")
    @RequiresPermissions(value = {"user:update"})
    public ResponseBean update(@RequestBody UserDto userDto) {
        ResponseCodeEnum codeEnum = preCheckUserInfo(userDto);
        if (ResponseCodeEnum.SUCCESS.equals(codeEnum)) {
            return ResponseBeanUtil.buildResponseByCode(codeEnum);
        }
        if (!userDto.getRepeatPassword().equals(userDto.getPassword())) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.REPEAT_PASSWORD_NOT_EQUAL);
        }
        User user = userService.getByUsername(userDto.getUsername(), userDto.getId());
        if (user != null) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USER_REPEATED_ERROR);
        }
        User newUser = UserDto.transformUserDto(userDto);
        AesCipherUtil aesCipherUtil = new AesCipherUtil(newUser.getUsername());
        newUser.setPassword(aesCipherUtil.encrypt(newUser.getPassword()));
        userDto.setUpdateTime(LocalDateTime.now());
        userService.updateById(newUser);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @PostMapping("/delete")
    @RequiresPermissions(value = {"user:delete"})
    public ResponseBean delete(Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USER_NOT_EXISTS_ERROR);
        }
        userService.removeById(user);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @PostMapping("/updateStatus")
    @RequiresPermissions(value = {"user:status:update"})
    public ResponseBean updateStatus(Long id, Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USER_NOT_EXISTS_ERROR);
        }
        user.setStatus(status);
        userService.updateById(user);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @GetMapping("/search")
    @RequiresPermissions(value = {"user:view"})
    public ResponseBean search(Long id, String username, Integer status, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Long count = userService.getCountByCond(id, username, status);
        List<User> userList = new ArrayList<>();
        if (count != 0) {
            userList = userService.getUserByCond(id, username, status, page, pageSize);
        }
        PageInfo pageInfo = new PageInfo(count, pageSize);

        return ResponseBeanUtil.buildSuccessResponse(new UserListResponse(pageInfo, userList));
    }

    @GetMapping("/online")
    @RequiresPermissions(value = {"user:view"})
    public ResponseBean online() {
        List<User> userList = new ArrayList<>();
        // 查询所有的Redis键
        Set<String> keys = JedisUtil.keysS(Constants.PREFIX_SHIRO_REFRESH_TOKEN + "*");
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 分割key,获取最后一个字符(帐号)
                String[] strArr = key.split(":");
                String username = strArr[strArr.length - 1];
                User user = userService.getByUsername(username);
                userList.add(user);
            }
        }
        return ResponseBeanUtil.buildSuccessResponse(userList);
    }

    @GetMapping("/info/personal")
    @RequiresAuthentication
    public ResponseBean personalInfo() {
        User user = userUtil.getUser();
        return ResponseBeanUtil.buildSuccessResponse(user);
    }

    // TODO:暂时停用该功能
    @PostMapping("/register")
    public ResponseBean register(@RequestBody UserDto userDto) {
        ResponseCodeEnum codeEnum = preCheckUserInfo(userDto);
        if (ResponseCodeEnum.SUCCESS.equals(codeEnum)) {
            return ResponseBeanUtil.buildResponseByCode(codeEnum);
        }
        User user = userService.getByUsername(userDto.getUsername());
        if (user != null) {
            return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.USER_REPEATED_ERROR);
        }
        User newUser = UserDto.transformUserDto(userDto);
        AesCipherUtil aesCipherUtil = new AesCipherUtil(newUser.getUsername());
        newUser.setPassword(aesCipherUtil.encrypt(newUser.getPassword()));
        newUser.setStatus(UserStatusEnum.OPEN.getStatus());
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());
        userService.save(newUser);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    private ResponseCodeEnum preCheckUserInfo(UserDto userDto) {
        // 为空校验
        if (StringUtils.isAnyBlank(userDto.getUsername(), userDto.getPassword())) {
            return ResponseCodeEnum.USERNAME_OR_PASSWORD_NOT_BLANK_ERROR;
        }
        // 用户名长度校验
        if (userDto.getUsername().length() < Constants.USERNAME_PASSWORD_MIN_LENGTH ||
            userDto.getUsername().length() > Constants.USERNAME_PASSWORD_MAX_LENGTH) {
            return ResponseCodeEnum.USERNAME_LENGTH_ERROR;
        }
        // 密码长度校验
        if (userDto.getPassword().length() < Constants.USERNAME_PASSWORD_MIN_LENGTH ||
            userDto.getPassword().length() > Constants.USERNAME_PASSWORD_MAX_LENGTH) {
            return ResponseCodeEnum.PASSWORD_LENGTH_ERROR;
        }
        // 用户名合法化校验 大小写字母+数字

        // 密码合法化校验 大小写字母+数字+特殊符号 满足三种

        // 邮箱合法化
        if (StringUtils.isNotBlank(userDto.getEmail()) && EmailUtil.checkEmail(userDto.getEmail())) {
            return ResponseCodeEnum.EMAIL_PATTERN_ERROR;
        }
        // 手机号合法化
        if (StringUtils.isNotBlank(userDto.getMobile()) && MobileUtil.checkMobile(userDto.getMobile())) {
            return ResponseCodeEnum.MOBILE_PATTERN_ERROR;
        }
        // 性别合法化

        return ResponseCodeEnum.SUCCESS;
    }

}
