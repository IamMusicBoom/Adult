package com.wma.adult.user;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("insert into user(id,account,password,user_phone,user_email) values (#{id},#{account},#{password},#{userPhone},#{userEmail})")
    void register(String id, String account, String password, String userPhone, String userEmail);

    @Select("select * from user where account = #{account}")
    User getUserByAccount(String account);

    @Select("update user set token = #{token},user_name = #{userName},user_phone = #{userPhone},user_email = #{userEmail}" +
            ",bg_wall = #{bgWall},head_image = #{headImage},sex = #{sex}" +
            ",birthday = #{birthday},province = #{province},city = #{city},area = #{area},login_time = #{loginTime} where id = #{id}")
    void updateUser(String id, String account, String password, String token
            , String userName, String userPhone, String userEmail, String bgWall, String headImage, Integer sex
            , Long birthday, String province, String city, String area, Long loginTime);

    @Select("select * from user where id = #{id}")
    User getUserById(String id);

    @Select("update user set password = #{password} where id = #{id}")
    void resetPassword(String id, String password);
}
