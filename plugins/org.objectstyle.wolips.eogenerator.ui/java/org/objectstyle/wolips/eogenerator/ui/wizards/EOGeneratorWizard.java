/*
 * ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0
 * 
 * Copyright (c) 2006 The ObjectStyle Group and individual authors of the
 * software. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by the ObjectStyle Group (http://objectstyle.org/)." Alternately,
 * this acknowlegement may appear in the software itself, if and wherever such
 * third-party acknowlegements normally appear.
 * 
 * 4. The names "ObjectStyle Group" and "Cayenne" must not be used to endorse or
 * promote products derived from this software without prior written permission.
 * For written permission, please contact andrus@objectstyle.org.
 * 
 * 5. Products derived from this software may not be called "ObjectStyle" nor
 * may "ObjectStyle" appear in their names without prior written permission of
 * the ObjectStyle Group.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * OBJECTSTYLE GROUP OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals on
 * behalf of the ObjectStyle Group. For more information on the ObjectStyle
 * Group, please see <http://objectstyle.org/>.
 *  
 */
package org.objectstyle.wolips.eogenerator.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.objectstyle.wolips.eogenerator.model.EOGeneratorModel;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "eogen". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class EOGeneratorWizard extends Wizard implements INewWizard {
  private EOGeneratorWizardPage myPage;
  private ISelection mySelection;

  /**
   * Constructor for EOGeneratorWizard.
   */
  public EOGeneratorWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  /**
   * Adding the page to the wizard.
   */

  public void addPages() {
    myPage = new EOGeneratorWizardPage(mySelection);
    addPage(myPage);
  }

  /**
   * This method is called when 'Finish' button is pressed in
   * the wizard. We will create an operation and run it
   * using wizard as execution context.
   */
  public boolean performFinish() {
    final String containerName = myPage.getContainerName();
    final String fileName = myPage.getFileName();
    IRunnableWithProgress op = new IRunnableWithProgress() {
      public void run(IProgressMonitor monitor) throws InvocationTargetException {
        try {
          doFinish(containerName, fileName, monitor);
        }
        catch (CoreException e) {
          throw new InvocationTargetException(e);
        }
        finally {
          monitor.done();
        }
      }
    };
    try {
      getContainer().run(true, false, op);
    }
    catch (InterruptedException e) {
      return false;
    }
    catch (InvocationTargetException e) {
      Throwable realException = e.getTargetException();
      MessageDialog.openError(getShell(), "Error", realException.getMessage());
      return false;
    }
    return true;
  }

  /**
   * The worker method. It will find the container, create the
   * file if missing or just replace its contents, and open
   * the editor on the newly created file.
   */

  private void doFinish(String containerName, String fileName, IProgressMonitor monitor) throws CoreException {
    // create a sample file
    monitor.beginTask("Creating " + fileName, 2);
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IResource resource = root.findMember(new Path(containerName));
    if (!resource.exists() || !(resource instanceof IContainer)) {
      throwCoreException("Container \"" + containerName + "\" does not exist.");
    }
    IContainer container = (IContainer) resource;
    final IFile file = container.getFile(new Path(fileName));
    try {
      EOGeneratorModel model = EOGeneratorModel.createDefaultModel();
      String modelStr = model.writeToString(null, null, null, null);
      InputStream stream = new ByteArrayInputStream(modelStr.getBytes("UTF-8"));
      if (file.exists()) {
        file.setContents(stream, true, true, monitor);
      }
      else {
        file.create(stream, true, monitor);
      }
      stream.close();
    }
    catch (IOException e) {
    }
    monitor.worked(1);
    monitor.setTaskName("Opening file for editing...");
    getShell().getDisplay().asyncExec(new Runnable() {
      public void run() {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        try {
          IDE.openEditor(page, file, true);
        }
        catch (PartInitException e) {
        }
      }
    });
    monitor.worked(1);
  }

  private void throwCoreException(String message) throws CoreException {
    IStatus status = new Status(IStatus.ERROR, "org.objectstyle.wolips.eogenerator.ui", IStatus.OK, message, null);
    throw new CoreException(status);
  }

  /**
   * We will accept the selection in the workbench to see if
   * we can initialize from it.
   * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection _selection) {
    mySelection = _selection;
  }
}
