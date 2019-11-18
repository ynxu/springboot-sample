package org.light4j.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.light4j.sample.annotation.LogAction;
import org.light4j.sample.bean.Response;
import org.light4j.sample.bean.User;
import org.light4j.sample.exception.RestCode;
import org.light4j.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Api(
        description = "用户模块",
        position = 1,
        basePath = "/api/user",
        tags = "UserController",
        value = "用户模块",
        produces = "api/produces",
        consumes = "api/consumes",
        protocols = "api/protocols"
)
@RestController
@Validated
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    UserService userService;



    @LogAction
    @ApiOperation(value = "获取用户信息", notes = "根据用户id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataTypeClass = Long.class, paramType = "path")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    public Response<User> getUserById(
            @PathVariable(value = "id") @Min(value = 1, message = "id最小值不能 < 1") @Max(value = Long.MAX_VALUE, message = "id最大值超过" + Long.MAX_VALUE) Long id
    ) {
        User user = userService.getUserById(id);
        return new Response(RestCode.SUCCESS, user);
    }


}
