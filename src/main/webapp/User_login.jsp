<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="Resources/css/style.css">

<title>Login</title>
</head>

<body>
	<f:view>
		<img src="Resources/img/VITRINE.jpg" alt="" class="img-bk">
		<div class="card">
			<h:form>
				<div class="side form">
					<div class="img-logo">
						<img alt="You System Name" src="Resources/img/RayBanVS2.png">
					</div>

					<h:outputText value="Identifiant : " styleClass="control-label"></h:outputText>
					<h:inputText value="#{User.name}" styleClass="form-control"></h:inputText>

					<h:outputText value="Mot de passe : " styleClass="control-label"></h:outputText>
					<h:inputSecret value="#{User.password}" styleClass="form-control"></h:inputSecret>

					<h:commandButton action="#{User.login}" value="Se connecter"
						styleClass="btn-submit"></h:commandButton>
<!-- 					<span class="text-muted"> Mot de passe oublié ? <a href="#">Cliquer -->
<!-- 							ici !</a> -->
<!-- 					</span> -->
				</div>
			</h:form>
			<div class="side picture">
				<img src="Resources/img/raybanX5.png" alt="">
			</div>
		</div>
	</f:view>
</body>
</html>