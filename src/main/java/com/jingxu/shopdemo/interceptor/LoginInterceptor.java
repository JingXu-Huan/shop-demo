package com.jingxu.shopdemo.interceptor;

import com.jingxu.shopdemo.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            response.getWriter().write("请先登录");
            return false;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("请先登录");
            return false;
        }

        // 保存用户 ID 到 ThreadLocal
        UserContext.set(userId);

//        // 可选：检查登录状态
//        Integer count = jdbcTemplate.queryForObject(
//                "select count(*) from user_signin where userId = ?", Integer.class, userId);
//        if (count == null || count == 0) {
//            // 首次登录或未签到逻辑
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.remove();
    }
}
