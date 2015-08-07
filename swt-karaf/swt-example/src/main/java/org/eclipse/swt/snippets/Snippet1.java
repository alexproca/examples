/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component(immediate = true)
public class Snippet1 {

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        Snippet1 test = new Snippet1();
        test.activate(null);

        System.out.println("vasile");

        TimeUnit.SECONDS.sleep(10);

        test.deactivate();

        System.out.println("vasile");

        TimeUnit.SECONDS.sleep(10);

        System.out.println("vasile");
    }

    public Thread getUiThread() {

        if (uiThread == null) {
            uiThread = new Thread() {
                @Override
                public void run() {
                    Display display = new Display();

                    Shell shell = new Shell(display);
                    shell.open();
                    while (!shell.isDisposed() && isRunning()) {
                        if (!display.readAndDispatch()) display.sleep();
                    }
                    display.dispose();

                }
            };
        }

        return uiThread;
    }

    public void setUiThread(Thread uiThread) {
        this.uiThread = uiThread;
    }

    private Thread uiThread;

    @Activate
    public void activate(Map<String, Object> properties) {
        getUiThread().start();
    }

    @Deactivate
    public void deactivate() {
        setRunning(false);
    }
}