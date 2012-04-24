/*******************************************************************************
 * Copyright (c) 2009, 2012 SpringSource, a divison of VMware, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SpringSource, a division of VMware, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.virgo.ide.runtime.core.artefacts;

/**
 * 
 * @author Miles Parker
 * 
 */
public interface IArtefactTyped {

	/**
	 * What type of artefact is this?
	 */
	public abstract ArtefactType getArtefactType();

}