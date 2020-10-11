package com.jason.blog.service.impl;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Tag;
import com.jason.blog.mapper.BlogMapper;
import com.jason.blog.mapper.TagMapper;
import com.jason.blog.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private BlogMapper blogMapper;

    //查询总数量
    @Override
    public Long countAll() {
        return tagMapper.countAll();
    }

    //分页显示所有
    @Override
    public List<Tag> listAll(int index, int size) {
        return tagMapper.listAll(index, size);
    }

    //根据name查询
    @Override
    public Tag findByName(String name) {
        return tagMapper.findByName(name);
    }

    //根据id查询
    @Override
    public Tag findById(Long id) {
        return tagMapper.findById(id);
    }

    //新增
    @Override
    public boolean saveTag(String name) {
        Tag byName = tagMapper.findByName(name);
        if (byName != null)
            return false;
        else {
            tagMapper.saveTag(name);
            return true;
        }
    }

    //修改
    @Override
    public boolean updateTag(Tag tag) {
        Tag byName = tagMapper.findByName(tag.getName());
        if (byName == null) {
            tagMapper.updateTag(tag);
            return true;
        } else {
            return false;
        }
    }

    //根据id删除
    @Override
    public void deleteById(Long id) {
        tagMapper.deleteById(id);
    }

    //根据tagIds新增
    @Override
    public void saveTags(String tagIds) {
        String[] split = tagIds.split(",");
        for (String s : split) {
            if (pattern(s))
                continue;
            tagMapper.saveTag(s);
        }
    }

    boolean pattern(String s) {
        Pattern compile = Pattern.compile("[0-9]+");
        return compile.matcher(s).matches();
    }

    //新增blogId&tagId
    @Override
    public void saveBlogIdAndTagId(String title, String tagIds) {
        Long blogId = blogMapper.findByTitle(title).get().getId();
        String[] split = tagIds.split(",");
        for (String s : split) {
            if (pattern(s))
                tagMapper.saveBlogIdAndTagId(blogId, Long.valueOf(s));
            else
                tagMapper.saveBlogIdAndTagId(blogId, tagMapper.findByName(s).getId());
        }
    }

    //根据blogId删除
    @Override
    public void deleteByBlogId(Long blogId) {
        tagMapper.deleteByBlogId(blogId);
    }

    //根据blogId查询
    @Override
    public List<Tag> findAllTagByBlogId(Long blogId) {
        return tagMapper.findAllTagByBlogId(blogId);
    }

    //对比tagIds
    @Override
    public void compare(List<Tag> tags, String ids, Long blogId) {
        List<Tag> oldIds = new ArrayList<>();
        oldIds.addAll(tags);
        List<Tag> newIds = new ArrayList<>();
        String[] split = ids.split(",");
        for (String s : split) {
            if (pattern(s)) {
                newIds.add(tagMapper.findById(Long.valueOf(s)));
            } else {
                tagMapper.saveTag(s);
                newIds.add(tagMapper.findByName(s));
            }
        }
        List<Tag> tempList = new ArrayList<>();
        tempList.addAll(oldIds);
        tempList.retainAll(newIds);
        for (Tag tag : tempList) {
            newIds.remove(tag);
            oldIds.remove(tag);
        }
        if (!oldIds.isEmpty()) {
            for (Tag oldId : oldIds) {
                tagMapper.deleteBTById(oldId.getId());
            }
        }
        if (!newIds.isEmpty()){
            for (Tag newId : newIds) {
                tagMapper.saveBlogIdAndTagId(blogId,newId.getId());
            }
        }
    }

    //根据blog数量倒序
    @Override
    public List<Map<Tag, Long>> findAllBlogSizeByTag(int index, int size) {
        return tagMapper.findAllBlogSizeByTag(index,size);
    }

    //根据tagId查询blog
    @Override
    public List<Blog> findAllBlogByTagId(Long tagId, int index, int size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tagId",tagId);
        hashMap.put("index",index);
        hashMap.put("size",size);
        List<Blog> allBlogByTagId = tagMapper.findAllBlogByTagId(hashMap);
        for (Blog blog : allBlogByTagId) {
            List<Tag> allTagByBlogId = tagMapper.findAllTagByBlogId(blog.getId());
            blog.setTags(allTagByBlogId);
        }
        return allBlogByTagId;
    }

    //根据tagId查询blog数量
    @Override
    public Long countByTagId(Long tagId) {
        return tagMapper.countByTagId(tagId);
    }
}
