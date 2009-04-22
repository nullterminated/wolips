// Generated by the Maven Archetype Plug-in
package ${package}.app;

#if($WonderSupport == "no")
import com.webobjects.appserver.WOSession;
#set( $sessionClass = "WOSession" )
#else
import er.extensions.appserver.ERXSession;
#set( $sessionClass = "ERXSession" )
#end

public class Session extends $sessionClass {
	private static final long serialVersionUID = 1L;

	public Session() {
		super();
	}
}
