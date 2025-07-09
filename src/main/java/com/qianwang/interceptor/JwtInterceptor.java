package com.qianwang.interceptor;

import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.utils.JwtUtil;
import com.qianwang.utils.LoginContext;
import com.qianwang.common.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    public JwtInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeUnauthorized(response, "未提供有效的 Token");
            return false;
        }

        String token = authHeader.substring(7);

        // 检查 Token 是否在黑名单中（即是否已注销）
        Boolean isBlacklisted = redisTemplate.hasKey("logout:" + token);
        if (Boolean.TRUE.equals(isBlacklisted)) {
            writeUnauthorized(response, "Token 已失效（用户已退出）");
            return false;
        }

        // 解析 Token 并获取用户ID
        Long userId = JwtUtil.getClaimsBody(token).get("id", Long.class);
        if (userId == null) {
            writeUnauthorized(response, "无效的 Token");
            return false;
        }

        // 设置当前线程用户ID
        LoginContext.setCurrentUserId(userId);
        return true;
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ResponseResult.errorResult(HttpCodeEnum.TOKEN_ERROR));
        out.write(json);
        out.flush();
        out.close();
    }
}
