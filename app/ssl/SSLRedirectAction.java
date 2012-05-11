package ssl;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class SSLRedirectAction extends Action<SSLRedirect> {

	public Result call(Http.Context ctx) throws Throwable {
		Boolean secure = false;
		if (ctx.request().headers().get("X-FORWARDED-PROTO") != null && ctx.request().headers().get("X-FORWARDED-PROTO").length > 0) {
			secure = ctx.request().headers().get("X-FORWARDED-PROTO")[0].contains("https");
		}
		if(!secure && (!configuration.ignoreLocalhost() || !secure && !ctx.request().host().contains("localhost"))) {
			return redirect("https://" + ctx.request().host() + ctx.request().uri());
		}
		return delegate.call(ctx);
	}
	
}
