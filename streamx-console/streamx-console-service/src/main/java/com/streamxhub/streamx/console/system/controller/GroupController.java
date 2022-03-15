package com.streamxhub.streamx.console.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.streamxhub.streamx.console.base.domain.RestRequest;
import com.streamxhub.streamx.console.base.domain.RestResponse;
import com.streamxhub.streamx.console.system.entity.Group;
import com.streamxhub.streamx.console.system.entity.Role;
import com.streamxhub.streamx.console.system.service.GroupService;
import com.streamxhub.streamx.console.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daixinyu
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("list")
    @RequiresPermissions("group:view")
    public RestResponse roleList(RestRequest restRequest, Group group) {
        IPage<Group> groupList = groupService.findGroups(group, restRequest);
        return RestResponse.create().data(groupList);
    }
}
