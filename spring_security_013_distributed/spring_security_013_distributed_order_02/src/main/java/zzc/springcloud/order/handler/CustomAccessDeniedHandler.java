package zzc.springcloud.order.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzc.springcloud.order.entity.Result;
import zzc.springcloud.order.entity.StatusCode;

/**
 * 	权限不足
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
        	PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString(Result.error(StatusCode.ERROR, "没有权限访问")));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
