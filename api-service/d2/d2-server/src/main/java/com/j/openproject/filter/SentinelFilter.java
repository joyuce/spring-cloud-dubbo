package com.j.openproject.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.adapter.servlet.util.FilterUtil;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.j.openproject.code.CommonRsCode;
import com.j.openproject.utils.HttpRequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type SentinelFilter
 * @Desc
 * @date 2020年03月06日
 * @Version V1.0
 */
@Slf4j
@Order(1)
@Component
@WebFilter(urlPatterns = "/*", filterName = "sentinelFilter")
public class SentinelFilter implements Filter {
    private static final String HTTP_METHOD_SPECIFY = "HTTP_METHOD_SPECIFY";
    private static final String COLON = ":";
    private boolean httpMethodSpecify = false;
    private static final String EMPTY_ORIGIN = "";

    public SentinelFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.httpMethodSpecify = Boolean.parseBoolean(filterConfig.getInitParameter("HTTP_METHOD_SPECIFY"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest sRequest = (HttpServletRequest) request;
        Entry urlEntry = null;

        try {
            String target = FilterUtil.filterTarget(sRequest);
            UrlCleaner urlCleaner = WebCallbackManager.getUrlCleaner();
            if (urlCleaner != null) {
                target = urlCleaner.clean(target);
            }

            if (!StringUtil.isEmpty(target)) {
                String origin = this.parseOrigin(sRequest);
                ContextUtil.enter("sentinel_web_servlet_context", origin);
                if (this.httpMethodSpecify) {
                    String pathWithHttpMethod = sRequest.getMethod().toUpperCase() + ":" + target;
                    urlEntry = SphU.entry(pathWithHttpMethod, 1, EntryType.IN);
                } else {
                    urlEntry = SphU.entry(target, 1, EntryType.IN);
                }
            }

            chain.doFilter(request, response);
        } catch (BlockException var14) {
            HttpServletResponse sResponse = (HttpServletResponse) response;
            HttpRequestUtil.setResponse(sResponse, CommonRsCode.INT_LIMIT);
        } catch (ServletException | RuntimeException | IOException var15) {
            Tracer.traceEntry(var15, urlEntry);
            throw var15;
        } finally {
            if (urlEntry != null) {
                urlEntry.exit();
            }

            ContextUtil.exit();
        }

    }

    private String parseOrigin(HttpServletRequest request) {
        RequestOriginParser originParser = WebCallbackManager.getRequestOriginParser();
        String origin = "";
        if (originParser != null) {
            origin = originParser.parseOrigin(request);
            if (StringUtil.isEmpty(origin)) {
                return "";
            }
        }

        return origin;
    }

    @Override
    public void destroy() {
    }
}
