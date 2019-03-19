import javax.swing.*;

public class AddPerif extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Catalog frame;
    private JLabel descriptLabel;
    private JTextField descriptTextField;
    private JButton closeButton;
    private JLabel countryLabel;
    private JTextField countryTextField;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JLabel modelLabel;
    private JTextField modelTextField;
    private JButton okButton;
    private JLabel typeLabel;
    private JTextField typeTextField;
    public AddPerif(Catalog temp) {

        frame = temp;
        initComponents();
        this.setTitle("Добавление периферийного устройства");
        this.setBounds(400, 200, 400, 150);
        this.setSize(350, 250);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {

        descriptLabel = new JLabel();
        typeLabel = new JLabel();
        modelLabel = new JLabel();
        descriptTextField = new JTextField();
        typeTextField = new JTextField();
        modelTextField = new JTextField();
        countryTextField = new JTextField();
        countryLabel = new JLabel();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        okButton = new JButton();
        closeButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        descriptLabel.setText("Описание: ");
        typeLabel.setText("Тип периферийного устройства: ");
        modelLabel.setText("Модель: ");



        typeTextField.addCaretListener(this::typeTextFieldCaretUpdate);
        modelTextField.addCaretListener(this::modelTextFieldCaretUpdate);

        countryLabel.setText("Страна-производитель: ");
        countryTextField.addCaretListener(this::countryTextFieldCaretUpdate);
        okButton.setText("Добавить");
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        closeButton.setText("Закрыть");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(typeLabel)
                                                        .addComponent(modelLabel)
                                                        .addComponent(countryLabel)
                                                        .addComponent(descriptLabel)

                                                )
                                                .addGap(28, 28, 28)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(typeTextField, GroupLayout.Alignment.TRAILING)
                                                        .addComponent(modelTextField, GroupLayout.Alignment.TRAILING)
                                                        .addComponent(countryTextField, GroupLayout.Alignment.TRAILING)

                                                        .addComponent(descriptTextField)
                                                ))
                                        .addComponent(jSeparator2)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(okButton)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(closeButton))
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
                                                        ))
                                                .addGap(0, 4, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(typeLabel)
                                        .addComponent(typeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(modelLabel)
                                        .addComponent(modelTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(countryLabel)
                                        .addComponent(countryTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(descriptTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(descriptLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okButton)
                                        .addComponent(closeButton))
                                .addContainerGap())
        );
        pack();
    }

    private void countryTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_descriptTextFieldCaretUpdate
        checkOkClose();
    }

    private void typeTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_typeTextFieldCaretUpdate
        checkOkClose();
    }

    private void modelTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_modelTextFieldCaretUpdate
        checkOkClose();
    }

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }

    private void okButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okButtonMouseClicked
        if (this.typeTextField.getText().isEmpty() || this.modelTextField.getText().isEmpty()
                || this.countryTextField.getText().isEmpty() || this.descriptTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Необходимо заполнить все поля!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.okButton.isEnabled()) {
            FlowerNode nb = new FlowerNode(
                    this.typeTextField.getText(), this.modelTextField.getText(),
                    this.countryTextField.getText(), this.descriptTextField.getText()
            );
            Catalog.addResult = nb;
            frame.addNewItem();
            this.dispose();
        }
    }

    public void checkOkClose() {

        if (!this.typeTextField.getText().isEmpty()
                && !this.modelTextField.getText().isEmpty()
                && !this.countryTextField.getText().isEmpty()) {
            this.okButton.setEnabled(true);
        }
    }

}
