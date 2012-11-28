package gems.ic.uff.br.newView;

import gems.ic.uff.br.settings.SettingsHelper;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsDialog {
    
    JDialog dialog;
    JTextField nameWeightField, valueWeightField, attributeWeightField,
            childrenWeightField;
    JCheckBox nameSimilarityBox, dynamicAllocationBox;
    JButton okButton, cancelButton;

    public SettingsDialog(Frame parent) {
        
        constructWidgets();
        
        constructDialog(parent);
    }
    
    private void constructDialog(Frame parent) {
        
        dialog = new JDialog(parent,"Settings");
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);
        dialog.setIconImage(
                new ImageIcon(getClass().getResource("images/settings.png")).getImage());
        
        BoxLayout layout = new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS);
        dialog.setLayout(layout);
        
        dialog.getContentPane().add(nameSimilarityBox);
        dialog.getContentPane().add(dynamicAllocationBox);
        
        JPanel nameWeightPane = new JPanel();
        nameWeightPane.add(new JLabel("Name weight ",JLabel.TRAILING));
        nameWeightPane.add(nameWeightField);
        dialog.getContentPane().add(nameWeightPane);
        
        JPanel valueWeightPane = new JPanel();
        valueWeightPane.add(new JLabel("Value weight ",JLabel.TRAILING));
        valueWeightPane.add(valueWeightField);
        dialog.getContentPane().add(valueWeightPane);

        JPanel attributePane = new JPanel();
        attributePane.add(new JLabel("Attribute weight ",JLabel.TRAILING));
        attributePane.add(attributeWeightField);
        dialog.getContentPane().add(attributePane);

        JPanel childrenPane = new JPanel();
        childrenPane.add(new JLabel("Children weight ",JLabel.TRAILING));
        childrenPane.add(childrenWeightField);
        dialog.getContentPane().add(childrenPane);

        JPanel buttonsPane = new JPanel();
        buttonsPane.add(cancelButton);
        buttonsPane.add(okButton);
        dialog.getContentPane().add(buttonsPane);
        
        updateFields();
    }

    private void updateFields() {
        boolean dynEnabled = dynamicAllocationBox.isSelected();
        boolean nameReqEnabled = nameSimilarityBox.isSelected();
        nameWeightField.setEnabled(!dynEnabled && !nameReqEnabled);
        valueWeightField.setEnabled(!dynEnabled);
        attributeWeightField.setEnabled(!dynEnabled);
        childrenWeightField.setEnabled(!dynEnabled);
    }

    private void constructWidgets() {

        nameSimilarityBox = new JCheckBox(
                "Name Similarity Required");
        nameSimilarityBox.setSelected(SettingsHelper.getNameSimilarityRequired());
        nameSimilarityBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateFields();
            }
        });
        
        dynamicAllocationBox = new JCheckBox(
                "Dinamically allocate similarity weight");
        dynamicAllocationBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });
        dynamicAllocationBox.setSelected(
                SettingsHelper.getDynamicWeightAllocation());
        
        nameWeightField = new JTextField(
                Float.toString(SettingsHelper.getNameSimilarityWeight()),5);
        valueWeightField = new JTextField(
                Float.toString(SettingsHelper.getValueSimilarityWeight()),5);
        attributeWeightField = new JTextField(
                Float.toString(SettingsHelper.getAttributeSimilarityWeight()),5);
        childrenWeightField = new JTextField(
                Float.toString(SettingsHelper.getChildrenSimilarityWeight()),5);
        
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (areValuesOk()) {
                    saveAllSettings();
                    closeDialog();
                } else {
                    showError();
                }
            }
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        });
    }
    
    protected void showError() {
        JOptionPane.showMessageDialog(dialog,
                "The weight values must sum to 1.0!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    protected boolean areValuesOk() {

        boolean dynamicAllocation = dynamicAllocationBox.isSelected();

        if (dynamicAllocation) {
            return true;
        }

        boolean nameSimilarityRequired = nameSimilarityBox.isSelected();
        float nameWeight = Float.parseFloat(nameWeightField.getText());
        float valueWeight = Float.parseFloat(valueWeightField.getText());
        float attributeWeight = Float
                .parseFloat(attributeWeightField.getText());
        float childrenWeight = Float.parseFloat(childrenWeightField.getText());

        if (nameSimilarityRequired) {
            return (valueWeight + attributeWeight + childrenWeight) == 1.0f;
        } else {
            return (nameWeight + valueWeight + attributeWeight + childrenWeight) == 1.0f;
        }
    }

    protected void closeDialog() {
        dialog.setVisible(false);
        dialog.dispose();
    }

    protected void saveAllSettings() {
        
        SettingsHelper.setNameSimilarityRequired(nameSimilarityBox.isSelected());
        
        boolean dynamicWeightAllocation = dynamicAllocationBox.isSelected();
        SettingsHelper.setDynamicWeightAllocation(dynamicWeightAllocation);
        
        if (!dynamicWeightAllocation) {
            SettingsHelper.setNameSimilarityWeight(
                    Float.parseFloat(nameWeightField.getText()));

            SettingsHelper.setValueSimilarityWeight(
                    Float.parseFloat(valueWeightField.getText()));
            
            SettingsHelper.setAttributeSimilarityWeight(
                    Float.parseFloat(attributeWeightField.getText()));

            SettingsHelper.setChildrenSimilarityWeight(
                    Float.parseFloat(childrenWeightField.getText()));
        }
    }

    public void show() {
        dialog.pack();
        dialog.setVisible(true);
    }
}
