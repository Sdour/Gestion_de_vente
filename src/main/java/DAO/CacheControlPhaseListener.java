package DAO;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CacheControlPhaseListener extends javax.faces.lifecycle.LifecycleFactory implements PhaseListener {
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
		try {
			response.sendRedirect("User_login.faces");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lifecycle getLifecycle(String lifecycleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<String> getLifecycleIds() {
		// TODO Auto-generated method stub
		return null;
	}
}