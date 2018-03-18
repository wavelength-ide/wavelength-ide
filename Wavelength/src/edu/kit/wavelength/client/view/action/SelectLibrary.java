package edu.kit.wavelength.client.view.action;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.gwtbootstrap3.client.ui.CheckBox;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.view.App;

/**
 * Includes the selected library. This only affects newly started executions.
 */
public class SelectLibrary implements Action {

	private static App app = App.get();
	
	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}
	
	@Override
	public void run() {
		List<Library> libraries = app.libraryCheckBoxes().stream().filter(CheckBox::getValue)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getText().equals(l.getName())))
				.collect(Collectors.toList());
		app.editor().setLibraries(libraries);
	}
	
}
