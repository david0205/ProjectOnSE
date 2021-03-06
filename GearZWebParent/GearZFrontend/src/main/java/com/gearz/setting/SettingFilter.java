package com.gearz.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.gearz.common.entity.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingFilter implements Filter {

    @Autowired
    private SettingService service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURI().toString();
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".jpg") || url.endsWith(".png")
                || url.endsWith(".svg") || url.endsWith(".woff")) {
            chain.doFilter(request, response);
            return;
        }
        List<Setting> generalSettings = service.getGeneralSettings();
        generalSettings.forEach(setting -> {
            request.setAttribute(setting.getKey(), setting.getValue());
        });

        chain.doFilter(request, response);
    }

}
