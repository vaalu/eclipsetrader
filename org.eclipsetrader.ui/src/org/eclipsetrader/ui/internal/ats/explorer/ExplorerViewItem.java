/*
 * Copyright (c) 2004-2011 Marco Maccaferri and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marco Maccaferri - initial API and implementation
 */

package org.eclipsetrader.ui.internal.ats.explorer;

import org.eclipse.core.databinding.observable.list.ObservableList;
import org.eclipse.core.runtime.IAdaptable;

public interface ExplorerViewItem extends IAdaptable {

    public ExplorerViewItem getParent();

    public boolean hasChildren();

    public ObservableList getItems();

    public void accept(ExplorerViewItemVisitor visitor);
}
