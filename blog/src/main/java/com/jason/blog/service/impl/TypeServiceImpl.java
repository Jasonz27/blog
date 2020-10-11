package com.jason.blog.service.impl;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Type;
import com.jason.blog.mapper.TypeMapper;
import com.jason.blog.service.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    //查询总数量     admin
    @Override
    public Long countAll() {
        return typeMapper.countAll();
    }

    //分页显示所有        admin
    @Override
    public List<Type> listAll(int index, int size) {
        return typeMapper.listAll(index, size);
    }

    //根据name查询
    @Override
    public Type findByName(String name) {
        return typeMapper.findByName(name);
    }

    //根据id查询
    @Override
    public Type findById(Long id) {
        return typeMapper.findById(id);
    }

    //新增        admin
    @Override
    public boolean saveType(String name) {
        Type byName = typeMapper.findByName(name);
        if (byName != null)
            return false;
        else {
            typeMapper.saveType(name);
            return true;
        }
    }

    //修改        admin
    @Override
    public boolean updateType(Type type) {
        Type byName = typeMapper.findByName(type.getName());
        if (byName == null) {
            typeMapper.updateType(type);
            return true;
        } else
            return false;
    }

    //根据id删除        admin
    @Override
    public void deleteById(Long id) {
        typeMapper.deleteById(id);
    }

    //根据blog数量倒序
    @Override
    public List<Map<Type, Long>> findAllBlogSizeByType(int index, int size) {
        return typeMapper.findAllBlogSizeByType(index, size);
    }

    //根据typeId查询blog数量
    @Override
    public Long countByTypeId(Long typeId) {
        return typeMapper.countByTypeId(typeId);
    }

    //根据typeId查询全部blog
    @Override
    public List<Blog> findBlogByTypeId(Long typeId, int index, int size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("typeId", typeId);
        hashMap.put("index", index);
        hashMap.put("size", size);
        return typeMapper.findBlogByTypeId(hashMap);
    }
}
