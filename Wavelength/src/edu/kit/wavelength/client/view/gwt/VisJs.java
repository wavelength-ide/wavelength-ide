package edu.kit.wavelength.client.view.gwt;

import com.google.gwt.user.client.ui.Panel;

/**
 * Wrapper class for the java script library vis.js. It is used for pretty
 * printing lambda terms as syntax trees.
 */
public class VisJs {
	/**
	 * Renders a new network graph in the given panel element.
	 * 
	 * @param nodes
	 *            String representation of the nodes
	 * @param edges
	 *            String representation of the edges
	 * @param parent
	 *            The panel to wrap the network in
	 */
	public static native void loadNetwork(String nodes, String edges, Panel parent)/*-{
		var idParent = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var parent = $doc.getElementById(idParent);
		var data = {
			nodes : eval(nodes),
			edges : eval(edges)
		};
		var options = {
			width : (parent.offsetWidth - 25) + "px",
			height : (parent.offsetHeight - 25) + "px",
			nodes : {
				color : {
					border : '#333333',
					background : 'white'
				},
				chosen : {
					node : false
				}
			},
			edges : {
				chosen : {
					edge : false
				}
			},
			autoResize : true,
			clickToUse : false,
			interaction : {
				dragNodes : false,
				dragView : false,
				zoomView : false
			},
			layout : {
				hierarchical : {
					enabled : true,
					sortMethod : 'directed',
					levelSeparation : 70
				}
			}
		};

		var network = new $wnd.vis.Network(parent, data, options);
	}-*/;

}
