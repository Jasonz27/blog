package com.jason.blog.mapper;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TypeMapper {

    //查询总数量
    Long countAll();

    //分页显示所有
    List<Type> listAll(int index, int size);

    //根据name查询
    Type findByName(String name);

    //根据id查询
    Type findById(Long id);

    //新增
    void saveType(String name);

    //修改
    void updateType(Type type);

    //根据id删除
    void deleteById(Long id);

    //根据blog数量倒序
    List<Map<Type, Long>> findAllBlogSizeByType(int index, int size);

    //根据typeId查询blog数量
    Long countByTypeId(Long typeId);

    //根据typeId查询blog
    List<Blog> findBlogByTypeId(HashMap hashMap);
}
