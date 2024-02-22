package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import org.apache.commons.io.IOUtils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@MultipartConfig
@WebServlet(urlPatterns = {"/UsuarioController"})
public class ServletUsuarioController extends servletGenercUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {	
			
			 String acao  = request.getParameter("acao");
			 
			 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				 
				 String idUser = request.getParameter("id");
				 
				 daoUsuarioRepository.deletarUser(idUser);
				 
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 request.setAttribute("modelLogins", modelLogins);
				 
				 request.setAttribute("msg", "Excluido com sucesso!");
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
				 
			 }
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					 
					 String idUser = request.getParameter("id");
					 
					 daoUsuarioRepository.deletarUser(idUser);
					 
					 response.getWriter().write("Excluido com sucesso!!");
			 }
			 
			 
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				 
				 String nomeBusca = request.getParameter("nomeBusca");
				 
				 List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				 
				 ObjectMapper mapper = new ObjectMapper();
				 
				 String json = mapper.writeValueAsString(dadosJsonUser);
				 
				 response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				 
				 response.getWriter().write(json);
				 
			 }
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				 String id = request.getParameter("id");
				 
				 ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
				 
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 request.setAttribute("modelLogins", modelLogins);
				 
				 request.setAttribute("msg", "Usuario em edicao");
				 request.setAttribute("modolLogin", modelLogin);  // SETA O OBJETO
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response); // REDIRECIONA
				 
				 
			 }
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				 
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 
				 request.setAttribute("msg", "Usuarios carregados");
				 request.setAttribute("modelLogins", modelLogins);  // SETA O OBJETO
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response); // REDIRECIONA
				 
			 }
			  
			 else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")){
				 
				 String idUser = request.getParameter("id");
				 
				 ModelLogin modelLogin = daoUsuarioRepository.consultaUsuario(idUser, super.getUserLogado(request));
				 if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					 
					 response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaoFotouser());
					 response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
					 
				 }
				 
				 else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
					 Integer offset = Integer.parseInt(request.getParameter("pagina"));
					 
					 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
					 
					 request.setAttribute("modelLogins", modelLogins);
					 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
					 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 }
				 
			 }
			 else {
				 
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 
				 request.setAttribute("modelLogins", modelLogins);
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
			 }
			 
			 
			 
			 
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}

		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String msg = "OPERACAO ** doPost servletUsuarioController ** REALIZADA COM SUCESSO" ;

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String numero = request.getParameter("numero");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String dataNascimento = request.getParameter("dataNascimento");
			String rendamensal = request.getParameter("rendamensal");
			
			rendamensal = rendamensal.split("\\ ")[0].replaceAll("\\.", "").replaceAll("\\,", ".");

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setNumero(numero);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
			modelLogin.setRendamensal(Double.valueOf(rendamensal));
			
			
			if (ServletFileUpload.isMultipartContent(request)) {
				
				Part part = request.getPart("fileFoto");  // PEGA FOTO DA TELA
				
				if (part.getSize() > 0) {
				byte[] foto = IOUtils.toByteArray(part.getInputStream()); // CONVERTE IMAGEM PARA BYTE
				String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);  // CONVERTE PARA STRING
				
				modelLogin.setFotouser(imagemBase64);
				modelLogin.setExtensaoFotouser(part.getContentType().split("\\/")[1]);
				}
			}
			
			if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg =" Ja existe usuario com mesmo login, informe outro login";
			}else { 
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso";
				}else {
					msg = "Atualizado com sucesso";
				}
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
				
			}

			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modolLogin", modelLogin);
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
}
