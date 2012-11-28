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

    // this dialog
    private JDialog dialog;
    
    // widgets
    private JTextField nameWeightField, valueWeightField, attributeWeightField,
            childrenWeightField;
    private JCheckBox nameSimilarityBox, dynamicAllocationBox;
    private JButton okButton, cancelButton;

    /**
     * Constructor
     * 
     * @param parent The parent frame for the settings modal dialog
     */
    public SettingsDialog(Frame parent) {

        constructWidgets();

        constructDialog(parent);
    }

    /**
     * Helper method to set dialog parameters and add all widgets to it.
     * 
     * @param parent The parent frame for the settings modal dialog
     */
    private void constructDialog(Frame parent) {

        dialog = new JDialog(parent, "Settings");
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);
        dialog.setIconImage(new ImageIcon(getClass().getResource(
                "images/settings.png")).getImage());

        BoxLayout layout = new BoxLayout(dialog.getContentPane(),
                BoxLayout.Y_AXIS);
        dialog.setLayout(layout);

        dialog.getContentPane().add(nameSimilarityBox);
        dialog.getContentPane().add(dynamicAllocationBox);

        JPanel nameWeightPane = new JPanel();
        nameWeightPane.add(new JLabel("Name weight ", JLabel.TRAILING));
        nameWeightPane.add(nameWeightField);
        dialog.getContentPane().add(nameWeightPane);

        JPanel valueWeightPane = new JPanel();
        valueWeightPane.add(new JLabel("Value weight ", JLabel.TRAILING));
        valueWeightPane.add(valueWeightField);
        dialog.getContentPane().add(valueWeightPane);

        JPanel attributePane = new JPanel();
        attributePane.add(new JLabel("Attribute weight ", JLabel.TRAILING));
        attributePane.add(attributeWeightField);
        dialog.getContentPane().add(attributePane);

        JPanel childrenPane = new JPanel();
        childrenPane.add(new JLabel("Children weight ", JLabel.TRAILING));
        childrenPane.add(childrenWeightField);
        dialog.getContentPane().add(childrenPane);

        JPanel buttonsPane = new JPanel();
        buttonsPane.add(cancelButton);
        buttonsPane.add(okButton);
        dialog.getContentPane().add(buttonsPane);

        updateFields();
    }

    /**
     * Helper method to update the fields (if they are enabled or not), depending
     * on the selections made by the user.
     */
    private void updateFields() {
        boolean dynEnabled = dynamicAllocationBox.isSelected();
        boolean nameReqEnabled = nameSimilarityBox.isSelected();
        nameWeightField.setEnabled(!dynEnabled && !nameReqEnabled);
        valueWeightField.setEnabled(!dynEnabled);
        attributeWeightField.setEnabled(!dynEnabled);
        childrenWeightField.setEnabled(!dynEnabled);
    }

    /**
     * Helper method to construct and set all the parameters for the widgets.
     * 
     * @param parent The parent frame for the settings modal dialog
     */
    private void constructWidgets() {

        nameSimilarityBox = new JCheckBox("Name Similarity Required");
        nameSimilarityBox.setSelected(SettingsHelper
                .getNameSimilarityRequired());
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
        dynamicAllocationBox.setSelected(SettingsHelper
                .getDynamicWeightAllocation());

        nameWeightField = new JTextField(Float.toString(SettingsHelper
                .getNameSimilarityWeight()), 5);
        valueWeightField = new JTextField(Float.toString(SettingsHelper
                .getValueSimilarityWeight()), 5);
        attributeWeightField = new JTextField(Float.toString(SettingsHelper
                .getAttributeSimilarityWeight()), 5);
        childrenWeightField = new JTextField(Float.toString(SettingsHelper
                .getChildrenSimilarityWeight()), 5);

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

    /**
     * Helper method to show error dialog to the user.
     */
    protected void showError() {
        JOptionPane.showMessageDialog(dialog,
                "The weight values must sum to 1.0!", "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Helper method to decide if the values set by the user are OK. Mainly
     * to check if similarity weights sum to 1.0.
     * 
     * @return true if the values are OK, false otherwise
     */
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

    /**
     * Helper method to close dialog
     */
    protected void closeDialog() {
        dialog.setVisible(false);
        dialog.dispose();
    }

    /**
     * Helper method to save all settings to file.
     */
    protected void saveAllSettings() {

        SettingsHelper
                .setNameSimilarityRequired(nameSimilarityBox.isSelected());

        boolean dynamicWeightAllocation = dynamicAllocationBox.isSelected();
        SettingsHelper.setDynamicWeightAllocation(dynamicWeightAllocation);

        if (!dynamicWeightAllocation) {
            SettingsHelper.setNameSimilarityWeight(Float
                    .parseFloat(nameWeightField.getText()));

            SettingsHelper.setValueSimilarityWeight(Float
                    .parseFloat(valueWeightField.getText()));

            SettingsHelper.setAttributeSimilarityWeight(Float
                    .parseFloat(attributeWeightField.getText()));

            SettingsHelper.setChildrenSimilarityWeight(Float
                    .parseFloat(childrenWeightField.getText()));
        }
    }

    /**
     * Shows the Settings dialog to the user.
     */
    public void show() {
        dialog.pack();
        dialog.setVisible(true);
    }
}
