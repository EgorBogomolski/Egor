package package1;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class PeripheralTableModel implements TableModel {
    public static final int BY_NAME = 0;
    public static final int BY_BRAND = 1;
    public static final int BY_COST = 2;

    private String[] columns = new String[]{"name", "brand", "cost"};
    private Connection connection;
    private String tableName;
    private String orderBy;

    private List<TableModelListener> listeners = new ArrayList<>();

    private String columnsFormat() {
        return '(' + columns[0] + ", " + columns[1] + ", " + columns[2] + ')';
    }

    public PeripheralTableModel(final Connection connection, final String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        this.columns[0] = tableName.charAt(0) + this.columns[0];
        this.orderBy = columns[BY_NAME];
    }

    public boolean add(final Peripheral peripheral) {
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName + ' ' + columnsFormat() + " VALUES" + peripheral.toString()
            );
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
    }

    public void sort(final int by) {
        orderBy = columns[by];
    }

    @Override
    public int getRowCount() {
        try {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2:
                return int.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " ORDER BY " + orderBy);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int i = 0;
            while (i < rowIndex && resultSet.next()) {
                ++i;
            }
            if (i < rowIndex) {
                return null;
            }
            switch (columnIndex) {
                case 0:
                    return resultSet.getString(1);
                case 1:
                    return resultSet.getString(2);
                case 2:
                    return resultSet.getInt(3);
                default:
                    return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        //cells are not editable
    }

    @Override
    public void addTableModelListener(final TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(final TableModelListener l) {
        listeners.remove(l);
    }
}
