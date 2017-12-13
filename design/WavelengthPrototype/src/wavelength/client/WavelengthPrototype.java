package wavelength.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import wavelength.client.view.MainPanel;

public class WavelengthPrototype implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootLayoutPanel.get().add(new MainPanel());
	}

}
