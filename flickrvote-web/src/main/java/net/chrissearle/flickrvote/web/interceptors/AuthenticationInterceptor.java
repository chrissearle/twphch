package net.chrissearle.flickrvote.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;

import java.util.Map;

public class AuthenticationInterceptor implements Interceptor {
    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map<String, Object> session = actionInvocation.getInvocationContext().getSession();

        PhotographerInfo photographer = (PhotographerInfo) session.get("flickrUser");

        if (photographer == null) {
            return "notloggedin";
        }

        if (!photographer.isAdministrator()) {
            return "notadmin";
        }

        return actionInvocation.invoke();
    }
}
