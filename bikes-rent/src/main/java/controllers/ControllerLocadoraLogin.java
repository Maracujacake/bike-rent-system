package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LocadoraLoginDAO;
import domain.Locadora;

// confere as informações de login da locadora provenientes do loginLocadora.jsp
@WebServlet("/locadoraLogin")
public class ControllerLocadoraLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LocadoraLoginDAO dao;

    @Override
    public void init() {
        dao = new LocadoraLoginDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            Locadora locadora = dao.authLocadora(email, password);
            if (locadora != null) {
                HttpSession session = request.getSession();
                session.setAttribute("email", locadora.getEmail());
                session.setAttribute("cnpj", locadora.getCnpj());
                session.setAttribute("nome", locadora.getNome());
                session.setAttribute("senha", locadora.getSenha());
                session.setAttribute("role", "locadora");
                String redirectTo = (String) session.getAttribute("redirectTo");
                System.out.println(redirectTo + "   " + request.getContextPath());
                if (redirectTo != null) {
                    session.removeAttribute("redirectTo");
                    response.sendRedirect(redirectTo);
                } else {
                    response.sendRedirect(request.getContextPath() + "/locadoraLogged/");
                }
            } else {
                String errorMessage = URLEncoder.encode("Senha ou email invalidos!", StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/login/loginLocadora.jsp?error="+errorMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
