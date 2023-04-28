package com.yhao.webdemo.service;

import com.yhao.webdemo.dao.model.Resource;

import java.util.List;

public interface IResourceService {

    List<Resource> getResourceByRole(Integer roleId);
}
