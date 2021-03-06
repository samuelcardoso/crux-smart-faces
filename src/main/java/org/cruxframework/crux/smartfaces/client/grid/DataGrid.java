/*
 * Copyright 2015 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.smartfaces.client.grid;

import org.cruxframework.crux.core.client.collection.Array;
import org.cruxframework.crux.smartfaces.client.backbone.common.FacesBackboneResourcesCommon;
import org.cruxframework.crux.smartfaces.client.divtable.DivTable;
import org.cruxframework.crux.smartfaces.client.grid.Type.RowSelectStrategy;

import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Implements a div grid based widget.
 * @author Samuel Almeida Cardoso (samuel@cruxframework.org)
 *
 * @param <T> the Data Object type.
 */
public class DataGrid<T> extends PageableDataGrid<T> implements HasEnabled
{
	private static final String STYLE_DISABLED = "-disabled";
	private static final String STYLE_FACES_DATAGRID = "faces-Datagrid";
	private boolean enabled;

	public DataGrid()
	{
		this(RowSelectStrategy.row);
	}

	public DataGrid(RowSelectStrategy rowSelectStrategy)
	{
		super(rowSelectStrategy);
		FacesBackboneResourcesCommon.INSTANCE.css().ensureInjected();
		setStyleName(STYLE_FACES_DATAGRID);
	}
	
	@Override
	public void setStyleName(String style)
	{
	    super.setStyleName(style);
	    addStyleName(FacesBackboneResourcesCommon.INSTANCE.css().facesBackboneDataGrid());
	}

	@Override
	public void setStyleName(String style, boolean add)
	{
		super.setStyleName(style, add);
		if (!add)
		{
		    addStyleName(FacesBackboneResourcesCommon.INSTANCE.css().facesBackboneDataGrid());
		}
	}

	/**
	 * Put all rolls in the edition mode.
	 */
	public void editAllRows()
	{
		if(rows == null)
		{
			return;
		}
		for(int i=0;i<rows.size();i++)
		{
			rows.get(i).edit();
		}
	}

	/**
	 * Put the Row in the edition mode.
	 * @param rowIndex the row index.
	 */
	public void editRow(int rowIndex)
	{
		rows.get(rowIndex).edit();
	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * @param rowIndex the row index.
	 * @return true if the row is editing or false otherwise.
	 */
	public boolean isRowEditing(int rowIndex)
	{
		return rows.get(rowIndex).editing;
	}
	
	/**
	 * Commit all the changes for a single row.
	 * @param rowIndex the row index.
	 */
	public void makeRowChanges(int rowIndex)
	{
		rows.get(rowIndex).makeChanges();
	}

	/**
	 * @param dataFactory
	 * @param key
	 * @return
	 */
	public <V extends IsWidget> Column<T, V> newColumn(GridDataFactory<T> dataFactory, String key)
	{
		return newColumn(dataFactory, key, false);
	}

	/**
	 * @param dataFactory
	 * @param key
	 * @param detail
	 * @return
	 */
	public <V extends IsWidget> Column<T, V> newColumn(GridDataFactory<T> dataFactory, String key, boolean detail)
	{
		Column<T, V> column = new Column<T, V>(this, dataFactory, key, detail);
		addColumn(key, column);
		return column;
	}
	
	/**
	 * Define a column group in order to set a specific header to each group.
	 * @param dataGridColumnGroup
	 * @return 
	 */
	public ColumnGroup<T> newColumnGroup(String key)
	{
		ColumnGroup<T> columnGroup = new ColumnGroup<T>(key);
		addColumnGroup(columnGroup);
		return columnGroup;
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		if(!enabled)
		{
			getContentPanel().addStyleDependentName(STYLE_DISABLED);
		}
		else
		{
			getContentPanel().addStyleDependentName(STYLE_DISABLED);
		}
		setEnableColumns(enabled);
	}

	/**
	 * Undo the changes for a single row.
	 * @param rowIndex the row index.
	 */
	public void undoRowChanges(int rowIndex)
	{
		rows.get(rowIndex).undoChanges();
	}

	@Override
	protected DivTable initializePagePanel() 
	{
		DivTable divTable = super.initializePagePanel();
		return divTable;
	}

	private void setEnableColumns(boolean enabled) 
	{
		Array<Column<T, ?>> columns = getColumns();
		int sizeColumns = columns.size();

		Array<Row<T>> rows = getCurrentPageRows();
		int sizeRows = rows.size();

		if(sizeColumns <= 0 || sizeRows <= 0)
		{
			return;
		}

		for(int i=0; i<sizeRows;i++)
		{
			for(int j=0;j<sizeColumns;j++)
			{
				Widget widget = getPagePanel().getWidget(i, j);
				if(widget != null)
				{
					try
					{
						((HasEnabled)widget).setEnabled(enabled);
					} catch(ClassCastException e){}
				}
			}
		}
	}
}