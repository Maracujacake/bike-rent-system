package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/locadoraLogged/*")
public class FilterLocadora implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("role") == null || !"locadora".equals(session.getAttribute("role"))) {
            System.out.println("Redirecionando para loginLocadora.jsp - Sessão ou papel não encontrado.");
            if (session != null) {
                System.out.println("Atributos de sessão: ");
                System.out.println("Email: " + session.getAttribute("email"));
                System.out.println("CNPJ: " + session.getAttribute("cnpj"));
                System.out.println("Nome: " + session.getAttribute("nome"));
                System.out.println("Role: " + session.getAttribute("role"));
            } else {
                System.out.println("Sessão é nula.");
            }

            session = httpRequest.getSession();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login/loginLocadora.jsp");
        } else {
            System.out.println("Sessão encontrada com papel: " + session.getAttribute("role"));
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }
}
