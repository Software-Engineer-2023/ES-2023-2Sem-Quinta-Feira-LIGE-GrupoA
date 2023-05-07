package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.CustomTable;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomTableTest {
    @Test
    public void testGetTableCellRendererComponent() {
        CustomTable customTable = new CustomTable("foo");

        JTable table = new JTable(2, 2);

        // Testa para a primeira coluna
        Component c1 = customTable.getTableCellRendererComponent(table, "foo", false, false, 0, 0);
        assertEquals(c1.getBackground(), Color.GRAY);

        Component c2 = customTable.getTableCellRendererComponent(table, "bar", false, false, 0, 1);
        assertEquals(c2.getBackground(), table.getBackground());

        Component c3 = customTable.getTableCellRendererComponent(table, "foo", false, false, 1, 0);
        assertEquals(c3.getBackground(), Color.GRAY);

        Component c4 = customTable.getTableCellRendererComponent(table, null, false, false, 1, 1);
        assertEquals(c4.getBackground(), table.getBackground());
    }

}