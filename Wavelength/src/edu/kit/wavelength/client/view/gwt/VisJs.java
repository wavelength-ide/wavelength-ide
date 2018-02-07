package edu.kit.wavelength.client.view.gwt;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

public class VisJs {

	private JavaScriptObject treeOutput;

	public static native void loadNetwork(String nodes, String edges, Panel parent)/*-{
		// create a network
		var idParent = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var parent = $doc.getElementById(idParent);
		var data = {
			nodes : eval(nodes),
			edges : eval(edges)
		};
		var options = {
			width: '100%',
			height: '100%',
			nodes: {
				color: {
					border: '#333333',
					background: 'white'
				},
				chosen: {
					node: false
				}
			},
			edges: {
				chosen: {
					edge: false
				}
			}, 
			autoResize: true,
			clickToUse: false,
  			interaction: {
    					dragNodes: false,
    					dragView: false,
    					zoomView: false
    			},
    			layout: {
    				hierarchical: {
    					enabled: true,
    					sortMethod: 'directed',
    					levelSeparation: 70
    				}
    			}
			};

		var network = new $wnd.vis.Network(parent, data, options);
	}-*/;

	// TODO: looks like I don't need this
	public static native VisJs load() /*-{
		var idParent = "network";
		var parent = $doc.getElementById(idParent);		
		var data = {};
		var options = {
			clickToUse: false,
  			interaction: {
    					dragNodes: false,
    					dragView: false,
    					zoomView: false},
    			layout: {
    				hierarchical: {
    					enabled: true,
    					sortMethod: 'directed'}}
			};
		return new $wnd.vis.Network(parent, data, options);
	}-*/;

}
