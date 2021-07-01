package com.leiran.security.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.mapper.AdminMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements AuthUserDetailsService {

    @Override
    public AuthUserDetails loadUserByUsername(String s) {
        Admin admin = getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getAdminAccount, s));
        if (admin == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return admin;
    }

    @Override
    public AuthUserDetails loadUserByAccount(String account) {
        return loadUserByUsername(account);
    }

    @Override
    public AuthUserDetails loadUserByPhoneNumber(String phoneNumber) {
        Admin admin = getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getAdminPhone, phoneNumber));
        if (admin == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return admin;
    }

    @Override
    public AuthUserDetails loadUserByMiniOpenId(String unionId, String openId, WechatResponse decrypt) throws UsernameNotFoundException {
        Admin admin = getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getMiniOpenId, openId));
        if (admin == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return admin;
    }

    @Override
    public AuthUserDetails loadUserByOfficialOpenId(String unionId, String openId) {
        Admin admin = getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getOfficialOpenId, openId));
        if (admin == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return admin;
    }

    @Override
    public AuthUserDetails loadUserByUnionId(String unionId, String openId) {
        return getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getWechatUnionId, unionId));
    }

    public AuthUserDetails loadUserById(String id) {
        return getById(id);
    }
}