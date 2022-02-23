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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.streamxhub.streamx.console.system.authentication.ServerComponent;
import com.streamxhub.streamx.console.system.dao.GroupUserMapper;
import com.streamxhub.streamx.console.system.entity.GroupUser;
import com.streamxhub.streamx.console.system.service.GroupUserService;
import com.streamxhub.streamx.console.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benjobs
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {
    @Autowired
    private ServerComponent serverComponent;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<GroupUser> getGroupUserList(Long userId) {

        if (userRoleService.isAdmin(userId)) {
            return new ArrayList<>();
        }

        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "user_id", userId);
        List<GroupUser> list = list(queryWrapper);
        if (null == list || list.size() == 0) {
            throw new IllegalArgumentException("您未加入项目组，请联系管理员：代欣雨");
        }
        return list;
    }


    @Override
    public Long getTopGroupIdByUser(Long userId) {
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "user_id", userId);
        List<GroupUser> list = list(queryWrapper);
        Boolean isAdmin = userRoleService.isAdmin(userId);
        if (null == list || list.size() <= 0) {
            if (isAdmin) {
                return 0L;
            }
            throw new IllegalArgumentException("您未加入项目组，请联系管理员：代欣雨");
        }
        return list.get(0).getGroupId();
    }


    @Override
    public List<GroupUser> getGroupUserList() {
        Long userId = serverComponent.getUser().getUserId();
        return getGroupUserList(userId);
    }

    @Override
    public List<Long> getGroupIdList() {
        Long userId = serverComponent.getUser().getUserId();
        return getGroupIdList(userId);
    }

    @Override
    public List<Long> getGroupIdList(Long userId) {
        List<GroupUser> groupIdList = getGroupUserList(userId);
        return groupIdList.stream().map(groupUser -> groupUser.getGroupId()).collect(Collectors.toList());
    }
}
