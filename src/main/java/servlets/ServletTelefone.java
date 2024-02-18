package servlets;

import java.io.IOException;
import java.util.List;

import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;


@WebServlet("/ServletTelefone")
public class ServletTelefone extends servletGenercUtil {
	
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
       
   
    public ServletTelefone() {
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String iduser = request.getParameter("iduser"); 
		
		
		try {
			if (iduser != null && iduser.isEmpty()) {
		
				
					ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong("iduser"));
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
					
					request.setAttribute(iduser, modelLogin);
					
				
			
			}else {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 
				 request.setAttribute("msg", "telefones carregados");
				 request.setAttribute("modelLogins", modelLogins);  // SETA O OBJETO
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response); // REDIRECIONA
			
			}
				} catch (Exception e) {
				e.printStackTrace();
				}
		
			}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
