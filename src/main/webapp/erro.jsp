<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TELA QUE MOSTRA OS ERROS</title>
</head>
<body>
	<h1>MENSAGEM DE ERRO, ENTRE EM CONTATO COM A EQUIPE DE SUPORTE DO SISTEMA</h1>
	
	<%
	
	out.print(request.getAttribute("msg"));
	%>
</body>
</html>