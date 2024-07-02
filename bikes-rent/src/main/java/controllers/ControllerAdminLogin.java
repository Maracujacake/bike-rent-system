package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;

// confere as informações de login do cliente provenientes do loginCliente.jsp
@WebServlet("/adminLogin")
public class ControllerAdminLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO dao; 


    @Override
    public void init() {
        dao = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
            try {
                if (dao.authAdmin(email, password) != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("role", "admin");

                    String redirectTo = (String) session.getAttribute("redirectTo");
                    if (redirectTo != null) {
                        session.removeAttribute("redirectTo");
                        response.sendRedirect(request.getContextPath() + redirectTo);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login/loginAdmin.jsp");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }
}
