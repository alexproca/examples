package ro.anproca.example;

import java.awt.SplashScreen;

public class MainClass {

	public static void main(String[] args) throws InterruptedException {
		final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        GroovyClass.sayHi("John Doe");
        Thread.sleep(10000);
        splash.close();
	}
	
}
