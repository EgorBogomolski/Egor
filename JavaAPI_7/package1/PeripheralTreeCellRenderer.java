package package1;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class PeripheralTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(
            final JTree tree, final Object value,
            final boolean selected, final boolean expanded, final boolean leaf,
            final int row, final boolean hasFocus) {
        if (leaf) {
            final JTable table = new JTable((PeripheralTableModel) value);
            final JPanel panel = new JPanel();
            panel.add(table);
            panel.setVisible(true);
            return panel;
        } else {
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, false, row, hasFocus);
        }
    }
}
