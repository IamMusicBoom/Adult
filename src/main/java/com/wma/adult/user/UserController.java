package com.wma.adult.user;

import com.wma.adult.Log;
import com.wma.adult.base.BaseController;
import com.wma.adult.base.ResponseModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService mUserService;

    @PostMapping("/register")
    public ResponseModule register(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password
            , @RequestParam(value = "userPhone") String userPhone, @RequestParam(value = "userEmail") String userEmail) {
        Log.d("register", "account = " + account + " password = " + password + " userPhone = " + userPhone + " userEmail = " + userEmail);
        User userByAccount = mUserService.getUserByAccount(account);
        if (userByAccount != null) {
            ResponseModule module = new ResponseModule(201, "该账户已经注册", null);
            return module;
        }
        User user = new User();
        user.setId(EncryptUtils.md5(UUID.randomUUID().toString()));
        user.setAccount(account);
        user.setPassword(password);
        user.setUserPhone(userPhone);
        user.setUserEmail(userEmail);
        mUserService.register(user);
        ResponseModule module = new ResponseModule(200, "注册成功", user);
        return module;
    }

    @PostMapping("/login")
    public ResponseModule login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        Log.d("login", "account = " + account + " password = " + password);
        ResponseModule module = null;
        User user = mUserService.getUserByAccount(account);
        if (user == null) {
            module = new ResponseModule(201, "该账号未注册");
        } else {
            String password1 = user.getPassword();
            if (!password1.equals(password)) {
                module = new ResponseModule(201, "密码错误");
            } else {
                long nowTime = System.currentTimeMillis();
                String token = EncryptUtils.generateToken(account, password, nowTime);
                user.setLoginTime(nowTime);
                user.setToken(token);
                mUserService.updateUser(user);
                module = new ResponseModule(200, "登录成功", user);
            }
        }
        return module;
    }

    @PostMapping("/resetPassword")
    public ResponseModule resetPassword(@RequestParam(value = "account") String account, @RequestParam(value = "resetType") String resetType
            , @RequestParam(value = "userPhone") String userPhone, @RequestParam(value = "userEmail") String userEmail
            , @RequestParam(value = "password") String password) {
        Log.d("resetPassword", "account = " + account + " password = " + password + " resetType = " + resetType + " userPhone = " + userPhone + " userEmail = " + userEmail);
        ResponseModule module = null;
        User user = mUserService.getUserByAccount(account);
        if ("Email".equals(resetType)) {
            if (!StringUtils.isEmpty(userEmail)) {
                if (userEmail.equals(user.getUserEmail())) {// 可以重置密码
                    mUserService.resetPassword(user.getId(), password);
                    module = new ResponseModule(200, "重置成功");
                } else {
                    module = new ResponseModule(201, "邮箱错误，无法重置");
                }
            } else {
                module = new ResponseModule(201, "邮箱未填写");
            }
        } else if ("Phone".equals(resetType)) {
            if (!StringUtils.isEmpty(userPhone)) {
                if (userPhone.equals(user.getUserPhone())) {// 可以重置密码
                    mUserService.resetPassword(user.getId(), password);
                    module = new ResponseModule(200, "重置成功");
                } else {
                    module = new ResponseModule(201, "电话号码错误，无法重置");
                }
            } else {
                module = new ResponseModule(201, "电话号码未填写");
            }
        } else {
            module = new ResponseModule(201, "重置类型未填写");
        }
        return module;
    }


    @PostMapping("/updateUser")
    public ResponseModule updateUser(@RequestParam("id") String id
            , @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "userPhone", required = false) String userPhone, @RequestParam(value = "userEmail", required = false) String userEmail
            , @RequestParam(value = "bgWall", required = false) String bgWall, @RequestParam(value = "headImage", required = false) String headImage, @RequestParam(value = "sex", required = false) String sex
            , @RequestParam(value = "birthday", required = false) String birthday, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city
            , @RequestParam(value = "area", required = false) String area, HttpServletRequest request) {
        User user = mUserService.getUserById(id);
        user.setUserName(userName);
        user.setUserPhone(userPhone);
        user.setUserEmail(userEmail);
        if (!StringUtils.isEmpty(sex)) {
            user.setSex(Integer.valueOf(sex));
        }
        if (!StringUtils.isEmpty(birthday)) {
            user.setBirthday(Long.valueOf(birthday));
        }
        user.setCity(city);
        user.setProvince(province);
        user.setArea(area);

        try {
            MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = request1.getFileNames();
            while (fileNames.hasNext()) {
                String separator = File.separator;
                StringBuilder path = new StringBuilder("F:" + separator + "upload" + separator);
                String name = fileNames.next();
                MultipartFile file = request1.getFile(name);
                if (file != null) {
                    if (name != null) {
                        String suffixName = name.substring(name.lastIndexOf("."));
                        System.out.println("文件名字：" + name + " 后缀名字：" + suffixName);
                        path.append("user").append(separator).append(id).append(separator).append(UUID.nameUUIDFromBytes(name.getBytes())).append("_").append(System.currentTimeMillis()).append(suffixName);
                        String filePath = path.toString();
                        File desc = new File(filePath);
                        if (!desc.getParentFile().exists()) {
                            desc.getParentFile().mkdirs();
                        }
                        file.transferTo(desc);
                        String absolutePath = desc.getAbsolutePath();
                        StringBuilder savePathSb = new StringBuilder();
                        savePathSb.append(getUrl()).append("/").append(absolutePath);
                        String replace = savePathSb.toString().replace(separator, "/");
                        if (name.equalsIgnoreCase(bgWall)) {
                            user.setBgWall(replace);
                        } else if (name.equalsIgnoreCase(headImage)) {
                            user.setHeadImage(replace);
                        }
                        System.out.println("path = " + desc.getAbsolutePath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        mUserService.updateUser(user);
        ResponseModule module = new ResponseModule(200, "修改成功", user);
        return module;
    }


    @GetMapping("/getUserById")
    public ResponseModule getUserById(@RequestParam(value = "userId") String userId) {
        Log.d("register", "userId = " + userId);
        User userByAccount = mUserService.getUserById(userId);
        if (userByAccount == null) {
            ResponseModule module = new ResponseModule(201, "未查到该用户", null);
            return module;
        }
        ResponseModule module = new ResponseModule(200, "查询成功", userByAccount);
        return module;
    }

}
