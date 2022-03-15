/*
 * Copyright (c) 2019 The StreamX Project
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.streamxhub.streamx.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.streamxhub.streamx.console.base.domain.Constant;
import com.streamxhub.streamx.console.base.domain.RestRequest;
import com.streamxhub.streamx.console.base.domain.router.RouterTree;
import com.streamxhub.streamx.console.base.exception.ServiceException;
import com.streamxhub.streamx.console.base.util.CommonUtils;
import com.streamxhub.streamx.console.base.util.SortUtils;
import com.streamxhub.streamx.console.base.util.TreeUtils;
import com.streamxhub.streamx.console.core.dao.SavePointMapper;
import com.streamxhub.streamx.console.core.entity.Application;
import com.streamxhub.streamx.console.core.entity.FlinkEnv;
import com.streamxhub.streamx.console.core.entity.SavePoint;
import com.streamxhub.streamx.console.core.enums.CheckPointType;
import com.streamxhub.streamx.console.core.service.FlinkEnvService;
import com.streamxhub.streamx.console.core.service.SavePointService;
import com.streamxhub.streamx.console.system.dao.GroupMapper;
import com.streamxhub.streamx.console.system.entity.Group;
import com.streamxhub.streamx.console.system.entity.Menu;
import com.streamxhub.streamx.console.system.entity.Role;
import com.streamxhub.streamx.console.system.entity.User;
import com.streamxhub.streamx.console.system.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author benjobs
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {


    @Override
    public IPage<Group> findGroups(Group group, RestRequest request) {
        Page<Group> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(group.getGroupName()),"group_name",group.getGroupName());
        IPage<Group> resPage = this.baseMapper.selectPage(page, queryWrapper);
        return resPage;
    }
}
