package com.yhao.webdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yhao.webdemo.dao.mapper.db1.ResourceMapper;
import com.yhao.webdemo.dao.mapper.db1.RoleResourceMapper;
import com.yhao.webdemo.dao.model.Resource;
import com.yhao.webdemo.dao.model.RoleResource;
import com.yhao.webdemo.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public List<Resource> getResourceByRole(Integer roleId) {
        List<RoleResource> roleResources =
                roleResourceMapper.selectList(new LambdaQueryWrapper<RoleResource>().eq(RoleResource::getRoleId, roleId));
        return roleResources.parallelStream()
                .map(roleResource -> resourceMapper.selectOne(new LambdaQueryWrapper<Resource>().eq(Resource::getId, roleResource.getSourceId())))
                .collect(Collectors.toList());
    }
}
