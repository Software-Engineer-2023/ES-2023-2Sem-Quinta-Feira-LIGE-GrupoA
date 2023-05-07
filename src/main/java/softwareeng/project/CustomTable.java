package softwareeng.project;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTable extends DefaultTableCellRenderer {
            private final String containValue;

    public CustomTable(String containValue) {
        this.containValue = containValue;
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Obtém o componente padrão da célula
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 0) {
            c.setBackground(Color.GRAY);
        } else if(value != null && value.toString().contains(containValue)) {
            // Define a cor de fundo padrão para as demais células
            c.setBackground(Color.RED);
        }else{
            c.setBackground(table.getBackground());
        }
        return c;
    }

}