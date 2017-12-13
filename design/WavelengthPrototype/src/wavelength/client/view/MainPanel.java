package wavelength.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

public class MainPanel extends Composite {

	public MainPanel() {
		DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.EM);
		
		Button options = new Button("Options");
		mainPanel.addNorth(options, 2);
		
		HorizontalPanel footerPanel = new HorizontalPanel();
		mainPanel.addSouth(footerPanel, 2);
		
		SplitLayoutPanel ioPanel = new SplitLayoutPanel();
		mainPanel.add(ioPanel);
		
		DockLayoutPanel inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 200);
		
		TextArea outputArea = new TextArea();
		outputArea.setWidth("100%");
		ioPanel.add(outputArea);
		
		DockLayoutPanel inputControlPanel = new DockLayoutPanel(Unit.PCT);
		inputPanel.addSouth(inputControlPanel, 1.7);
		
		TextArea inputArea = new TextArea();
		inputArea.setWidth("100%");
		inputArea.setHeight("100%");
		inputPanel.add(inputArea);
		
		HorizontalPanel optionBarPanel = new HorizontalPanel();
		inputControlPanel.addWest(optionBarPanel, 50);
		
		ListBox displayBox = new ListBox();
		displayBox.addItem("Display");
		optionBarPanel.add(displayBox);
		
		ListBox reductionBox = new ListBox();
		reductionBox.addItem("Reduction");
		optionBarPanel.add(reductionBox);
		
		ListBox outputBox = new ListBox();
		outputBox.addItem("Output");
		optionBarPanel.add(outputBox);
		
		HorizontalPanel controlWrapperPanel = new HorizontalPanel();
		controlWrapperPanel.setWidth("100%");
		controlWrapperPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		inputControlPanel.addEast(controlWrapperPanel, 50);
		
		HorizontalPanel controlPanel = new HorizontalPanel();
		controlWrapperPanel.add(controlPanel);
		
		Button backwardsButton = new Button("b");
		controlPanel.add(backwardsButton);
		
		Button stepByStepButton = new Button("sbs");
		controlPanel.add(stepByStepButton);
		
		Button forwardButton = new Button("f");
		controlPanel.add(forwardButton);
		
		Button cancelButton = new Button("c");
		controlPanel.add(cancelButton);
		
		Button runButton = new Button("r");
		controlPanel.add(runButton);

		Button exportButton = new Button("e");
		footerPanel.add(exportButton);
		
		Button shareButton = new Button("s");
		footerPanel.add(shareButton);
		
		initWidget(mainPanel);
	}
	
}
