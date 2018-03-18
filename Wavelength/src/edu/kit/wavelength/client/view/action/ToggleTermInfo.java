package edu.kit.wavelength.client.view.action;

import com.google.gwt.user.client.ui.FlowPanel;

import edu.kit.wavelength.client.view.App;

public class ToggleTermInfo implements Action {
	
	private static App app = App.get();
	
	private FlowPanel infoDiv;
	
	public ToggleTermInfo(FlowPanel infoDiv) {
		this.infoDiv = infoDiv;
	}
	
	@Override
	public void run() {
		app.libraryTermInfos().stream().filter(i -> i != infoDiv).forEach(i -> {
			i.setStyleName("closedTermInfo", true);
			i.setStyleName("openedTermInfo", false);
		});
		
		boolean isClosed = infoDiv.getStyleName().contains("closedTermInfo");
		infoDiv.setStyleName("closedTermInfo", !isClosed);
		infoDiv.setStyleName("openedTermInfo", isClosed);
	}

}
