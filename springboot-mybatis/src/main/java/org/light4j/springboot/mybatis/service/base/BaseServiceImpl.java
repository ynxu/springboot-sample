package org.light4j.springboot.mybatis.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * 主要用途：通用Service 封装常用的CRUD方法
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    public List<T> list(T entity) {
        return mapper.select(entity);
    }

    public T get(T entity) {
        return  mapper.selectOne(entity);
    }

    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public int save(T entity) {
        return mapper.insertSelective(entity);
    }

    public int delete(T entity) {
        return mapper.deleteByPrimaryKey(entity);
    }
}
