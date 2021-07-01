package com.leiran.api.config;

import com.leiran.common.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author：lei ran
 */

@RestController
public class HystrixFallBackController {

    @RequestMapping("/fallback")
    public Result fallback() {
        return Result.failure("服务正忙，请稍后再试...");
    }
}
