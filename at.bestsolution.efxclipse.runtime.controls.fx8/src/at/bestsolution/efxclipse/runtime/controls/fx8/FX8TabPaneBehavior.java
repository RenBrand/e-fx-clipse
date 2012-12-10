/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package at.bestsolution.efxclipse.runtime.controls.fx8;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.traversal.Direction;

public class FX8TabPaneBehavior extends BehaviorBase<FX8TabPane> {

    /**************************************************************************
     *                          Setup KeyBindings                             *
     *************************************************************************/
    private static final String HOME = "Home";
    private static final String END = "End";
    private static final String CTRL_PAGE_UP = "Ctrl_Page_Up";
    private static final String CTRL_PAGE_DOWN = "Ctrl_Page_Down";
    private static final String CTRL_TAB = "Ctrl_Tab";
    private static final String CTRL_SHIFT_TAB = "Ctrl_Shift_Tab";

    protected static final List<KeyBinding> TABPANE_BINDINGS = new ArrayList<KeyBinding>();
    static {
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.UP, "TraverseUp"));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "TraverseDown"));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "TraverseLeft"));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "TraverseRight"));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());

        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.HOME, HOME));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.END, END));
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, CTRL_PAGE_UP).ctrl());
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, CTRL_PAGE_DOWN).ctrl());
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, CTRL_TAB).ctrl());
        TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, CTRL_SHIFT_TAB).shift().ctrl());
    }

    @Override protected List<KeyBinding> createKeyBindings() {
        return TABPANE_BINDINGS;
    }

    @Override protected void callAction(String name) {
        if ("TraverseLeft".equals(name) ||
            "TraverseUp".equals(name)) {
            if (getControl().isFocused()) {
                selectPreviousTab();
            }
        } else if ("TraverseRight".equals(name)
                || "TraverseDown".equals(name)) {
            if (getControl().isFocused()) {
                selectNextTab();
            }
        } else if ("TraverseNext".equals(name)) {
            FX8TabPane tp = getControl();
            FX8TabPaneSkin tps = (FX8TabPaneSkin)tp.getSkin();
            if (tps.getSelectedTabContentRegion() != null) {
                if (tps.getSelectedTabContentRegion().getImpl_traversalEngine().registeredNodes.isEmpty()) {

                    tps.getSelectedTabContentRegion().getImpl_traversalEngine().getTopLeftFocusableNode();
                    Parent traversableParent = null;
                    traversableParent = getFirstPopulatedInnerTraversalEngine(tps.getSelectedTabContentRegion().getChildren());
                    if (traversableParent != null) {
                        boolean nodeFound = false;
                        for (Node n : traversableParent.getImpl_traversalEngine().registeredNodes) {
                            if (!n.isFocused() && n.impl_isTreeVisible() && !n.isDisabled()) {
                                n.requestFocus();
                                nodeFound = true;
                                break;
                            }
                        }
                        if (nodeFound == false) {
                            super.callAction(name);
                        }
                    }
                    else {
                        super.callAction(name);
                    }

                }
                else {
                    /*
                    ** if we have the focus owner then traverse from it, otherwise
                    ** request focus in the top-left
                    */
                    List<Node> children = tps.getSelectedTabContentRegion().getChildrenUnmodifiable();
                    Node focusNode = tps.getSelectedTabContentRegion().getScene().getFocusOwner();
                    
                    if (focusNode != null && (isChildFocused(focusNode, children) == true)) {
                        focusNode.impl_traverse(Direction.NEXT);
                    }
                    else {
                        focusFirstChild(children);
                    }
                }
            } else {
                super.callAction(name);
            }
        } else if ("TraversePrevious".equals(name)) {
            FX8TabPane tp = getControl();
            FX8TabPaneSkin tps = (FX8TabPaneSkin)tp.getSkin();
            if (tps.getSelectedTabContentRegion() != null) {
                if (tps.getSelectedTabContentRegion().getImpl_traversalEngine().registeredNodes.isEmpty()) {

                    tps.getSelectedTabContentRegion().getImpl_traversalEngine().getTopLeftFocusableNode();
                    Parent traversableParent = null;
                    traversableParent = getFirstPopulatedInnerTraversalEngine(tps.getSelectedTabContentRegion().getChildren());
                    if (traversableParent != null) {
                        boolean nodeFound = false;
                        for (Node n : traversableParent.getImpl_traversalEngine().registeredNodes) {
                            if (!n.isFocused() && n.impl_isTreeVisible() && !n.isDisabled()) {
                                n.requestFocus();
                                nodeFound = true;
                                break;
                            }
                        }
                        if (nodeFound == false) {
                            super.callAction(name);
                        }
                    }
                    else {
                        super.callAction(name);
                    }
                }
                else {
                    /*
                    ** if we have the focus owner then traverse from it, otherwise
                    ** request focus in the top-left
                    */
                    List<Node> children = tps.getSelectedTabContentRegion().getChildrenUnmodifiable();
                    Node focusNode = tps.getSelectedTabContentRegion().getScene().getFocusOwner();

                    if (focusNode != null && (isChildFocused(focusNode, children) == true)) {
                        focusNode.impl_traverse(Direction.PREVIOUS);
                    }
                    else {
                        focusLastChild(children);
                    }
                }
            } else {
                super.callAction(name);
            }
        } else if (CTRL_TAB.equals(name) || CTRL_PAGE_DOWN.equals(name)) {
            FX8TabPane tp = getControl();
            if (tp.getSelectionModel().getSelectedIndex() == (tp.getTabs().size() - 1)) {
                tp.getSelectionModel().selectFirst();
            } else {
                selectNextTab();
            }
            tp.requestFocus();
        } else if (CTRL_SHIFT_TAB.equals(name) || CTRL_PAGE_UP.equals(name)) {
            FX8TabPane tp = getControl();
            if (tp.getSelectionModel().getSelectedIndex() == 0) {
                tp.getSelectionModel().selectLast();
            } else {
                selectPreviousTab();
            }
            tp.requestFocus();
        } else if (HOME.equals(name)) {
            if (getControl().isFocused()) {
                getControl().getSelectionModel().selectFirst();
            }
        } else if (END.equals(name)) {
            if (getControl().isFocused()) {
                getControl().getSelectionModel().selectLast();
            }
        } else {
            super.callAction(name);
        }
    }

    public static boolean isChildFocused(Node focusedNode, List<Node> children) {
        boolean answer = false;
        for(int i = 0; i < children.size(); i++) {
            if (children.get(i) == focusedNode) {
                answer = true;
                break;
            }
            if (children.get(i) instanceof Parent) {
                if (isChildFocused(focusedNode, ((Parent)children.get(i)).getChildrenUnmodifiable())) {
                    return true;
                }
            }
        }
        return answer;
    }

    public static boolean focusFirstChild(List<Node> children) {
        for(int i = 0; i < children.size(); i++) {
            Node n = children.get(i);
            if (n.isFocusTraversable() && n.impl_isTreeVisible() && !n.isDisabled()) {
                n.requestFocus();
                return true;
            }
            else if (n instanceof Parent) {
                if (focusFirstChild(((Parent)n).getChildrenUnmodifiable())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean focusLastChild(List<Node> children) {
        for(int i = children.size()-1 ; i > -1; i--) {
            Node n = children.get(i);
            if (n.isFocusTraversable() && n.impl_isTreeVisible() && !n.isDisabled()) {
                n.requestFocus();
                return true;
            }
            else if (n instanceof Parent) {
                if (focusFirstChild(((Parent)n).getChildrenUnmodifiable())) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Parent getFirstPopulatedInnerTraversalEngine(ObservableList<Node> root) {
        Parent firstPopulatedEngine = null;
        for (Node node : root) {
            if (node instanceof Parent) {
                if (((Parent)node).getImpl_traversalEngine() != null && !((Parent)node).getImpl_traversalEngine().registeredNodes.isEmpty()) {
                    firstPopulatedEngine = (Parent)node;
                    break;
                }
                else {
                    firstPopulatedEngine = getFirstPopulatedInnerTraversalEngine(((Parent)node).getChildrenUnmodifiable());
                    if (firstPopulatedEngine != null) {
                        break;
                    }
                }
            }
        }
        return firstPopulatedEngine;
    }

    /***************************************************************************
     *                                                                         *
     * Mouse event handling                                                    *
     *                                                                         *
     **************************************************************************/

    @Override public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        FX8TabPane tp = getControl();
        tp.requestFocus();
    }

    /**************************************************************************
     *                         State and Functions                            *
     *************************************************************************/

    public FX8TabPaneBehavior(FX8TabPane tabPane) {
        super(tabPane);
    }

    public void selectTab(FX8Tab tab) {
        getControl().getSelectionModel().select(tab);
    }

    public boolean canCloseTab(FX8Tab tab) {
        Event event = new Event(tab,tab,FX8Tab.CLOSE_REQUEST_EVENT);
        Event.fireEvent(tab, event);
        return ! event.isConsumed();
    }
    
    public void closeTab(FX8Tab tab) {
        FX8TabPane tabPane = getControl();
        // only switch to another tab if the selected tab is the one we're closing
        int index = tabPane.getTabs().indexOf(tab);
        if (tab.isSelected()) {
            if (index == 0) {
                if (tabPane.getTabs().size() > 0) {
                    tabPane.getSelectionModel().selectFirst();
                }
            } else {
                tabPane.getSelectionModel().selectPrevious();
            }
        }
        if (index != -1) {
            tabPane.getTabs().remove(index);
        }                
        if (tab.getOnClosed() != null) {
            Event.fireEvent(tab, new Event(FX8Tab.CLOSED_EVENT));
        }
    }

    // Find a tab after the currently selected that is not disabled.
    public void selectNextTab() {
        SingleSelectionModel<FX8Tab> selectionModel = getControl().getSelectionModel();
        int current = selectionModel.getSelectedIndex();
        int index = current;
        while (index < getControl().getTabs().size()) {
            selectionModel.selectNext();
            index++;
            if (!selectionModel.getSelectedItem().isDisable()) {
                return;
            }
        }
        selectionModel.select(current);
    }

    // Find a tab before the currently selected that is not disabled.
    public void selectPreviousTab() {       
        SingleSelectionModel<FX8Tab> selectionModel = getControl().getSelectionModel();
        int current = selectionModel.getSelectedIndex();
        int index = current;
        while (index > 0) {
            selectionModel.selectPrevious();
            index--;
            if (!selectionModel.getSelectedItem().isDisable()) {
                return;
            }
        }
        selectionModel.select(current);
    }
}
