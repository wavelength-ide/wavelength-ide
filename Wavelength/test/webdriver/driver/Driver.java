package webdriver.driver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.Logs;

public class Driver extends ChromeDriver {
	
	private long oldTime = 0;
	private TimeUnit oldUnit = TimeUnit.SECONDS;
	
	private long currentTime = 0;
	private TimeUnit currentUnit = TimeUnit.SECONDS;
	
	public void removeImplicitTimeout() {
		manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	public void resetImplicitTimeout() {
		manage().timeouts().implicitlyWait(oldTime, oldUnit);
	}
	
	public boolean hasElement(By by) {
		removeImplicitTimeout();
		boolean hasChildren = super.findElements(by).size() > 0;
		resetImplicitTimeout();
		return hasChildren;
	}
	
	private class LocalOptions implements Options {

		Options superOptions;
		
		LocalOptions(Options superOptions) {
			this.superOptions = superOptions;
		}
		
		@Override
		public void addCookie(Cookie cookie) {
			superOptions.addCookie(cookie);
		}

		@Override
		public void deleteAllCookies() {
			superOptions.deleteAllCookies();
		}

		@Override
		public void deleteCookie(Cookie cookie) {
			superOptions.deleteCookie(cookie);
		}

		@Override
		public void deleteCookieNamed(String name) {
			superOptions.deleteCookieNamed(name);
		}

		@Override
		public Cookie getCookieNamed(String name) {
			return superOptions.getCookieNamed(name);
		}

		@Override
		public Set<Cookie> getCookies() {
			return superOptions.getCookies();
		}

		@Override
		public ImeHandler ime() {
			return superOptions.ime();
		}

		@Override
		public Logs logs() {
			return superOptions.logs();
		}

		@Override
		public Timeouts timeouts() {
			return new LocalTimeouts(superOptions.timeouts());
		}

		@Override
		public Window window() {
			return superOptions.window();
		}
		
	}
	
	private class LocalTimeouts implements Timeouts {

		Timeouts superTimeouts;
		
		LocalTimeouts(Timeouts superTimeouts) {
			this.superTimeouts = superTimeouts;
		}
		
		@Override
		public Timeouts implicitlyWait(long time, TimeUnit unit) {
			oldTime = currentTime;
			oldUnit = currentUnit;
			currentTime = time;
			currentUnit = unit;
			return new LocalTimeouts(superTimeouts.implicitlyWait(time, unit));
		}

		@Override
		public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
			return new LocalTimeouts(superTimeouts.pageLoadTimeout(time, unit));
		}

		@Override
		public Timeouts setScriptTimeout(long time, TimeUnit unit) {
			return new LocalTimeouts(superTimeouts.setScriptTimeout(time, unit));
		}
		
	}
	
	public Driver() {
		super();
	}
	
	@Override
	public Options manage() {
		return new LocalOptions(super.manage());
	}
	
}
