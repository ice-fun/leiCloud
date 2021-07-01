package com.leiran.security.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.security.mapper.LoginLogMapper;
import org.springframework.stereotype.Service;


@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {
    public void saveSuccessLog(String userId, String username, String path, String result, String ipAddress, String param, String point) {

        LoginLog loginLog = new LoginLog();
        loginLog.setLoginPath(path);
        loginLog.setLoginResult(result);
        loginLog.setIp(ipAddress);
        loginLog.setParam(param);
        loginLog.setLogPoint(point);
        loginLog.setUserId(userId);
        loginLog.setUsername(username);
        save(loginLog);
    }

}