package com.streamxhub.streamx.console.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.streamxhub.streamx.console.base.domain.RestRequest;
import com.streamxhub.streamx.console.base.domain.RestResponse;
import com.streamxhub.streamx.console.system.entity.Team;
import com.streamxhub.streamx.console.system.entity.User;
import com.streamxhub.streamx.console.system.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author daixinyu
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("list")
    @RequiresPermissions("team:view")
    public RestResponse roleList(RestRequest restRequest, Team group) {
        IPage<Team> groupList = teamService.findTeams(group, restRequest);
        return RestResponse.create().data(groupList);
    }

    @PostMapping("post")
    @RequiresPermissions("team:add")
    public RestResponse addTeam(@Valid Team team) throws Exception {
        this.teamService.createTeam(team);
        return RestResponse.create();
    }
}
