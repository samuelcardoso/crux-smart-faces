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
package org.cruxframework.crux.smartfaces.client.dialog;


import org.cruxframework.crux.smartfaces.client.util.animation.InOutAnimation;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A simple dialog box that can contain an widget.
 * @author Thiago da Rosa de Bustamante
 */
public class DialogBox extends AbstractDialogBox
{
	public static final String DEFAULT_STYLE_NAME = "faces-DialogBox";

	/**
	 * Constructor
	 */
	public DialogBox()
	{
		this(true, true, true, false, DEFAULT_STYLE_NAME);
	}
	
	/**
	 * Constructor
	 * @param movable
	 * @param resizable
	 * @param closable
	 */
	public DialogBox(boolean movable, boolean resizable, boolean closable, boolean modal) 
	{
		this(movable, resizable, closable, modal, DEFAULT_STYLE_NAME);
	}
	
	/**
	 * Constructor
	 * @param movable
	 * @param resizable
	 * @param closable
	 * @param modal 
	 * @param baseStyleName
	 */
	protected DialogBox(boolean movable, boolean resizable, boolean closable, boolean modal, String baseStyleName) 
	{
		super(movable, resizable, closable, modal, baseStyleName);
	}

	/**
	 * Shows a dialog box
	 * @param widget the content widget displayed by this dialog
	 */
	public static DialogBox show(IsWidget widget)
	{
		return show(null, widget, true, true, true, false, DEFAULT_STYLE_NAME, null);
	}

	/**
	 * Shows a dialog box
	 * @param widget the content widget displayed by this dialog
	 * @param animation animates the dialog while showing or hiding
	 */
	public static DialogBox show(IsWidget widget, InOutAnimation animation)
	{
		return show(null, widget, true, true, true, false, DEFAULT_STYLE_NAME, animation);
	}

	/**
	 * Shows a dialog box
	 * @param title the dilog box title.
	 * @param widget the content widget displayed by this dialog
	 * @param movable if true, the window can be dragged
	 * @param resizable if true, the window can be resized
	 * @param closable if true, the window can be clased by a button on the title bar
	 * @param modal if true this dialog disables events that does not target the dialog 
	 * @param styleName the dialog base CSS class name
	 */
	public static DialogBox show(String title, IsWidget widget, boolean movable, boolean resizable, boolean closable, boolean modal, String styleName)
	{
		return show(title, widget, movable, resizable, closable, modal, styleName, null);
	}

	/**
	 * Shows a dialog box
	 * @param title the dilog box title.
	 * @param widget the content widget displayed by this dialog
	 * @param movable if true, the window can be dragged
	 * @param resizable if true, the window can be resized
	 * @param closable if true, the window can be clased by a button on the title bar
	 * @param modal if true this dialog disables events that does not target the dialog 
	 * @param animation animates the dialog while showing or hiding
	 */
	public static DialogBox show(String title, IsWidget widget, boolean movable, boolean resizable, boolean closable, 
		boolean modal, InOutAnimation animation)
	{
		return show(title, widget, movable, resizable, closable, modal, DEFAULT_STYLE_NAME, animation);
	}
	
	/**
	 * Shows a dialog box
	 * @param title the dilog box title.
	 * @param widget the content widget displayed by this dialog
	 * @param movable if true, the window can be dragged
	 * @param resizable if true, the window can be resized
	 * @param closable if true, the window can be clased by a button on the title bar
	 * @param modal if true this dialog disables events that does not target the dialog 
	 * @param styleName the dialog base CSS class name
	 * @param animation animates the dialog while showing or hiding
	 */
	public static DialogBox show(String title, IsWidget widget, boolean movable, boolean resizable, boolean closable, 
								boolean modal, String styleName, InOutAnimation animation)
	{
		DialogBox msgBox = new DialogBox(movable, resizable, closable, modal, styleName); 
		msgBox.setWidget(widget);
		if (title != null)
		{
			msgBox.setDialogTitle(title);
		}
		msgBox.setAnimation(animation);
		msgBox.center();
		return msgBox;
	}
}
