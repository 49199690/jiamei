package cn.nongph.jiamei.admin.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);

    public static String ACCESSTYPE = "accesstype";

    public static int COOKIE_VALID_MILLS = Integer.MAX_VALUE;

    public static void setCookie(HttpServletResponse resp, String domain, String cookieName, String cookieValue, int seconds) {
        if ( !StringUtils.isEmpty(cookieValue)) {
        	try {
                cookieValue = java.net.URLEncoder.encode(cookieValue, "utf-8");
                Cookie cookie = new Cookie(cookieName, cookieValue);
                if( !StringUtils.isEmpty( domain ) )
                	cookie.setDomain(domain);
                cookie.setPath("/");
                cookie.setMaxAge(seconds);
                resp.addCookie(cookie);
            } catch (Exception e) {
                LOGGER.error( "failed setCookie,domain={},cookieName={},cookieValue={},seconds={}", domain, cookieName, cookieValue, seconds);
                LOGGER.error( "Exception:", e );
            }
        }
    }

    public static void setCookieHttpOnly(HttpServletResponse resp,
            String domain, String cookieName, String cookieValue, int seconds) {
        if (cookieValue == null) {
            return;
        }
        try {
            cookieValue = java.net.URLEncoder.encode(cookieValue, "utf-8");
            resp.addHeader("Set-Cookie", cookieName + "=" + cookieValue
                    + ";Path=/;Domain=" + domain + ";Max-Age=" + seconds
                    + ";HTTPOnly");
        } catch (Exception e) {
            // ignore
            LOGGER.error( "failed setCookieHttpOnly,domain={0},cookieName={1},cookieValue={2},seconds={3}",
                    e, domain, cookieName, cookieValue, seconds);
        }

    }

    public static void setCookieSessionExpires(HttpServletResponse resp,
            String domain, String cookieName, String cookieValue) {
        if (cookieValue == null) {
            return;
        }
        try {
            cookieValue = java.net.URLEncoder.encode(cookieValue, "UTF-8");
            // resp.addCookie(Cookie)
            resp.addHeader("Set-Cookie", cookieName + "=" + cookieValue
                    + ";Path=/;Domain=" + domain + ";HTTPOnly");
        } catch (Exception e) {
            LOGGER.error(
                    "failed setCookieSessionExpires,domain={0},cookieName={1},cookieValue={2}",
                    e, domain, cookieName, cookieValue);
        }
    }

    public static void delCookie(HttpServletResponse resp, String domain, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        if( !StringUtils.isEmpty( domain) )
        	cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        if (cookieName == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie sCookie = cookies[i];
                if (cookieName.equals(sCookie.getName())) {
                    String cValue = sCookie.getValue();
                    return cValue == null ? null : cValue;
                }
            }
        }
        return null;

    }

    public static String getDecodedCookie(HttpServletRequest request,
            String cookieName) {
        String cookieValue = getCookie(request, cookieName);
        if (cookieValue != null) {
            try {
                return java.net.URLDecoder.decode(cookieValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return cookieValue;
    }
}
