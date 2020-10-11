package com.jason.blog.mapper;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {

    //查询总数量
    Long countAll();

    //分页显示所有
    List<Tag> listAll(int index, int size);

    //根据name查询
    Tag findByName(String name);

    //根据id查询
    Tag findById(Long id);

    //新增
    void saveTag(String name);

    //修改
    void updateTag(Tag tag);

    //根据id删除
    void deleteById(Long id);

    //新增blogId&tagId
    void saveBlogIdAndTagId(Long blogId,Long tagId);

    //根据blogId删除
    void deleteByBlogId(Long blogId);

    //根据blogId查询
    List<Tag> findAllTagByBlogId(Long blogId);

    //根据tagId删除t_blog_tag
    void deleteBTById(Long id);

    //根据blog数量倒序
    List<Map<Tag,Long>> findAllBlogSizeByTag(int index, int size);

    //根据tagId查询blog
    List<Blog> findAllBlogByTagId(HashMap hashMap);

     //根据tagId查询blog数量
    Long countByTagId(Long tagId);
}
