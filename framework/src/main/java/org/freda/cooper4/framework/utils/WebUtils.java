package org.freda.cooper4.framework.utils;

import org.freda.cooper4.framework.datastructure.Dto;
import org.freda.cooper4.framework.datastructure.impl.BaseDto;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * WEB Utils
 *
 * Created by rally on 2016/12/4.
 */
public class WebUtils
{
    /**
     *
     * 获取Session中的参数
     *
     * @param request
     * @param sessionKey
     * @return Object
     */
    public static Object getSessionAttribute(HttpServletRequest request, String sessionKey)
    {
        Object objSessionAttribute = null;

        HttpSession session = request.getSession(false);

        if (session != null)
        {
            objSessionAttribute = session.getAttribute(sessionKey);
        }
        return objSessionAttribute;
    }

    /**
     *
     * 设置Session中的参数
     *
     * @param request
     * @param sessionKey
     * @param objSessionAttribute
     */
    public static void setSessionAttribute(HttpServletRequest request,String sessionKey, Object objSessionAttribute)
    {
        HttpSession session = request.getSession();

        if (session != null)
        {
            session.setAttribute(sessionKey, objSessionAttribute);
        }
    }

    /**
     * 移除Session对象属性值
     *
     * @param request
     * @param sessionKey
     * @return
     */
    public static void removeSessionAttribute(HttpServletRequest request,String sessionKey)
    {
        HttpSession session = request.getSession();

        if (session != null)
        {
            session.removeAttribute(sessionKey);
        }
    }

    /**
     * 将请求参数封装为Dto
     *
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Dto getParamsAsDto(HttpServletRequest request)
    {
        Dto dto = new BaseDto();
        Map map = request.getParameterMap();
        Iterator keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext())
        {
            String key = (String) keyIterator.next();
            String value = ((String[]) (map.get(key)))[0];
            dto.put(key, value);
        }
        return dto;
    }
    /**
     *
     * 获取IP地址
     *
     * @param httpServletRequest
     * @return
     */
    public static String getIpAddr(ServletRequest httpServletRequest)
    {
        HttpServletRequest request=(HttpServletRequest)httpServletRequest;

        String ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     *
     * 获取上下文
     *
     * @param request
     */
    public static ServletContext getApplicationContext(HttpServletRequest request)
    {
        return request.getSession().getServletContext();
    }

    /**
     * 获取指定cookie的值..
     *
     * @param request request
     * @param cookieName cookie name
     * @return
     */
    public static String getCookie(HttpServletRequest request,String cookieName)
    {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(cookieName))
            {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 删除所有COOKIE
     *
     * @param request
     * @param response
     */
    public static void cleanAllCookies(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies)
        {
            if (cookie != null)
            {
                cookie.setMaxAge(0);

                response.addCookie(cookie);
            }
        }

    }
}
