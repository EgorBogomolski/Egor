package package1;

import javax.swing.*;
import java.awt.event.*;

public class TableAddDialog extends JDialog {

    public TableAddDialog(final JFrame owner, final PeripheralTreeModel treeModel) {
        super(owner, "Add item", true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        final JLabel nameLabel = new JLabel("Name");
        final JTextField nameField = new JTextField();

        final JLabel brandLabel = new JLabel("Brand");
        final JComboBox<String> brandsBox = new JComboBox<>();
        for (final String brand : Peripheral.BRANDS_LIST) {
            brandsBox.addItem(brand);
        }

        final JLabel typeLabel = new JLabel("Type");
        final JComboBox<String> typeBox = new JComboBox<>();
        for (final String category : PeripheralTreeModel.TABLES_NAMES) {
            typeBox.addItem(category);
        }

        final JLabel costLabel = new JLabel("Cost");
        final JSpinner costSpinner = new JSpinner();

        final JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String name = nameField.getText();
                final String brand = String.valueOf(brandsBox.getSelectedItem());
                int type = 0;
                for (int i = 0; i < PeripheralTreeModel.TABLES_NAMES.length; ++i) {
                    if (PeripheralTreeModel.TABLES_NAMES[i].equals(String.valueOf(typeBox.getSelectedItem()))) {
                        type = i;
                        break;
                    }
                }
                final int cost = (int) costSpinner.getValue();
                treeModel.peripheralTables[type].add(new Peripheral(name, brand, cost));
            }
        });

        final KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                //not needed
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                final int i = e.getKeyCode();
                if (i == KeyEvent.VK_ENTER) {
                    addButton.doClick();
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                //not needed
            }
        };
        nameField.addKeyListener(keyListener);
        costSpinner.addKeyListener(keyListener);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(brandLabel);
        panel.add(brandsBox);
        panel.add(typeLabel);
        panel.add(typeBox);
        panel.add(costLabel);
        panel.add(costSpinner);
        panel.add(addButton);

        setContentPane(panel);
        pack();
        setVisible(true);
    }
}
