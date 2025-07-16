package ink.chyk.worldstation.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.util.matcher.RequestMatcher

class PageRequestMatcher(val pagesAllowed: Int): RequestMatcher {
    override fun matches(request: HttpServletRequest?): Boolean {
        val page = request?.getParameter("page")?.toIntOrNull()
        return page == null || page < pagesAllowed  // 允许游客查看前两页
    }
}
