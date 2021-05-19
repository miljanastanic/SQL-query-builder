package gui.table;

import javax.swing.*;
import java.awt.*;

public class RunToolBar extends JToolBar {
    public RunToolBar() {
        super(SwingConstants.VERTICAL);
        setFloatable(true);
        setBackground(Color.WHITE);
        add(MainFrame.getInstance().getActionManager().getRunButton());
    }
}
