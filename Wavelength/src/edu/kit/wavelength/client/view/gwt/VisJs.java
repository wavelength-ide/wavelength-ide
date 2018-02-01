package edu.kit.wavelength.client.view.gwt;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

public class VisJs {

	private JavaScriptObject treeOutput;

	public static native void loadNetwork(String nodes, String edges)/*-{
		// create a network
		var idParent = "network";
		var parent = $doc.getElementById("network");
		var data = {
			nodes : eval(nodes),
			edges : eval(edges)
		};
		var options = {
  			interaction: {
    					dragNodes: false,
    					dragView: false}
			};

		var network = new $wnd.vis.Network(parent, data, options);
	}-*/;

	public static native VisJs load(Panel parent) /*-{
		var idParent = "network";
		var parent = $doc.getElementById("network");		
		var data = {};
		var options = {};
		return new $wnd.vis.Network(parent, data, options);
	}-*/;

}
