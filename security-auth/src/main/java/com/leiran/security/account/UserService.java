package com.leiran.security.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.enums.RoleEnum;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.mapper.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> implements AuthUserDetailsService {

    @Override
    public AuthUserDetails loadUserByUsername(String s) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUserAccount, s));
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return user;
    }

    @Override
    public AuthUserDetails loadUserByAccount(String account) {
        return loadUserByUsername(account);
    }

    @Override
    public AuthUserDetails loadUserByPhoneNumber(String phoneNumber) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone, phoneNumber));
        if (user == null) {
            return createUser(null, null, phoneNumber);
        }
        return user;
    }

    @Override
    public AuthUserDetails loadUserByMiniOpenId(String unionId, String openId, WechatResponse decrypt) throws UsernameNotFoundException {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getMiniOpenId, openId));
        // 如此微信未登录过
        if (user == null) {
            String phone = null;
            if (decrypt != null) {
                phone = decrypt.getPurePhoneNumber();
                user = getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone, phone));
                if (user != null) {
                    user.setMiniOpenId(openId);
                    user.setWechatUnionId(unionId);
                    return user;
                }
            }
            user = createUser(unionId, openId, phone);
        }
        // 如果原先登录的手机号码和当前的不一致，如：10086 现在是10000
        if (decrypt != null && user.getUserId() != null && !user.getUserPhone().equals(decrypt.getPurePhoneNumber())) {
            // 原来的手机账号 清除微信相关信息
            user.setMiniOpenId(null);
            user.setWechatUnionId(null);
            user.setOfficialOpenId(null);
            user.setSubscribed(false);
            user.setTokenVersion(user.getTokenVersion() == 0 ? 1 : 0);
            updateById(user);
            // 根据当前手机号码查询
            user = getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone, decrypt.getPurePhoneNumber()));
            if (user == null) {
                user = createUser(unionId, openId, decrypt.getPurePhoneNumber());
            }
        }
        user.setMiniOpenId(openId);
        user.setWechatUnionId(unionId);
        return user;
    }

    @Override
    public AuthUserDetails loadUserByOfficialOpenId(String unionId, String openId) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getOfficialOpenId, openId));
        if (user == null) {
            user = createUser(unionId, openId, "");
        }
        return user;
    }

    @Override
    public AuthUserDetails loadUserByUnionId(String unionId, String openId) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getWechatUnionId, unionId));
    }

    public AuthUserDetails loadUserById(String id) {
        return getById(id);
    }

    private User createUser(String unionId, String openId, String phone) {
        User user = new User();
        user.setRole(RoleEnum.ROLE_USER.name());
        user.setLocked(false);
        user.setTokenVersion(0);
        user.setWechatUnionId(unionId);
        user.setMiniOpenId(openId);
        user.setUserPhone(phone);
        return user;
    }

    public User getByPhone(String phoneNumber) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone, phoneNumber));
    }
}
