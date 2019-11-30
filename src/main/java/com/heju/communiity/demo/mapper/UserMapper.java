package com.heju.communiity.demo.mapper;

import com.heju.communiity.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,count_id,token,gmt_create,gmt_modified) value (#{name},#{countId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User uer);
}
