import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.util.*;

public class Catalog extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static FlowerNode addResult = null;
    public static String path = null;
    JTable infoPanel = new JTable();
    JTree perifsTree = new JTree();
    myTableModel tableModel = null;
    myTreeModel treeModel = null;
    public static void main(String[] args) {
        Catalog mainClass = new Catalog();
        mainClass.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainClass.setVisible(true);
    }
    public void openAddDialog() {
        AddPerif myFrameForm = new AddPerif(this);
        myFrameForm.setVisible(true);
    }

    public void addNewItem() {
        treeNode temp, where, insert, root = treeModel.getRoot();

        try {
            insert = new treeNode(addResult.descript, addResult);
            if ((where = findNode(root, addResult.country)) != null) {
                treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
            } else if (findNode(root, addResult.model) != null) {
                treeModel.insertNodeInto(new treeNode(addResult.country), (temp = findNode(root, addResult.model)), temp.getChildCount(), false);
                where = findNode(root, addResult.country);
                treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
            } else if (findNode(root, addResult.type) != null) {
                treeModel.insertNodeInto(new treeNode(addResult.model), (temp = findNode(root, addResult.type)), temp.getChildCount(), false);
                treeModel.insertNodeInto(new treeNode(addResult.country), (temp = findNode(root, addResult.model)), temp.getChildCount(), false);
                where = findNode(root, addResult.country);
                treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
            } else {
                treeModel.insertNodeInto(new treeNode(addResult.type), root, root.getChildCount(), false);
                treeModel.insertNodeInto(new treeNode(addResult.model), (temp = findNode(root, addResult.type)), temp.getChildCount(), false);
                treeModel.insertNodeInto(new treeNode(addResult.country), (temp = findNode(root, addResult.model)), temp.getChildCount(), false);
                where = findNode(root, addResult.country);
                treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
            }
        } catch (Exception e) {
            path = null;
            addResult = null;
            return;
        }

        path = null;
        addResult = null;
    }

    public void removeItem() {
        TreePath currentSelection = perifsTree.getSelectionPath();
        if (currentSelection != null) {
            treeNode currentNode = (treeNode) (currentSelection.getLastPathComponent());
            treeNode parent = (treeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                parent.deleteNode(currentNode);
                ArrayList<FlowerNode> array = parent.getAllNodes();
                tableModel = new myTableModel(array);
                infoPanel.setModel(tableModel);
            }
        }
    }

    public treeNode findNode(treeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<treeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            treeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return node;
            }
        }
        return null;
    }
    public Catalog() throws HeadlessException {
        JButton addButton = new JButton("Добавить периферийное устройство в каталог");
        addButton.addActionListener(e -> SwingUtilities.invokeLater(() -> openAddDialog()));

        JButton removeButton = new JButton("Удалить периферийное устройство из каталога");
        removeButton.addActionListener(e -> removeItem());

        tableModel = new myTableModel();
        infoPanel = new JTable(tableModel);
        treeModel = new myTreeModel(new treeNode("Каталог"));
        perifsTree = new JTree(treeModel);
        perifsTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                treeNode node = (treeNode) perifsTree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                ArrayList<FlowerNode> array = node.getAllNodes();
                tableModel = new myTableModel(array);
                infoPanel.setModel(tableModel);
            }
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(perifsTree), new JScrollPane(infoPanel));
        splitPane.setDividerLocation(300);


        getContentPane().add(splitPane);
        getContentPane().add("North", addButton);
        getContentPane().add("South", removeButton);
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class myTreeModel extends DefaultTreeModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private treeNode root;

    public myTreeModel(treeNode r) {
        super(r);
        root = r;
    }

    @Override
    public treeNode getRoot() {
        return root;
    }

    public void insertNodeInto(treeNode child, treeNode parent, int i, boolean flag) {
        this.insertNodeInto(child, parent, i);
        parent.addNode(child);
    }
}

class myTableModel implements TableModel {

    static final String[] columnNames = new String[]{"Тип периферийного устройства", "Модель", "Страна-производитель", "Описание"};
    @SuppressWarnings("rawtypes")
    static final Class[] columndescripts = new Class[]{String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class};
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private ArrayList<FlowerNode> infoNodes;

    public myTableModel() {
        infoNodes = new ArrayList<FlowerNode>();
    }

    public myTableModel(ArrayList<FlowerNode> al) {
        this.infoNodes = al;
    }

    public void setInfoArray(ArrayList<FlowerNode> al) {
        infoNodes = al;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return infoNodes.size();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Class getColumnClass(int columnIndex) {
        return columndescripts[columnIndex];
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        FlowerNode nb = infoNodes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return nb.getType();
            case 1:
                return nb.getModel();
            case 2:
                return nb.getCountry();
            case 3:
                return nb.getDescript();
        }
        return "";
    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    }
}

class treeNode extends DefaultMutableTreeNode {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String name;
    FlowerNode ifNode = null;
    ArrayList<treeNode> nodes;
    boolean isThisTheEnd = false;

    public treeNode() {
        name = "-";
        nodes = new ArrayList<treeNode>();
        ifNode = null;
        isThisTheEnd = false;
    }

    public treeNode(String str) {
        name = str;
        nodes = new ArrayList<treeNode>();
        ifNode = null;
        isThisTheEnd = false;
    }

    public treeNode(String str, FlowerNode nbNode) {
        name = str;
        nodes = new ArrayList<treeNode>();
        ifNode = nbNode;
        isThisTheEnd = true;
    }

    public ArrayList<FlowerNode> getAllNodes() {
        ArrayList<FlowerNode> ret = new ArrayList<FlowerNode>();
        Deque<treeNode> deque = new ArrayDeque<treeNode>();

        treeNode temp;
        deque.push(this);
        while (!deque.isEmpty()) {
            temp = deque.removeFirst();
            if (temp.isThisTheEnd) {
                ret.add(temp.getIfNode());
            } else {
                for (int i = 0; i < temp.nodes.size(); i++) {
                    deque.push(temp.nodes.get(i));
                }
            }
        }
        return ret;
    }
    public FlowerNode getIfNode() {
        return ifNode;
    }


    public String toString() {
        return name;
    }
    public void addNode(treeNode tn) {
        nodes.add(tn);
    }

    public void deleteNode(treeNode tn) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).toString().compareToIgnoreCase(tn.toString()) == 0) {
                nodes.remove(i);
            }
        }
    }


}

class FlowerNode {
    String country, descript, type, model;


    FlowerNode() {
    }

    FlowerNode(String type, String model, String country, String name) {

        this.type = type;
        this.model = model;
        this.country = country;
        this.descript = name;
    }
    public String getType() {
        return type;
    }
    public String getModel() {
        return model;
    }


    public String getCountry(){
        return country;
    }

    public String getDescript() {
        return descript;
    }



}