package com.liuty.maven.rest;

import com.liuty.maven.dto.Fasp_T_Causer;
import com.liuty.maven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestful {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/findbyid/{id}", method = RequestMethod.GET)
    public Fasp_T_Causer findUserById(@PathVariable String id) throws Exception {
        Fasp_T_Causer user = userService.findUserById(id);
        return user;
    }

    @RequestMapping(value = "/user/findbyid/reqh", method = RequestMethod.GET)
    public Fasp_T_Causer findUserByRequestHeader(@RequestHeader String id) throws Exception {
        Fasp_T_Causer user = userService.findUserById(id);
        return user;
    }

    @RequestMapping(value = "/user/modifyoneuser", method = RequestMethod.POST)
    public String modifyOneUser(@RequestBody Fasp_T_Causer user) {
        return "success";
    }
}
