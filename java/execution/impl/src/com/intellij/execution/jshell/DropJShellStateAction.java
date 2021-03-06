// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.execution.jshell;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Eugene Zhuravlev
 */
class DropJShellStateAction extends AnAction{
  private static final Logger LOG = Logger.getInstance("#com.intellij.execution.jshell.ExecuteJShellAction");
  private static final AnAction ourInstance = new DropJShellStateAction();

  private DropJShellStateAction() {
    super("Drop All Code Snippets", "Invalidate all code snippets in the associated JShell instance", AllIcons.Actions.GC);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    final Project project = e.getProject();
    if (project == null) {
      return;
    }
    final VirtualFile vFile = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
    if (vFile == null) {
      return;
    }

    try {
      final JShellHandler handler = JShellHandler.getAssociatedHandler(vFile);
      if (handler != null) {
        handler.toFront();
        handler.dropState();
      }
    }
    catch (Exception ex) {
      LOG.info(ex);
    }
  }

  public static AnAction getSharedInstance() {
    return ourInstance;
  }
}
