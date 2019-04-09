package package1;

import javax.swing.*;
import java.awt.event.*;

public final class Main {
    private static final int WINDOW_HEIGHT = 500, WINDOW_WIDTH = 400;

    private Main() {}

    public static void main(final String[] args) {
        final JFrame frame = new JFrame();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final PeripheralTreeModel treeModel = new PeripheralTreeModel();
        final JTree tree = new JTree(treeModel);
        tree.setCellRenderer(new PeripheralTreeCellRenderer());

        final JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        final JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem addItem = new JMenuItem("Add");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new TableAddDialog(frame, treeModel);
                tree.setCellRenderer(new PeripheralTreeCellRenderer());
            }
        });
        final JMenu sortMenu = new JMenu("Sort");
        final JMenuItem nameItem = new JMenuItem("By name");
        nameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                treeModel.sortItems(PeripheralTableModel.BY_NAME);
                tree.setCellRenderer(new PeripheralTreeCellRenderer());
            }
        });
        final JMenuItem brandItem = new JMenuItem("By brand");
        brandItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                treeModel.sortItems(PeripheralTableModel.BY_BRAND);
                tree.setCellRenderer(new PeripheralTreeCellRenderer());
            }
        });
        final JMenuItem costItem = new JMenuItem("By cost");
        costItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                treeModel.sortItems(PeripheralTableModel.BY_COST);
                tree.setCellRenderer(new PeripheralTreeCellRenderer());
            }
        });

        fileMenu.add(addItem);
        sortMenu.add(nameItem);
        sortMenu.add(brandItem);
        sortMenu.add(costItem);
        menuBar.add(fileMenu);
        menuBar.add(sortMenu);

        frame.setContentPane(scrollPane);
        frame.setJMenuBar(menuBar);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
