package org.light4j.springboot.mybatis.service.mappser;

import org.light4j.springboot.mybatis.service.entity.UserEntity;
import tk.mybatis.mapper.common.Mapper;
/**
 * 继承通用Mapper获取CURD方法
 */
public interface UserMapper extends Mapper<UserEntity> {

}
