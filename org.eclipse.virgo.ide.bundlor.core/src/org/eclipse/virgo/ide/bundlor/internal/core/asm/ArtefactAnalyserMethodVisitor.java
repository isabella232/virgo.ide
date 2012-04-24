/*******************************************************************************
 * Copyright (c) 2010 SpringSource, a divison of VMware, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SpringSource, a division of VMware, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.virgo.ide.bundlor.internal.core.asm;

import org.eclipse.virgo.bundlor.support.partialmanifest.PartialManifest;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

/**
 * ASM {@link MethodVisitor} to scan method bodies for imports.
 * 
 * @author Christian Dupuis
 * @author Rob Harrop
 */
final class ArtefactAnalyserMethodVisitor extends EmptyVisitor implements MethodVisitor {

	/**
	 * The <code>PartialManifest</code> being updated.
	 */
	private final PartialManifest partialManifest;

	/**
	 * The type that is being scanned.
	 */
	private final Type type;

	/**
	 * Creates a new <code>ArtefactAnalyserMethodVisitor</code> for the supplied
	 * {@link PartialManifest}.
	 * 
	 * @param partialManifest the <code>PartialManifest</code>.
	 */
	public ArtefactAnalyserMethodVisitor(PartialManifest partialManifest, Type type) {
		this.partialManifest = partialManifest;
		this.type = type;
	}

	/**
	 * @inheritDoc
	 */
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
		VisitorUtils.recordUses(partialManifest, this.type, t);
		return null;
	}

	/**
	 * @inheritDoc
	 */
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		VisitorUtils.recordReferencedTypes(partialManifest, Type.getType(desc));
		VisitorUtils.recordReferencedTypes(partialManifest, Type.getObjectType(owner));
	}

	/**
	 * @inheritDoc
	 */
	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
	}

	/**
	 * @inheritDoc
	 */
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		Type t = Type.getObjectType(owner);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
		VisitorUtils.recordReferencedTypes(partialManifest, Type.getReturnType(desc));
		VisitorUtils.recordReferencedTypes(partialManifest, Type.getArgumentTypes(desc));
	}

	/**
	 * @inheritDoc
	 */
	public void visitMultiANewArrayInsn(String desc, int dims) {
		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
	}

	/**
	 * @inheritDoc
	 */
	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
		VisitorUtils.recordUses(partialManifest, this.type, t);
		return null;
	}

	/**
	 * @inheritDoc
	 */
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		if (type != null) {
			Type t = Type.getObjectType(type);
			VisitorUtils.recordReferencedTypes(partialManifest, t);
		}
	}

	/**
	 * @inheritDoc
	 */
	public void visitTypeInsn(int opcode, String type) {
		Type t = Type.getObjectType(type);
		VisitorUtils.recordReferencedTypes(partialManifest, t);
	}

	/**
	 * {@inheritDoc}
	 */
	public void visitLdcInsn(Object cst) {
		if (cst instanceof Type) {
			VisitorUtils.recordReferencedTypes(partialManifest, (Type) cst);
		}
	}

}
