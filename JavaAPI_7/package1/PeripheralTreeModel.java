package package1;

import java.sql.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class PeripheralTreeModel implements TreeModel {
    public static final String DB_URL = "jdbc:h2:/G:/Java-УП (задания)/JavaAPI_7/db/data";
    public static final String DB_Driver = "org.h2.Driver";
    public static final String[] TABLES_NAMES = new String[]{"input", "output", "storage", "network"};
    private static final String ROOT = "Peripherals";

    private Connection connection;
    public PeripheralTableModel[] peripheralTables = new PeripheralTableModel[TABLES_NAMES.length];

    private List<TreeModelListener> listeners = new ArrayList<>();

    public PeripheralTreeModel() {
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            this.connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to DB");
        for (int i = 0; i < peripheralTables.length; ++i) {
            peripheralTables[i] = new PeripheralTableModel(connection, TABLES_NAMES[i] + "_table");
        }
    }

    public void sortItems(final int by) {
        for (final PeripheralTableModel table : peripheralTables) {
            table.sort(by);
        }
    }

    @Override
    public Object getRoot() {
        return ROOT;
    }

    @Override
    public Object getChild(final Object parent, final int index) {
        if (parent.equals(ROOT)) {
            return TABLES_NAMES[index];
        }
        for (int i = 0; i < TABLES_NAMES.length; ++i) {
            if (parent.equals(TABLES_NAMES[i]) && index == 0) {
                return peripheralTables[i];
            }
        }
        return null;
    }

    @Override
    public int getChildCount(final Object parent) {
        if (parent.equals(ROOT)) {
            return TABLES_NAMES.length;
        }
        for (final String type : TABLES_NAMES) {
            if (parent.equals(type)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean isLeaf(final Object node) {
        if (node.equals(ROOT)) {
            return false;
        }
        for (final String type : TABLES_NAMES) {
            if (node.equals(type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getIndexOfChild(final Object parent, final Object child) {
        if (parent.equals(ROOT)) {
            for (int i = 0; i < TABLES_NAMES.length; ++i) {
                if (child == TABLES_NAMES[i]) {
                    return i;
                }
            }
        }
        for (int i = 0; i < TABLES_NAMES.length; ++i) {
            if (parent == TABLES_NAMES[i] && child == peripheralTables[i]) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object newValue) {
        //paths are not editable
    }

    @Override
    public void addTreeModelListener(final TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(final TreeModelListener l) {
        listeners.remove(l);
    }
}
