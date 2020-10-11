package com.jason.blog.service;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.BlogQuery;
import com.jason.blog.domain.User;

import java.util.List;
import java.util.Map;

public interface BlogService {

    //查询总数量     admin
    Long countAll();

    //分页显示并按照updateTime进行倒序     admin
    List<Blog> listAll(int index, int size);

    //分页显示并按照updateTime进行倒序&发布
    List<Blog> listAllByPublish(int index, int size);

    //根据title&typeId&recommend分页并按照updateTime进行倒序
    List<Blog> pageForTTR(BlogQuery blogQuery,int index,int size);

    //title&typeId&recommend条件查询数量
    Long count(BlogQuery blogQuery);

    //新增Blog
    void saveBlog(Blog blog, Long typeId, User user);

    //根据id修改
    void updateBlog(Blog blog,Long typeId);

    //根据id删除
    void deleteBlog(Long id);

    //根据typeId查询
    boolean findByTypeId(Long typeId);

    //根据title查询
    boolean findByTitle(String title);

    //根据id查询
    Blog findById(Long id);

    //根据id查询并转换content并设置tags
    Blog getAndConvert(Long id);

    //最新推荐
    List<Blog> newTop(int size);

    //已发布数量
    Long countPublish();

    //全局search
    List<Blog> searchBlog(String query,int index,int size);

    //全局search总数
    Long searchCount(String query);

    //归档，按照年份分组
    Map<Integer,List<Blog>> archives();
}
