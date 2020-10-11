package com.jason.blog.service.impl;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.BlogQuery;
import com.jason.blog.domain.User;
import com.jason.blog.mapper.BlogMapper;
import com.jason.blog.mapper.TagMapper;
import com.jason.blog.service.BlogService;
import com.jason.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private TagMapper tagMapper;

    //查询总数量     admin
    @Override
    public Long countAll() {
        return blogMapper.countAll();
    }

    //分页显示并按照updateTime进行倒序     admin
    @Override
    public List<Blog> listAll(int index, int size) {
        return blogMapper.listAll(index, size);
    }

    //分页显示并按照updateTime进行倒序&发布
    @Override
    public List<Blog> listAllByPublish(int index, int size) {
        return blogMapper.listAllByPublish(index, size);
    }

    //根据title&typeId&recommend分页并按照updateTime进行倒序   admin
    @Override
    public List<Blog> pageForTTR(BlogQuery blogQuery, int index, int size) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("title", blogQuery.getTitle());
        hashMap.put("typeId", blogQuery.getTypeId());
        hashMap.put("recommend", blogQuery.isRecommend());
        hashMap.put("index", index);
        hashMap.put("size", size);
        return blogMapper.pageForTTR(hashMap);
    }

    //新增Blog        admin
    @Override
    public void saveBlog(Blog blog, Long typeId, User user) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("title", blog.getTitle());
        hashMap.put("content", blog.getContent());
        hashMap.put("firstPic", blog.getFirstPic());
        hashMap.put("flag", blog.getFlag());
        hashMap.put("views", 0);
        hashMap.put("appreciation", blog.isAppreciation());
        hashMap.put("shareStatement", blog.isShareStatement());
        hashMap.put("commentAble", blog.isCommentAble());
        hashMap.put("publish", blog.isPublish());
        hashMap.put("recommend", blog.isRecommend());
        hashMap.put("createTime", new Date());
        hashMap.put("updateTime", new Date());
        hashMap.put("description", blog.getDescription());
        hashMap.put("typeId", typeId);
        hashMap.put("userId", user.getId());
        blogMapper.saveBlog(hashMap);
    }

    //根据id修改        admin
    @Override
    public void updateBlog(Blog blog, Long typeId) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", blog.getId());
        hashMap.put("title", blog.getTitle());
        hashMap.put("content", blog.getContent());
        hashMap.put("firstPic", blog.getFirstPic());
        hashMap.put("flag", blog.getFlag());
        hashMap.put("appreciation", blog.isAppreciation());
        hashMap.put("shareStatement", blog.isShareStatement());
        hashMap.put("commentAble", blog.isCommentAble());
        hashMap.put("publish", blog.isPublish());
        hashMap.put("recommend", blog.isRecommend());
        hashMap.put("updateTime", new Date());
        hashMap.put("description", blog.getDescription());
        hashMap.put("typeId", typeId);
        blogMapper.updateBlog(hashMap);
    }

    //title&typeId&recommend条件查询数量      admin
    @Override
    public Long count(BlogQuery blogQuery) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("title", blogQuery.getTitle());
        hashMap.put("typeId", blogQuery.getTypeId());
        hashMap.put("recommend", blogQuery.isRecommend());
        return blogMapper.count(hashMap);
    }

    //根据id删除        admin
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    //根据typeId查询        admin
    @Override
    public boolean findByTypeId(Long typeId) {
        return blogMapper.findByTypeId(typeId).isPresent();
    }

    //根据title查询     admin
    @Override
    public boolean findByTitle(String title) {
        return blogMapper.findByTitle(title).isPresent();
    }

    //根据id查询        admin
    @Override
    public Blog findById(Long id) {
        Optional<Blog> byId = blogMapper.findById(id);
        if (byId.isPresent())
            return byId.get();
        else
            return null;
    }

    //根据id查询并转换content并设置tags
    @Override
    public Blog getAndConvert(Long id) {
        Optional<Blog> byId = blogMapper.findById(id);
        if (byId.isPresent()) {
            blogMapper.viewsAdd(id);
            Blog temp = new Blog();
            BeanUtils.copyProperties(byId.get(), temp);
            String content = temp.getContent();
            temp.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
            temp.setTags(tagMapper.findAllTagByBlogId(id));
            return temp;
        } else
            return null;
    }

    //最新推荐
    @Override
    public List<Blog> newTop(int size) {
        return blogMapper.newTop(size);
    }

    //已发布数量
    @Override
    public Long countPublish() {
        return blogMapper.countPublish();
    }

    //全局search
    @Override
    public List<Blog> searchBlog(String query, int index, int size) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("query", query);
        hashMap.put("index", index);
        hashMap.put("size", size);
        List<Blog> blogs = blogMapper.searchBlog(hashMap);
        for (Blog blog : blogs) {
            Long id = blog.getId();
            blog.setTags(tagMapper.findAllTagByBlogId(id));
        }
        return blogs;
    }

    //全局search总数
    @Override
    public Long searchCount(String query) {
        return blogMapper.searchCount(query);
    }

    //归档，按照年份分组
    @Override
    public Map<Integer, List<Blog>> archives() {
        List<Integer> archives = blogMapper.archives();
        HashMap<Integer, List<Blog>> hashMap = new HashMap<>();
        for (Integer archive : archives) {
            hashMap.put(archive, blogMapper.findAllBlogByYear(archive));
        }
        return hashMap;
    }
}
