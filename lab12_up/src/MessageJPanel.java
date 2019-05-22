
import javax.swing.*;

enum ButtonValues {
    Press, Conclude, OK
}
enum LabelValues {
    Val, Text
}
enum RadioButtonValues {
    ChooseMe, DoNotChooseMe, Smth
}
enum KeyValues {
    a, b, c, d, e
}

public class MessageJPanel extends javax.swing.JPanel {
    char key = 'a';
    
    public MessageJPanel() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        okJButton = new javax.swing.JButton();
        textJLabel = new javax.swing.JLabel();

        jRadioButton1.setText("myRadioButton1");
       /* jRadioButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textJTextFieldKeyTyped(evt);
            }
        });*/
        jRadioButton2.setText("myRadioButton2");

        okJButton.setText("OK");
        okJButton.addActionListener(evt -> okJButtonActionPerformed(evt));
        okJButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                okJButtonKeyTyped(evt);
            }
        });

        textJLabel.setText("myLabel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(textJLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(okJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2)))
                            
                        
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(okJButton)))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(textJLabel)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton1)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(okJButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(okJButton)
                .addContainerGap(131, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private String jRadioButton1Status(){
        if(jRadioButton1.isSelected())
            return jRadioButton1.getText() + " is selected";
        return jRadioButton1.getText() + " is not selected";
    }
    private String jRadioButton2Status(){
        if(jRadioButton2.isSelected())
            return jRadioButton2.getText() + " is selected"; 
        return jRadioButton2.getText() + " is not selected";
    }
    
    private void okJButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_okJButtonKeyTyped
        if (evt.getKeyChar() == key) {
            okJButton.doClick();
        }
    }//GEN-LAST:event_okJButtonKeyTyped
    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed
        JOptionPane.showMessageDialog(okJButton, jRadioButton1Status() + "\n" + jRadioButton2Status());
    }//GEN-LAST:event_okJButtonActionPerformed

    ButtonValues buttonValues;
    public ButtonValues getButton() {
        return buttonValues;
    }
    public void setButton(ButtonValues buttonValues) {
        this.buttonValues = buttonValues;
        switch (this.buttonValues) {
            case Press:
                okJButton.setText(ButtonValues.Press.toString());
                break;
            case Conclude:
                okJButton.setText(ButtonValues.Conclude.toString());
                break;
            case OK:
                okJButton.setText(ButtonValues.OK.toString());
                break;
        }
    }
    
    RadioButtonValues radioButtonValues1;
    public RadioButtonValues getJRadioButton1() {
        return radioButtonValues1;
    }
    
    public RadioButtonValues getJRadioButton2() {
        return radioButtonValues1;
    }
    public void setRadioButton1(RadioButtonValues radioButtonValues) {
        this.radioButtonValues1 = radioButtonValues;
        switch (this.radioButtonValues1) {
            case ChooseMe:
                jRadioButton1.setText(RadioButtonValues.ChooseMe.toString());
                break;
            case DoNotChooseMe:
                jRadioButton1.setText(RadioButtonValues.DoNotChooseMe.toString());
                break;
            case Smth:
                jRadioButton1.setText(RadioButtonValues.Smth.toString());
                break;
        }
    }
    public void setRadioButton2(RadioButtonValues radioButtonValues) {
        this.radioButtonValues1 = radioButtonValues;
        switch (this.radioButtonValues1) {
            case ChooseMe:
                jRadioButton2.setText(RadioButtonValues.ChooseMe.toString());
                break;
            case DoNotChooseMe:
                jRadioButton2.setText(RadioButtonValues.DoNotChooseMe.toString());
                break;
            case Smth:
                jRadioButton2.setText(RadioButtonValues.Smth.toString());
                break;
        }
    }
    
    LabelValues labelValues;
    public LabelValues getLabel() {
        return labelValues;
    }
    public void setLabel(LabelValues labelValues) {
        this.labelValues = labelValues;
        textJLabel.setText(this.labelValues.toString());
    }
    
    KeyValues keyValues;
    public KeyValues getKey() {
        return keyValues;
    }
    public void setKey(KeyValues keyValues) {
        this.keyValues = keyValues;
        key = this.keyValues.toString().charAt(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton okJButton;
    private javax.swing.JLabel textJLabel;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    // End of variables declaration//GEN-END:variables
}
