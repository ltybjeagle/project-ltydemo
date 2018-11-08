package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.DemoUserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DemoUserRestful {

    @Autowired
    private DemoUserRepository demoUserRepository;

    @GetMapping("{id}")
    public Fasp_T_Causer findById(@PathVariable String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Collection<? extends GrantedAuthority> collection = user.getAuthorities();
            for (GrantedAuthority c : collection) {
                System.out.println("username:" + user.getUsername() + "、role:" + c.getAuthority());
            }
        } else {
            System.out.println("not login");
        }
        System.out.println("======================服务端口：8001");
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
