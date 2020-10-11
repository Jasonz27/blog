package com.jason.blog;

import com.jason.blog.domain.*;
import com.jason.blog.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.*;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TagService tagService;

    @Test
    void t() {
    }

}
