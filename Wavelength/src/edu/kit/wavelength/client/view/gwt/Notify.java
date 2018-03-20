package edu.kit.wavelength.client.view.gwt;

public class Notify {

	public static native void error(String msg) /*-{
		$wnd.jQuery.notify(msg, { position: "t c" });
	}-*/;
	
}