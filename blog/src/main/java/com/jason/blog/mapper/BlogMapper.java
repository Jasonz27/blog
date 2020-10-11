package com.jason.blog.mapper;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.BlogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface BlogMapper {

    //分页显示并按照updateTime进行倒序     admin
    List<Blog> listAll(int index, int size);

    //分页显示并按照updateTime进行倒序&发布
    List<Blog> listAllByPublish(int index, int size);

    //根据id查询
    Optional<Blog> findById(Long id);

    //查询总数量     admin
    Long countAll();

    //根据title&typeId&recommend分页并按照updateTime进行倒序 & 全局search
    List<Blog> pageForTTR(HashMap map);

    //title&typeId&recommend条件查询数量
    Long count(HashMap hashMap);

    //根据id删除
    void deleteBlog(Long id);

    //根据typeId查询
    Optional<Blog> findByTypeId(Long typeId);

    //新增Blog
    void saveBlog(HashMap hashMap);

    //根据title查询
    Optional<Blog> findByTitle(String title);

    //根据id修改
    void updateBlog(HashMap hashMap);

    //最新推荐
    List<Blog> newTop(int size);

    //已发布数量
    Long countPublish();

    //全局search
    List<Blog> searchBlog(HashMap hashMap);

    //全局search总数
    Long searchCount(String query);

    //按照updateTime的年份进行分组
    List<Integer> archives();

    List<Blog> findAllBlogByYear(int year);

    //浏览次数
    void viewsAdd(Long id);
}
