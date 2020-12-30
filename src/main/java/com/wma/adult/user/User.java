package com.wma.adult.user;

import com.wma.adult.user.EncryptUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)// 自增涨
    private String id;
// ----------------------------------------------------- 账户信息开始;
    /**
     * 用户账户
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户Token
     */
    private String token;

// ----------------------------------------------------- 账户信息结束;



// ----------------------------------------------------- 用户基本信息开始;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户电话号码
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户头像
     */
    private String headImage;

    /**
     * 背景墙
     */
    private String bgWall;

    /**
     * 性别，0：男，1：女，2：其他
     */
    private Integer sex;

    /**
     * 生日
     */
    private Long birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 登录时间
     */
    private Long loginTime;



// ----------------------------------------------------- 用户基本信息结束;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getBgWall() {
        return bgWall;
    }

    public void setBgWall(String bgWall) {
        this.bgWall = bgWall;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
