/*******************************************************************************
 * Copyright (c) 2009, 2010 Siemens AG and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Tödter - initial implementation
 ******************************************************************************/

package at.bestsolution.efxclipse.runtime.demo.contacts.handlers;

import at.bestsolution.efxclipse.runtime.demo.contacts.Contact;
import at.bestsolution.efxclipse.runtime.demo.contacts.ContactsPackage;
import at.bestsolution.efxclipse.runtime.demo.contacts.Group;
import at.bestsolution.efxclipse.runtime.demo.contacts.model.ContactsManager;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;

@SuppressWarnings("restriction")
public class PasteHandler {

	ContactsManager contactsManager;

	Command command;

	@Inject
	public PasteHandler(ContactsManager contactsManager) {
		this.contactsManager = contactsManager;
	}

	@CanExecute
	public boolean canExecute(@Optional Object selectedItem) {
		Group group = null;

		if (selectedItem instanceof Contact)
			// get containing Group if selection is a Contact
			group = (Group) ((Contact) selectedItem).eContainer();

		else if (selectedItem instanceof Group)
			group = (Group) selectedItem;

		if (group != null) {
			command = PasteFromClipboardCommand.create(contactsManager.getEditingDomain(), group,
					ContactsPackage.eINSTANCE.getGroup_Contacts());
			return command.canExecute();
		}
		
		return false;
	}

	@Execute
	public void execute() {
		if (command != null && command.canExecute())
			contactsManager.getEditingDomain().getCommandStack().execute(command);
	}

}
