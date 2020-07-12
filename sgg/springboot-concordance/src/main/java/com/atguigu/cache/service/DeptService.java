package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    @Qualifier("redisCacheManager")
    RedisCacheManager deptCacheManager;
    /**
     * 可以存数据，但取出来时会报错，因为CacheManager默认使用RedisTemplate<Object,Employee>操作Redis
     * 需要指定使用的CacheManager
     * @param id
     * @return
     */
//    @Cacheable(cacheNames = "dept",cacheManager = "redisCacheManager")
//    public Department getDeptById(Integer id){
//        System.out.println("查询部门："+id);
//        return departmentMapper.getDeptById(id);
//    }

    //编码方式使用缓存
    public Department getDeptById(Integer id){
        System.out.println("查询部门："+id);
        Department department = departmentMapper.getDeptById(id);
        Cache deptCache = deptCacheManager.getCache("dept");
        deptCache.put("dept:1",department);
        return department;
    }
}
