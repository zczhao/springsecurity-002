package zzc.springcloud.order.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzc.springcloud.order.entity.Result;
import zzc.springcloud.order.entity.StatusCode;

/**
 * 	无效token
 *
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
        	PrintWriter out = response.getWriter();
            if(cause instanceof InvalidTokenException) {
               out.write(JSON.toJSONString(Result.error(StatusCode.ERROR, "token 格式非法或已失效")));
            } else{
            	out.write(JSON.toJSONString(Result.error(StatusCode.ERROR, "token 缺失")));
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
