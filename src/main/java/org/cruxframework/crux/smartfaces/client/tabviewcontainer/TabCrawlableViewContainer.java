/*
 * Copyright 2014 cruxframework.org.
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
package org.cruxframework.crux.smartfaces.client.tabviewcontainer;

import java.util.LinkedHashMap;

import org.cruxframework.crux.core.client.event.focusblur.BeforeBlurEvent;
import org.cruxframework.crux.core.client.event.focusblur.BeforeFocusEvent;
import org.cruxframework.crux.core.client.screen.views.MultipleCrawlableViewsContainer;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.ViewFactory.CreateCallback;
import org.cruxframework.crux.smartfaces.client.tab.TabPanel;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.Panel;

/**
 * A multiple container view that displays the view in tabs and supports hashbang schemas for history control and crawling.
 *
 * @author bruno.rafael (bruno@triggolabs.com)
 */
public class TabCrawlableViewContainer extends MultipleCrawlableViewsContainer
{

	/**
	 * Default css style of the component.
	 */
	public static final String DEFAULT_STYLE_NAME = "faces-TabCrawlableViewContainer";

	private CrawlableTabPanel tabPanel;
	private LinkedHashMap<String, Tab> tabs = new LinkedHashMap<String, Tab>();

	/**
	 * Default constructor.
	 */
	public TabCrawlableViewContainer()
	{
		super(new CrawlableTabPanel(), true);
		tabPanel = getMainWidget();
		tabPanel.setContainer(this);
		tabPanel.addBeforeSelectionHandler(createBeforeSelectionHandler());
		tabPanel.setStyleName(DEFAULT_STYLE_NAME);
	}

	/** Add a view in TabCrawlableViewContainer.
	 * 
	 * @param view - View that will be assigned to the container
	 * @param lazy - Attaches the view in the container, but only renders when the view is accessed
	 * @param closeable - If the view can be closed
	 * @return true if added
	 */
	public boolean add(View view, boolean lazy, boolean closeable)
	{
		if (doAdd(view, lazy, closeable, null))
		{
			adoptView(view);
			return true;
		}
		return false;
	}

	/**
	 * @param view - View that will be assigned to the container
	 * @param lazy - Attaches the view in the container, but only renders when the view is accessed
	 * @param closeable - If the view can be closed
	 * @param render - Defines the view should be rendered on the screen
	 * @return true if added
	 */
	public boolean add(View view, boolean lazy, boolean closeable, boolean render)
	{
		if (add(view, lazy, closeable))
		{
			if (render)
			{
				renderView(view, null);
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds a new view into the container, but does not load the view. 
	 * @param viewName Name of the View to be added
	 * @param closeable if true, allow user to close the tab
	 * @return true if added
	 */
	public boolean addLazy(String viewName, final boolean closeable)
	{
		return addLazy(viewName, viewName, closeable);
	}
	
    /**
	 * Adds a new view into the container, but does not load the view. 
	 * @param viewName Name of the View to be added
	 * @param viewId ID of the View to be added
	 * @param closeable if true, allow user to close the tab
	 * @return true if added
	 */
	public boolean addLazy(String viewName, String viewId, final boolean closeable)
	{
		createView(viewName, viewId, new CreateCallback()
		{
			@Override
            public void onViewCreated(View view)
            {
				add(view, true, closeable);
            }
		});
		return true;
	}
	
    /**
	 * Adds a new view into the container, but does not load the view. 
	 * @param view View to be added
	 * @return true if added
	 */
	public boolean addLazy(View view, final boolean closeable)
	{
		return add(view, true, closeable);
	}

	/**
	 * Closes the tab, skipping any Unload event.
	 * 
	 * @param viewId - the viewId that will be closed
	 */
	public void closeView(String viewId)
	{
		closeView(viewId, true);
	}

	/**
	 * @param viewId - The viewId that will be closed
	 * @param skipEvents - skipping any Unload event.
	 */
	public void closeView(String viewId, final boolean skipEvents)
	{
		View view = getView(viewId);
		if (view != null)
		{
			remove(view, skipEvents);
		}
	}

	/**
	 * @param viewId - The viewId that will be focus
	 */
	public void focusView(String viewId)
	{
		int index = getIndex(viewId);
		if (index >= 0)
		{
			updateHistory(viewId);
			this.tabPanel.selectTab(index);
		}
	}

	/** Return the index of view focused.
	 * @return view focused index
	 */
	public int getFocusedViewIndex()
	{
		return tabPanel.getSelectedTab();
	}

	/**
	 * @param viewId view id
	 * @return index
	 */
	public int getIndex(String viewId)
	{
		return tabPanel.getWidgetIndex(getTab(viewId));
	}

	/**
	 * @param view - view
	 * @return view index 
	 */
	public int getIndex(View view)
	{
		return getIndex(view.getId());
	}

	/**
	 * @param viewId view id
	 * @return tab containing view
	 */
	public Tab getTab(String viewId)
	{
		return tabs.get(viewId);
	}

	
	/**
	 * @param tabIndex tab index
	 * @return view id
	 */
	public String getViewId(int tabIndex)
	{
		return ((Tab) tabPanel.getWidget(tabIndex)).getViewId();
	}

	/**
     * Render the requested view into the container.
     * @param viewName View name
	 * @param closeable if true, allow user to close the tab
	 * @param parameter to be passed to view load and activate events. 
     */
	public void showView(String viewName, boolean closeable)
	{
		showView(viewName, closeable, null);
	}
	
	/**
     * Render the requested view into the container.
     * @param viewName View name
	 * @param closeable if true, allow user to close the tab
	 * @param parameter to be passed to view load and activate events. 
     */
	public void showView(String viewName, boolean closeable, Object parameter)
	{
		showView(viewName, viewName, closeable, parameter);
	}
	
	/**
     * Render the requested view into the container.
     * @param viewName View name
     * @param viewId View identifier
	 * @param closeable if true, allow user to close the tab
	 * @param parameter to be passed to view load and activate events. 
	 */
	public void showView(String viewName, String viewId, final boolean closeable, final Object parameter)
	{
		createView(viewName, viewId, new CreateCallback()
		{
			@Override
            public void onViewCreated(View view)
            {
				if (add(view, false, closeable))
				{
					renderView(view, parameter);
				}
            }
		});
	}

	protected boolean doAdd(View view, boolean lazy, boolean closeable, Object parameter)
    {
	    String tabId = view.getId();
	    
	    if (!views.containsKey(view.getId()))
	    {
	    	boolean doAdd = super.doAdd(view, lazy, parameter);
	    	if (doAdd)
	    	{
	    		Flap flap = new Flap(this, view, closeable);
	    		Tab tab = new Tab(tabPanel, flap, tabId);
	    		this.tabs.put(tabId, tab);			
	    		tabPanel.add(tab, flap);
	    		if (!lazy)
	    		{
	    			focusView(tabId);
	    		}
	    		
	    	}
	    	return doAdd;
	    }
	    else
	    {
    		if (!lazy)
    		{
    			focusView(tabId);
    		}
	    }
	    return false;
    }

	@Override
	protected boolean doAdd(View view, boolean lazy, Object parameter)
	{
		return doAdd(view, lazy, true, parameter);
	}

	@Override
	protected boolean doRemove(View view, boolean skipEvent)
	{
	    boolean doRemove = super.doRemove(view, skipEvent);
	    if (doRemove)
	    {
	    	doCloseTab(view.getId());
	    }
		return doRemove;
	}	
	
	@Override
	protected Panel getContainerPanel(View view)
	{
		Tab tab = getTab(view.getId());
		return tab.getContainerPanel();
	}

	/**
	 * @return Tab focused
	 */
	protected Tab getFocusedTab()
	{
		int index = tabPanel.getSelectedTab();

		if (index >= 0)
		{
			return (Tab) tabPanel.getWidget(index);
		}

		return null;
	}

	@Override
	protected void handleViewTitle(String title, Panel containerPanel, String viewId)
	{
		Tab tab = getTab(viewId);
		if (tab != null)
		{
			tab.setLabel(title);
		}
	}
	
	/***********************************
	 * Override Methods
	 ***********************************/

	@Override
	protected boolean isViewDisplayed(String viewId)
	{
		int index = getIndex(viewId);
		if (index >= 0)
		{
			return index == getFocusedViewIndex();
		}
		return false;
	}

	@Override
	protected void showView(String viewName, String viewId, Object parameter)
	{
		View view = getView(viewName);
		if (view != null)
		{
			if (!view.isActive())
			{
				renderView(view, null);
			} 
			else 
			{
				focusView(viewId);
			}
		}
		else
		{
			loadAndRenderView(viewName, viewId, parameter);
		}
	}

	private BeforeSelectionHandler<Integer> createBeforeSelectionHandler()
	{
		return new BeforeSelectionHandler<Integer>()
		{
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event)
			{
				Tab selectedTab = getFocusedTab();
				String tabId = getViewId(event.getItem());
				boolean canceled = false;
				if (selectedTab != null && !selectedTab.getViewId().equals(tabId))
				{
					BeforeBlurEvent evt = BeforeBlurEvent.fire(selectedTab.getFlap());
					canceled = evt.isCanceled();
				}
				if ((!canceled) && (selectedTab == null || !selectedTab.getViewId().equals(tabId)))
				{
					BeforeFocusEvent evt = BeforeFocusEvent.fire(getTab(tabId));
					canceled = canceled || evt.isCanceled();
				}

				View view = getView(tabId);
				if (view != null && !view.isLoaded())
				{
					renderView(view, null);//TODO selectedTab.getparameter()... e passar o parameter no addLazy
				}

				if (canceled)
				{
					event.cancel();
				}
			}
		};
	}
	
	/**
	 * @param tabId - id of the tab will be closed
	 */
	private void doCloseTab(String tabId)
	{
		int index = getIndex(tabId);
		this.tabPanel.remove(index);
		this.tabs.remove(tabId);
		
		if (this.tabPanel.getWidgetCount() > 0)
		{
			int indexToFocus = index == 0 ? 0 : index - 1;
			this.tabPanel.selectTab(indexToFocus);
		}
	}
		
	static class CrawlableTabPanel extends TabPanel
	{
		private TabCrawlableViewContainer container;
		
		public TabCrawlableViewContainer getContainer()
		{
			return container;
		}

		public void setContainer(TabCrawlableViewContainer container)
		{
			this.container = container;
		}

		@Override
		protected void showTabContent(int selectedItem)
		{
			super.showTabContent(selectedItem);
			container.updateHistory(container.getViewId(selectedItem));
		}
	}
}