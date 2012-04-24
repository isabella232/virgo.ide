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
package org.eclipse.virgo.ide.runtime.internal.ui.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.virgo.ide.runtime.core.ServerUtils;
import org.eclipse.virgo.ide.runtime.core.artefacts.ArtefactSet;
import org.eclipse.virgo.ide.runtime.core.artefacts.ArtefactType;
import org.eclipse.virgo.ide.runtime.core.artefacts.IArtefact;
import org.eclipse.virgo.ide.runtime.core.artefacts.IArtefactTyped;
import org.eclipse.virgo.ide.runtime.core.artefacts.ILocalEntity;
import org.eclipse.virgo.ide.runtime.core.artefacts.LocalBundleArtefact;
import org.eclipse.virgo.ide.runtime.internal.ui.ServerUiImages;
import org.eclipse.wst.server.core.IServer;

/**
 * @author Christian Dupuis
 * @author Miles Parker
 */
public class ArtefactLabelProvider extends LabelProvider {

	public ArtefactLabelProvider() {
	}

	public Image getImage(Object parentElement) {
		if (parentElement instanceof IArtefactTyped) {
			ArtefactType artefactType = ((IArtefactTyped) parentElement).getArtefactType();
			if (artefactType == ArtefactType.BUNDLE) {
				if (parentElement instanceof LocalBundleArtefact
						&& ((LocalBundleArtefact) parentElement).isSourceDownloaded()) {
					return ServerUiImages.getImage(ServerUiImages.IMG_OBJ_BUNDLE_SRC);
				}
				return ServerUiImages.getImage(ServerUiImages.IMG_OBJ_BUNDLE);
			} else if (artefactType == ArtefactType.LIBRARY) {
				return ServerUiImages.getImage(ServerUiImages.IMG_OBJ_LIB);
			}
		}
		return super.getImage(parentElement);
	}

	public String getText(Object element) {
		if (element instanceof ArtefactSet) {
			ArtefactSet set = (ArtefactSet) element;
			String label = set.getArtefactType().getPluralLabel();
			if (element instanceof ILocalEntity) {
				String fileName = ((ILocalEntity) element).getFile().toString();
				if (set.getRepository() != null) {
					IServer server = set.getRepository().getServer();
					if (server != null && server.getRuntime() != null) {
						String home = ServerUtils.getServerHome(server.getRuntime());
						if (fileName.startsWith(home)) {
							fileName = fileName.substring(home.length() + 1);
						}
					}
				}
				label = fileName + " [" + label + "]";
			}
			return label;
		} else if (element instanceof IArtefact) {
			IArtefact version = (IArtefact) element;
			StringBuilder l = new StringBuilder();
			if (version.getName() != null) {
				l.append(version.getName());
				l.append(" - ");
			}
			l.append(version.getSymbolicName());
			l.append(" (");
			l.append(version.getVersion());
			l.append(")");
			return l.toString();
		}
		return super.getText(element);
	}

}
