package com.lingtian.pmrApi.model.dao;

import com.lingtian.pmrApi.Entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer code);

    List<UserEntity> selectUserList();

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}