package pers.yiran.housekeeper.tools;


import org.apache.commons.beanutils.BeanUtils;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;

public class ListTableModel<T> extends AbstractTableModel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final java.util.List<T> list;
    private final String[] colNames;
    private final String[] propNames;

    public ListTableModel(java.util.List<T> list, Class<?> c, String[] colNames, String[] propNames) throws Exception {
        if (list == null) {
            throw new Exception();
        }
        this.list = list;
        this.colNames = colNames;
        this.propNames = propNames;
    }

    public int getRowCount() {
    	int size = list.size();
        return Math.max(size, 10);
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int c) {
        return colNames[c];
    }

    public Object getValueAt(int r, int c) {
    	if(r >= list.size()) return null;
        try {
			return BeanUtils.getProperty(list.get(r), propNames[c]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    public T getInstance(int row) {
    	if(row >= list.size()) return null;
    	return list.get(row);
    }
}
