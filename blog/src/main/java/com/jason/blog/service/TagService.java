package com.jason.blog.service;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    //查询总数量
    Long countAll();

    //分页显示所有
    List<Tag> listAll(int index, int size);

    //根据name查询
    Tag findByName(String name);

    //根据id查询
    Tag findById(Long id);

    //新增
    boolean saveTag(String name);

    //修改
    boolean updateTag(Tag tag);

    //根据id删除
    void deleteById(Long id);

    //根据tagIds新增
    void saveTags(String tagIds);

    //新增blogId&tagId
    void saveBlogIdAndTagId(String title, String tagIds);

    //根据blogId删除
    void deleteByBlogId(Long blogId);

    //根据blogId查询
    List<Tag> findAllTagByBlogId(Long blogId);

    //对比tagIds
    void compare(List<Tag> tags, String ids, Long blogId);

    //根据blog数量倒序
    List<Map<Tag, Long>> findAllBlogSizeByTag(int index, int size);

    //根据tagId查询blog
    List<Blog> findAllBlogByTagId(Long tagId, int index, int size);

    //根据tagId查询blog数量
    Long countByTagId(Long tagId);
}
