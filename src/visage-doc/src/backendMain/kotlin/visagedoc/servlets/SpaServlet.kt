package visagedoc.servlets

import javax.servlet.RequestDispatcher
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaServlet", value = ["*.vsg"])
class SpaServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val rd = servletContext.getRequestDispatcher("/index.jsp")
        rd.forward(req, resp)
    }
}