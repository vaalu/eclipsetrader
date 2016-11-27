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

package org.eclipsetrader.core.ats;

public interface ITradingSystemService {

    public ITradingSystem[] getTradeSystems();

    public void start();

    public void stop();

    public void start(ITradingSystem system);

    public void stop(ITradingSystem system);

    public void addTradingSystemListener(ITradingSystemListener listener);

    public void removeTradingSystemListener(ITradingSystemListener listener);
}
