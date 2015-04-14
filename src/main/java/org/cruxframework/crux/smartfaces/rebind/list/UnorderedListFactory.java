/*
 * Copyright 2011 cruxframework.org.
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
package org.cruxframework.crux.smartfaces.rebind.list;

import org.cruxframework.crux.core.rebind.screen.widget.WidgetCreatorContext;
import org.cruxframework.crux.core.rebind.screen.widget.creator.children.AnyWidgetChildProcessor;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.DeclarativeFactory;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagChild;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagChildren;
import org.cruxframework.crux.core.rebind.screen.widget.declarative.TagConstraints;
import org.cruxframework.crux.gwt.rebind.ComplexPanelFactory;
import org.cruxframework.crux.smartfaces.client.list.UnorderedList;
import org.cruxframework.crux.smartfaces.rebind.Constants;

/**
 * 
 * @author Thiago Bustamante
 *
 */
@DeclarativeFactory(id="unorderedList", library=Constants.LIBRARY_NAME, targetWidget=UnorderedList.class, 
					description="A panel that shows its items as an unordered list (using <ul> and <li> tags)")
@TagChildren({
	@TagChild(UnorderedListFactory.WidgetContentProcessor.class)
})
public class UnorderedListFactory extends ComplexPanelFactory<WidgetCreatorContext>
{
    @TagConstraints(minOccurs="0", maxOccurs="unbounded")
    public static class WidgetContentProcessor extends AnyWidgetChildProcessor<WidgetCreatorContext> {}
	
	@Override
    public WidgetCreatorContext instantiateContext()
    {
	    return new WidgetCreatorContext();
    }
}