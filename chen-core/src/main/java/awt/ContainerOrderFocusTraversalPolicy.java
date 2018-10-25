/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.util.List;
import java.util.ArrayList;
import sun.util.logging.PlatformLogger;

/**
 * A FocusTraversalPolicy that determines traversal order based on the order
 * of child Components in a Container. From a particular focus cycle root, the
 * policy makes a pre-order traversal of the Component hierarchy, and traverses
 * a Container's children according to the ordering of the array returned by
 * <code>Container.getComponents()</code>. Portions of the hierarchy that are
 * not visible and displayable will not be searched.
 * <p>
 * By default, ContainerOrderFocusTraversalPolicy implicitly transfers focus
 * down-cycle. That is, during normal forward focus traversal, the Component
 * traversed after a focus cycle root will be the focus-cycle-root's default
 * Component to focus. This behavior can be disabled using the
 * <code>setImplicitDownCycleTraversal</code> method.
 * <p>
 * By default, methods of this class will return a Component only if it is
 * visible, displayable, enabled, and focusable. Subclasses can modify this
 * behavior by overriding the <code>accept</code> method.
 * <p>
 * This policy takes into account <a
 * href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus traversal
 * policy providers</a>.  When searching for first/last/next/previous Component,
 * if a focus traversal policy provider is encountered, its focus traversal
 * policy is used to perform the search operation.
 *
 * @author David Mendenhall
 *
 * @see java.awt.Container#getComponents
 * @since 1.4
 */
public class ContainerOrderFocusTraversalPolicy extends java.awt.FocusTraversalPolicy
    implements java.io.Serializable
{
    private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.ContainerOrderFocusTraversalPolicy");

    final private int FORWARD_TRAVERSAL = 0;
    final private int BACKWARD_TRAVERSAL = 1;

    /*
     * JDK 1.4 serialVersionUID
     */
    private static final long serialVersionUID = 486933713763926351L;

    private boolean implicitDownCycleTraversal = true;

    /**
     * Used by getComponentAfter and getComponentBefore for efficiency. In
     * order to maintain compliance with the specification of
     * FocusTraversalPolicy, if traversal wraps, we should invoke
     * getFirstComponent or getLastComponent. These methods may be overriden in
     * subclasses to behave in a non-generic way. However, in the generic case,
     * these methods will simply return the first or last Components of the
     * sorted list, respectively. Since getComponentAfter and
     * getComponentBefore have already built the list before determining
     * that they need to invoke getFirstComponent or getLastComponent, the
     * list should be reused if possible.
     */
    transient private java.awt.Container cachedRoot;
    transient private List<java.awt.Component> cachedCycle;

    /*
     * We suppose to use getFocusTraversalCycle & getComponentIndex methods in order
     * to divide the policy into two parts:
     * 1) Making the focus traversal cycle.
     * 2) Traversing the cycle.
     * The 1st point assumes producing a list of components representing the focus
     * traversal cycle. The two methods mentioned above should implement this logic.
     * The 2nd point assumes implementing the common concepts of operating on the
     * cycle: traversing back and forth, retrieving the initial/default/first/last
     * component. These concepts are described in the AWT Focus Spec and they are
     * applied to the FocusTraversalPolicy in general.
     * Thus, a descendant of this policy may wish to not reimplement the logic of
     * the 2nd point but just override the implementation of the 1st one.
     * A striking example of such a descendant is the javax.swing.SortingFocusTraversalPolicy.
     */
    /*protected*/ private List<java.awt.Component> getFocusTraversalCycle(java.awt.Container aContainer) {
        List<java.awt.Component> cycle = new ArrayList<java.awt.Component>();
        enumerateCycle(aContainer, cycle);
        return cycle;
    }
    /*protected*/ private int getComponentIndex(List<java.awt.Component> cycle, java.awt.Component aComponent) {
        return cycle.indexOf(aComponent);
    }

    private void enumerateCycle(java.awt.Container container, List<java.awt.Component> cycle) {
        if (!(container.isVisible() && container.isDisplayable())) {
            return;
        }

        cycle.add(container);

        java.awt.Component[] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            java.awt.Component comp = components[i];
            if (comp instanceof java.awt.Container) {
                java.awt.Container cont = (java.awt.Container)comp;

                if (!cont.isFocusCycleRoot() && !cont.isFocusTraversalPolicyProvider()) {
                    enumerateCycle(cont, cycle);
                    continue;
                }
            }
            cycle.add(comp);
        }
    }

    private java.awt.Container getTopmostProvider(java.awt.Container focusCycleRoot, java.awt.Component aComponent) {
        java.awt.Container aCont = aComponent.getParent();
        java.awt.Container ftp = null;
        while (aCont  != focusCycleRoot && aCont != null) {
            if (aCont.isFocusTraversalPolicyProvider()) {
                ftp = aCont;
            }
            aCont = aCont.getParent();
        }
        if (aCont == null) {
            return null;
        }
        return ftp;
    }

    /*
     * Checks if a new focus cycle takes place and returns a Component to traverse focus to.
     * @param comp a possible focus cycle root or policy provider
     * @param traversalDirection the direction of the traversal
     * @return a Component to traverse focus to if {@code comp} is a root or provider
     *         and implicit down-cycle is set, otherwise {@code null}
     */
    private java.awt.Component getComponentDownCycle(java.awt.Component comp, int traversalDirection) {
        java.awt.Component retComp = null;

        if (comp instanceof java.awt.Container) {
            java.awt.Container cont = (java.awt.Container)comp;

            if (cont.isFocusCycleRoot()) {
                if (getImplicitDownCycleTraversal()) {
                    retComp = cont.getFocusTraversalPolicy().getDefaultComponent(cont);

                    if (retComp != null && log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### Transfered focus down-cycle to " + retComp +
                                 " in the focus cycle root " + cont);
                    }
                } else {
                    return null;
                }
            } else if (cont.isFocusTraversalPolicyProvider()) {
                retComp = (traversalDirection == FORWARD_TRAVERSAL ?
                           cont.getFocusTraversalPolicy().getDefaultComponent(cont) :
                           cont.getFocusTraversalPolicy().getLastComponent(cont));

                if (retComp != null && log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Transfered focus to " + retComp + " in the FTP provider " + cont);
                }
            }
        }
        return retComp;
    }

    /**
     * Returns the Component that should receive the focus after aComponent.
     * aContainer must be a focus cycle root of aComponent or a focus traversal policy provider.
     * <p>
     * By default, ContainerOrderFocusTraversalPolicy implicitly transfers
     * focus down-cycle. That is, during normal forward focus traversal, the
     * Component traversed after a focus cycle root will be the focus-cycle-
     * root's default Component to focus. This behavior can be disabled using
     * the <code>setImplicitDownCycleTraversal</code> method.
     * <p>
     * If aContainer is <a href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus
     * traversal policy provider</a>, the focus is always transferred down-cycle.
     *
     * @param aContainer a focus cycle root of aComponent or a focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus after aComponent, or
     *         null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public java.awt.Component getComponentAfter(java.awt.Container aContainer, java.awt.Component aComponent) {
        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Searching in " + aContainer + " for component after " + aComponent);
        }

        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        if (!aContainer.isFocusTraversalPolicyProvider() && !aContainer.isFocusCycleRoot()) {
            throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");

        } else if (aContainer.isFocusCycleRoot() && !aComponent.isFocusCycleRoot(aContainer)) {
            throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            // Before all the ckecks below we first see if it's an FTP provider or a focus cycle root.
            // If it's the case just go down cycle (if it's set to "implicit").
            java.awt.Component comp = getComponentDownCycle(aComponent, FORWARD_TRAVERSAL);
            if (comp != null) {
                return comp;
            }

            // See if the component is inside of policy provider.
            java.awt.Container provider = getTopmostProvider(aContainer, aComponent);
            if (provider != null) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Asking FTP " + provider + " for component after " + aComponent);
                }

                // FTP knows how to find component after the given. We don't.
                java.awt.FocusTraversalPolicy policy = provider.getFocusTraversalPolicy();
                java.awt.Component afterComp = policy.getComponentAfter(provider, aComponent);

                // Null result means that we overstepped the limit of the FTP's cycle.
                // In that case we must quit the cycle, otherwise return the component found.
                if (afterComp != null) {
                    if (log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### FTP returned " + afterComp);
                    }
                    return afterComp;
                }
                aComponent = provider;
            }

            List<java.awt.Component> cycle = getFocusTraversalCycle(aContainer);

            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle + ", component is " + aComponent);
            }

            int index = getComponentIndex(cycle, aComponent);

            if (index < 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Didn't find component " + aComponent + " in a cycle " + aContainer);
                }
                return getFirstComponent(aContainer);
            }

            for (index++; index < cycle.size(); index++) {
                comp = cycle.get(index);
                if (accept(comp)) {
                    return comp;
                } else if ((comp = getComponentDownCycle(comp, FORWARD_TRAVERSAL)) != null) {
                    return comp;
                }
            }

            if (aContainer.isFocusCycleRoot()) {
                this.cachedRoot = aContainer;
                this.cachedCycle = cycle;

                comp = getFirstComponent(aContainer);

                this.cachedRoot = null;
                this.cachedCycle = null;

                return comp;
            }
        }
        return null;
    }

    /**
     * Returns the Component that should receive the focus before aComponent.
     * aContainer must be a focus cycle root of aComponent or a <a
     * href="doc-files/FocusSpec.html#FocusTraversalPolicyProviders">focus traversal policy
     * provider</a>.
     *
     * @param aContainer a focus cycle root of aComponent or focus traversal policy provider
     * @param aComponent a (possibly indirect) child of aContainer, or
     *        aContainer itself
     * @return the Component that should receive the focus before aComponent,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is not a focus cycle
     *         root of aComponent or focus traversal policy provider, or if either aContainer or
     *         aComponent is null
     */
    public java.awt.Component getComponentBefore(java.awt.Container aContainer, java.awt.Component aComponent) {
        if (aContainer == null || aComponent == null) {
            throw new IllegalArgumentException("aContainer and aComponent cannot be null");
        }
        if (!aContainer.isFocusTraversalPolicyProvider() && !aContainer.isFocusCycleRoot()) {
            throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");

        } else if (aContainer.isFocusCycleRoot() && !aComponent.isFocusCycleRoot(aContainer)) {
            throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            // See if the component is inside of policy provider.
            java.awt.Container provider = getTopmostProvider(aContainer, aComponent);
            if (provider != null) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Asking FTP " + provider + " for component after " + aComponent);
                }

                // FTP knows how to find component after the given. We don't.
                FocusTraversalPolicy policy = provider.getFocusTraversalPolicy();
                java.awt.Component beforeComp = policy.getComponentBefore(provider, aComponent);

                // Null result means that we overstepped the limit of the FTP's cycle.
                // In that case we must quit the cycle, otherwise return the component found.
                if (beforeComp != null) {
                    if (log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("### FTP returned " + beforeComp);
                    }
                    return beforeComp;
                }
                aComponent = provider;

                // If the provider is traversable it's returned.
                if (accept(aComponent)) {
                    return aComponent;
                }
            }

            List<java.awt.Component> cycle = getFocusTraversalCycle(aContainer);

            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle + ", component is " + aComponent);
            }

            int index = getComponentIndex(cycle, aComponent);

            if (index < 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Didn't find component " + aComponent + " in a cycle " + aContainer);
                }
                return getLastComponent(aContainer);
            }

            java.awt.Component comp = null;
            java.awt.Component tryComp = null;

            for (index--; index>=0; index--) {
                comp = cycle.get(index);
                if (comp != aContainer && (tryComp = getComponentDownCycle(comp, BACKWARD_TRAVERSAL)) != null) {
                    return tryComp;
                } else if (accept(comp)) {
                    return comp;
                }
            }

            if (aContainer.isFocusCycleRoot()) {
                this.cachedRoot = aContainer;
                this.cachedCycle = cycle;

                comp = getLastComponent(aContainer);

                this.cachedRoot = null;
                this.cachedCycle = null;

                return comp;
            }
        }
        return null;
    }

    /**
     * Returns the first Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * forward direction.
     *
     * @param aContainer the focus cycle root or focus traversal policy provider whose first
     *        Component is to be returned
     * @return the first Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public java.awt.Component getFirstComponent(java.awt.Container aContainer) {
        List<java.awt.Component> cycle;

        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Getting first component in " + aContainer);
        }
        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");

        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            if (this.cachedRoot == aContainer) {
                cycle = this.cachedCycle;
            } else {
                cycle = getFocusTraversalCycle(aContainer);
            }

            if (cycle.size() == 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Cycle is empty");
                }
                return null;
            }
            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle);
            }

            for (java.awt.Component comp : cycle) {
                if (accept(comp)) {
                    return comp;
                } else if (comp != aContainer &&
                           (comp = getComponentDownCycle(comp, FORWARD_TRAVERSAL)) != null)
                {
                    return comp;
                }
            }
        }
        return null;
    }

    /**
     * Returns the last Component in the traversal cycle. This method is used
     * to determine the next Component to focus when traversal wraps in the
     * reverse direction.
     *
     * @param aContainer the focus cycle root or focus traversal policy provider whose last
     *        Component is to be returned
     * @return the last Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @throws IllegalArgumentException if aContainer is null
     */
    public java.awt.Component getLastComponent(java.awt.Container aContainer) {
        List<java.awt.Component> cycle;
        if (log.isLoggable(PlatformLogger.Level.FINE)) {
            log.fine("### Getting last component in " + aContainer);
        }

        if (aContainer == null) {
            throw new IllegalArgumentException("aContainer cannot be null");
        }

        synchronized(aContainer.getTreeLock()) {

            if (!(aContainer.isVisible() && aContainer.isDisplayable())) {
                return null;
            }

            if (this.cachedRoot == aContainer) {
                cycle = this.cachedCycle;
            } else {
                cycle = getFocusTraversalCycle(aContainer);
            }

            if (cycle.size() == 0) {
                if (log.isLoggable(PlatformLogger.Level.FINE)) {
                    log.fine("### Cycle is empty");
                }
                return null;
            }
            if (log.isLoggable(PlatformLogger.Level.FINE)) {
                log.fine("### Cycle is " + cycle);
            }

            for (int i= cycle.size() - 1; i >= 0; i--) {
                java.awt.Component comp = cycle.get(i);
                if (accept(comp)) {
                    return comp;
                } else if (comp instanceof java.awt.Container && comp != aContainer) {
                    java.awt.Container cont = (java.awt.Container)comp;
                    if (cont.isFocusTraversalPolicyProvider()) {
                        java.awt.Component retComp = cont.getFocusTraversalPolicy().getLastComponent(cont);
                        if (retComp != null) {
                            return retComp;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the default Component to focus. This Component will be the first
     * to receive focus when traversing down into a new focus traversal cycle
     * rooted at aContainer. The default implementation of this method
     * returns the same Component as <code>getFirstComponent</code>.
     *
     * @param aContainer the focus cycle root or focus traversal policy provider whose default
     *        Component is to be returned
     * @return the default Component in the traversal cycle of aContainer,
     *         or null if no suitable Component can be found
     * @see #getFirstComponent
     * @throws IllegalArgumentException if aContainer is null
     */
    public java.awt.Component getDefaultComponent(java.awt.Container aContainer) {
        return getFirstComponent(aContainer);
    }

    /**
     * Sets whether this ContainerOrderFocusTraversalPolicy transfers focus
     * down-cycle implicitly. If <code>true</code>, during normal forward focus
     * traversal, the Component traversed after a focus cycle root will be the
     * focus-cycle-root's default Component to focus. If <code>false</code>,
     * the next Component in the focus traversal cycle rooted at the specified
     * focus cycle root will be traversed instead. The default value for this
     * property is <code>true</code>.
     *
     * @param implicitDownCycleTraversal whether this
     *        ContainerOrderFocusTraversalPolicy transfers focus down-cycle
     *        implicitly
     * @see #getImplicitDownCycleTraversal
     * @see #getFirstComponent
     */
    public void setImplicitDownCycleTraversal(boolean implicitDownCycleTraversal) {
        this.implicitDownCycleTraversal = implicitDownCycleTraversal;
    }

    /**
     * Returns whether this ContainerOrderFocusTraversalPolicy transfers focus
     * down-cycle implicitly. If <code>true</code>, during normal forward focus
     * traversal, the Component traversed after a focus cycle root will be the
     * focus-cycle-root's default Component to focus. If <code>false</code>,
     * the next Component in the focus traversal cycle rooted at the specified
     * focus cycle root will be traversed instead.
     *
     * @return whether this ContainerOrderFocusTraversalPolicy transfers focus
     *         down-cycle implicitly
     * @see #setImplicitDownCycleTraversal
     * @see #getFirstComponent
     */
    public boolean getImplicitDownCycleTraversal() {
        return implicitDownCycleTraversal;
    }

    /**
     * Determines whether a Component is an acceptable choice as the new
     * focus owner. By default, this method will accept a Component if and
     * only if it is visible, displayable, enabled, and focusable.
     *
     * @param aComponent the Component whose fitness as a focus owner is to
     *        be tested
     * @return <code>true</code> if aComponent is visible, displayable,
     *         enabled, and focusable; <code>false</code> otherwise
     */
    protected boolean accept(Component aComponent) {
        if (!aComponent.canBeFocusOwner()) {
            return false;
        }

        // Verify that the Component is recursively enabled. Disabling a
        // heavyweight Container disables its children, whereas disabling
        // a lightweight Container does not.
        if (!(aComponent instanceof java.awt.Window)) {
            for (Container enableTest = aComponent.getParent();
                 enableTest != null;
                 enableTest = enableTest.getParent())
            {
                if (!(enableTest.isEnabled() || enableTest.isLightweight())) {
                    return false;
                }
                if (enableTest instanceof Window) {
                    break;
                }
            }
        }

        return true;
    }
}
