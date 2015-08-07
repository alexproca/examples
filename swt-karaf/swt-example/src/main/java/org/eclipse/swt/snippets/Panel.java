package org.eclipse.swt.snippets;

/**
 * Created by alex on 29.05.2014.
 */
interface Panel {
    public void draw(); // draws the Panel
    public String getNarrative(); // returns a description of the Panel
}


// implementation of a simple Panel with no scrollbars
class SimplePanel implements Panel {
    public void draw() {
        // draw panel
    }

    public String getNarrative() {
        return "simple panel";
    }
}
abstract class PanelImpl implements Panel {
    protected Panel xPanel;

    public PanelImpl (Panel xPanel) {
        this.xPanel = xPanel;
    }
}

class VertScrollBarImpl extends PanelImpl {
    public VertScrollBarImpl (Panel xPanel) {
        super(xPanel);
    }

    public void draw() {
        drawVertScrollBar();
        xPanel.draw();
    }
    private void drawVertScrollBar() {
        // draw the vertical scrollbar
    }

    public String getNarrative() {
        return xPanel.getNarrative() + ", including vert scrollbars";
    }
}
