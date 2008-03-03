package org.objectstyle.wolips.componenteditor.inspector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.objectstyle.wolips.bindings.wod.BindingValueKey;
import org.objectstyle.wolips.bindings.wod.BindingValueKeyPath;

public class WOBrowser extends ScrolledComposite implements ISelectionChangedListener, ISelectionProvider, KeyListener {
	private Composite _browser;
	
	private List<WOBrowserColumn> _columns;

	private List<ISelectionChangedListener> _listeners = new LinkedList<ISelectionChangedListener>();
	
	private IWOBrowserDelegate _browserDelegate;

	public WOBrowser(Composite parent, int style) {
		super(parent, SWT.H_SCROLL | style);
		_columns = new LinkedList<WOBrowserColumn>();

		_browser = new Composite(this, SWT.NONE);
		_browser.setBackground(parent.getBackground());
		// _browser.setLayoutData(new GridData(GridData.FILL_BOTH));

		setContent(_browser);
		setExpandVertical(true);

		GridLayout browserLayout = new GridLayout(1, false);
		browserLayout.horizontalSpacing = 0;
		browserLayout.marginWidth = 5;
		browserLayout.marginHeight = 5;
		browserLayout.horizontalSpacing = 5;
		_browser.setLayout(browserLayout);
	}
	
	public void setBrowserDelegate(IWOBrowserDelegate browserDelegate) {
		_browserDelegate = browserDelegate;
		for (WOBrowserColumn column : _columns) {
			column.setDelegate(browserDelegate);
		}
	}
	
	public IWOBrowserDelegate getBrowserDelegate() {
		return _browserDelegate;
	}

	public WOBrowserColumn setRootType(IType type) throws JavaModelException {
		disposeToColumn(-1);
		return addType(type);
	}

	public WOBrowserColumn addType(IType type) throws JavaModelException {
		WOBrowserColumn newColumn = null;
		if (type != null) {
			newColumn = new WOBrowserColumn(type, _browser, SWT.NONE);
			newColumn.getViewer().getTable().addKeyListener(this);
			newColumn.setDelegate(_browserDelegate);
			newColumn.addSelectionChangedListener(this);
			GridData columnLayoutData = new GridData(GridData.FILL_BOTH);
			newColumn.setLayoutData(columnLayoutData);
			_columns.add(newColumn);
			if (_browserDelegate != null) {
				_browserDelegate.browserColumnAdded(newColumn);
			}
			((GridLayout) _browser.getLayout()).numColumns = _columns.size();

			_browser.pack();

			for (WOBrowserColumn column : _columns) {
				Object selectedElement = ((IStructuredSelection) column.getSelection()).getFirstElement();
				if (selectedElement != null) {
					column.getViewer().reveal(selectedElement);
				}
			}

			getHorizontalBar().setSelection(getHorizontalBar().getMaximum());
			layout();
		}
		return newColumn;
	}

	public void disposeToColumn(int columnIndex) {
		for (int columnNum = _columns.size() - 1; columnNum > columnIndex; columnNum--) {
			WOBrowserColumn column = _columns.get(columnNum);
			if (_browserDelegate != null) {
				_browserDelegate.browserColumnRemoved(column);
			}
			column.dispose();
			_columns.remove(columnNum);
		}

		_browser.pack();
	}

	public WOBrowserColumn selectKeyInColumn(BindingValueKey selectedKey, WOBrowserColumn column) {
		WOBrowserColumn addedColumn = null;

		int columnIndex = _columns.indexOf(column);
		if (columnIndex != -1) {
			disposeToColumn(columnIndex);
		}

		if (selectedKey == null) {
			// System.out.println("WOBrowserPage.selectionChanged: none");
		} else {
			try {
				if (!selectedKey.isLeaf()) {
					IType nextType = selectedKey.getNextType();
					if (nextType != null) {
						addedColumn = addType(nextType);
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return addedColumn;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		WOBrowserColumn selectedColumn = (WOBrowserColumn) event.getSource();

		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		BindingValueKey selectedKey = (BindingValueKey) selection.getFirstElement();

		selectKeyInColumn(selectedKey, selectedColumn);

		SelectionChangedEvent wrappedEvent = new SelectionChangedEvent(this, getSelection());
		for (Iterator listeners = _listeners.iterator(); listeners.hasNext();) {
			ISelectionChangedListener listener = (ISelectionChangedListener) listeners.next();
			listener.selectionChanged(wrappedEvent);
		}
	}

	public WOBrowserColumn getSelectedColumn() {
		WOBrowserColumn selectedColumn = null;
		for (WOBrowserColumn column : _columns) {
			if (column.getSelectedKey() != null) {
				selectedColumn = column;
			}
		}
		return selectedColumn;
	}
	
	public String getSelectedKeyPath() {
		StringBuffer keyPath = new StringBuffer();
		for (WOBrowserColumn column : _columns) {
			BindingValueKey key = column.getSelectedKey();
			if (key != null) {
				if (keyPath.length() > 0) {
					keyPath.append('.');
				}
				keyPath.append(key.getBindingName());
			}
		}
		return keyPath.toString();
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		_listeners.add(listener);
	}

	public ISelection getSelection() {
		return new StructuredSelection(getSelectedKeyPath());
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		_listeners.remove(listener);
	}

	public void setSelection(ISelection selection) {
		String selectedKeyPath = (String) ((IStructuredSelection) selection).getFirstElement();
		if (selectedKeyPath == null) {
			WOBrowserColumn column = _columns.get(0);
			selectKeyInColumn(null, column);
			column.setSelection(new StructuredSelection());
		} else {
			try {
				BindingValueKeyPath bindingValueKeyPath = new BindingValueKeyPath(selectedKeyPath, _columns.get(0).getType());
				disposeToColumn(0);
				if (bindingValueKeyPath.isValid()) {
					for (BindingValueKey bindingValueKey : bindingValueKeyPath.getBindingKeys()) {
						WOBrowserColumn column = _columns.get(_columns.size() - 1);
						column.setSelection(new StructuredSelection(bindingValueKey));

						WOBrowserColumn newColumn = selectKeyInColumn(bindingValueKey, column);
						if (newColumn == null) {
							break;
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.ARROW_LEFT) {
			String selectedKey = getSelectedKeyPath();
			if (selectedKey.length() > 0) {
				int dotIndex = selectedKey.lastIndexOf('.');
				if (dotIndex != -1) {
					String previousKey = selectedKey.substring(0, dotIndex);
					setSelection(new StructuredSelection(previousKey));
					WOBrowserColumn previousColumn = getSelectedColumn();
					if (previousColumn != null) {
						previousColumn.setFocus();
					}
				}
			}
		}
		else if (e.keyCode == SWT.ARROW_RIGHT) {
			WOBrowserColumn column = getSelectedColumn();
			if (column != null) {
				int columnIndex = _columns.indexOf(column);
				if (columnIndex < _columns.size() - 1) {
					WOBrowserColumn nextColumn = _columns.get(_columns.size() - 1);
					Object firstElement = nextColumn.getViewer().getElementAt(0);
					nextColumn.setSelection(new StructuredSelection(firstElement));
					nextColumn.setFocus();
				}
			}
		}
		//System.out.println("WOBrowser.keyPressed: " + e);
		// TODO
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO
	}

}
