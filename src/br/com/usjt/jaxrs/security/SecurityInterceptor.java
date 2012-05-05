package br.com.usjt.jaxrs.security;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jboss.resteasy.annotations.interception.Precedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.shiro.Security;
import br.com.usjt.shiro.SecurityShiro;

/**
 * Controlador de seguranca para o site. Padrão é bloquear.
 */
@Provider
@ServerInterceptor
@Precedence("SECURITY")
public class SecurityInterceptor implements PreProcessInterceptor {

	private static Logger LOG = LoggerFactory
			.getLogger(SecurityInterceptor.class);

	@Override
	public ServerResponse preProcess(HttpRequest r, ResourceMethod m)
			throws Failure, WebApplicationException {
		if (m.getMethod().isAnnotationPresent(SecurityPublic.class)) {
			return null;
		}
		Security security = SecurityShiro.init();
		Subject currentUser = SecurityUtils.getSubject();
		if (!security.isAuthenticated()) {
			throw new Failure("Sem autorização",
					HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			if (m.getMethod().isAnnotationPresent(RequiresRoles.class)) {
				List<String> roles = Arrays.asList(m.getMethod()
						.getAnnotation(RequiresRoles.class).value());
				if (!currentUser.hasAllRoles(roles)) {
					throw new Failure("Sem autorização",
							HttpServletResponse.SC_UNAUTHORIZED);
				}
			}

			try {
				Session session = currentUser.getSession(false);
				session.touch();
			} catch (InvalidSessionException e) {
				currentUser.logout();
				throw new Failure("Sessão Expirada",
						HttpServletResponse.SC_GATEWAY_TIMEOUT);
			}

			String sec = "";
			try {
				sec = m.getMethod().getAnnotation(SecurityPrivate.class)
						.toString();
			} catch (Exception e) {
			}
			SecurityInterceptor.LOG
					.error("SecurityInterceptor.preProcess: httpReq=["
							+ r.getUri().getPath() + "], resMethod.name=["
							+ m.getClass().getCanonicalName() + "], value=["
							+ sec + "].");
			throw new Failure("Sem autorização",
					HttpServletResponse.SC_UNAUTHORIZED);
		}// preProcess
	}
}// class
