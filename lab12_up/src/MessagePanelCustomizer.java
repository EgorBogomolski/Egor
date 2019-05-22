import javax.swing.*;
import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessagePanelCustomizer extends Panel implements Customizer, TextListener {
    protected MessageJPanel bean;
    protected JComboBox labelComboBox;

    @Override
    public void setObject(Object o) {
        bean = (MessageJPanel) o;

        this.setLayout(new BorderLayout());
        this.add(new Label("Choose label"), "North");

        labelComboBox = new JComboBox();
        DefaultComboBoxModel labelModel = (DefaultComboBoxModel) labelComboBox.getModel();
        labelModel.addElement("Smth");
        labelModel.addElement("Enter the text");
        labelModel.addElement("Geben Sie den Text ein");
        this.add(labelComboBox);
    }

    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        listeners.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.removePropertyChangeListener(l);
    }

    @Override
    public void textValueChanged(TextEvent te) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
