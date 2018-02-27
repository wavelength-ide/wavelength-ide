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
			width: (parent.offsetWidth - 25) + "px",
			height: (parent.offsetHeight - 25) + "px",
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


}
