package com.gm.rtonumbermatcher.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 *
 * Fragment related tools
 */

public final class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 1;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 2;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_POP_ADD_FRAGMENT = 0x01 << 4;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 6;
    private static final int TYPE_HIDE_SHOW_FRAGMENT = 0x01 << 7;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    /**
     * Add fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId) {
        return addFragment(fragmentManager, fragment, containerId, false);
    }

    /**
     * Add fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isHide          Whether it is hidden
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide) {
        return addFragment(fragmentManager, fragment, containerId, isHide, false);
    }

    /**
     * Add fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isHide          Whether it is hidden
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide,
                                       boolean isAddStack) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT);
    }

    /**
     * Add fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isHide          Whether it is hidden
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide,
                                       boolean isAddStack,
                                       SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    /**
     * Add the newly added fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param hideFragment    The fragment to hide
     * @param addFragment     Added fragment
     * @param isHide          Whether it is hidden
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment hideAddFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment hideFragment,
                                           @NonNull Fragment addFragment,
                                           @IdRes int containerId,
                                           boolean isHide,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        putArgs(addFragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, hideFragment, addFragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    /**
     * Add multiple fragments
     *
     * @param fragmentManager fragment manager
     * @param fragments       fragments
     * @param containerId     Layout Id
     * @param showIndex       The fragment index to be displayed
     * @return The fragment to be displayed
     */
    public static Fragment addFragments(@NonNull FragmentManager fragmentManager,
                                        @NonNull List<Fragment> fragments,
                                        @IdRes int containerId,
                                        int showIndex) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                addFragment(fragmentManager, fragment, containerId, showIndex != i, false);
            }
        }
        return fragments.get(showIndex);
    }

    /**
     * Add multiple fragments
     *
     * @param fragmentManager fragment manager
     * @param fragments       fragments
     * @param containerId     Layout Id
     * @param showIndex       The fragment index to be displayed
     * @param lists           Shared element list
     * @return The fragment to be displayed
     */
    public static Fragment addFragments(@NonNull FragmentManager fragmentManager,
                                        @NonNull List<Fragment> fragments,
                                        @IdRes int containerId,
                                        int showIndex,
                                        @NonNull List<SharedElement>... lists) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            List<SharedElement> list = lists[i];
            if (fragment != null && list != null) {
//                if (list != null) {
                    putArgs(fragment, new Args(containerId, showIndex != i, false));
                    return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, list.toArray(new SharedElement[0]));
//                }
            }
        }
        return fragments.get(showIndex);
    }

    /**
     * Remove the fragment
     *
     * @param fragment fragment
     */
    public static void removeFragment(@NonNull Fragment fragment) {
        operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_REMOVE_FRAGMENT);
    }

    /**
     * Remove the specified fragment
     *
     * @param fragment      fragment
     * @param isIncludeSelf Does it include the Fragment class itself?
     */
    public static void removeToFragment(@NonNull Fragment fragment, boolean isIncludeSelf) {
        operateFragment(fragment.getFragmentManager(), isIncludeSelf ? fragment : null, fragment, TYPE_REMOVE_TO_FRAGMENT);
    }

    /**
     * Remove the same level fragment
     */
    public static void removeFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null)
                removeFragment(fragment);
        }
    }

    /**
     * Remove all fragments
     */
    public static void removeAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                removeAllFragments(fragment.getChildFragmentManager());
                removeFragment(fragment);
            }
        }
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment  Source fragment
     * @param destFragment Target fragment
     * @param isAddStack   Whether to fall back to the stack
     * @return Target fragment
     */
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack) {
        if (srcFragment.getArguments() == null)
            return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0)
            return null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment   Source fragment
     * @param destFragment  Target fragment
     * @param isAddStack    Whether to fall back to the stack
     * @param sharedElement Share elements
     * @return Target fragment
     */
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        if (srcFragment.getArguments() == null)
            return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0)
            return null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack, sharedElement);
    }

    /**
     * Replace the fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment,
                                           @IdRes int containerId,
                                           boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT);
    }

    /**
     * Replace the fragment
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isAddStack      Whether to fall back to the stack
     * @param sharedElement   Share elements
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment,
                                           @IdRes int containerId,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT, sharedElement);
    }

    /**
     * Stack fragment
     *
     * @param fragmentManager fragment manager
     * @return {@code true}: Successful stacking<br>{@code false}: The stack failed
     */
    public static boolean popFragment(@NonNull FragmentManager fragmentManager) {
        return fragmentManager.popBackStackImmediate();
    }

    /**
     * Stack to the specified fragment
     *
     * @param fragmentManager fragment manager
     * @param fragmentClass   Fragment class
     * @param isIncludeSelf   Does it include the Fragment class itself?
     * @return {@code true}: Successful stacking<br>{@code false}: The stack failed
     */
    public static boolean popToFragment(@NonNull FragmentManager fragmentManager,
                                        Class<? extends Fragment> fragmentClass,
                                        boolean isIncludeSelf) {
        return fragmentManager.popBackStackImmediate(fragmentClass.getSimpleName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    /**
     * Drop the same level fragment
     *
     * @param fragmentManager fragment manager
     */
    public static void popFragments(@NonNull FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * Stack all the fragments
     *
     * @param fragmentManager fragment manager
     */
    public static void popAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null)
                popAllFragments(fragment.getChildFragmentManager());
        }
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * Add the new stack before stacking
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          @NonNull Fragment fragment,
                                          @IdRes int containerId,
                                          boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT);
    }

    /**
     * Add the new stack before stacking
     *
     * @param fragmentManager fragment manager
     * @param containerId     Layout Id
     * @param fragment        fragment
     * @param isAddStack      Whether to fall back to the stack
     * @return fragment
     */
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          @NonNull Fragment fragment,
                                          @IdRes int containerId,
                                          boolean isAddStack,
                                          SharedElement... sharedElements) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT, sharedElements);
    }

    /**
     * Hide the fragment
     *
     * @param fragment fragment
     * @return Hidden Fragment
     */
    public static Fragment hideFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, true, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_HIDE_FRAGMENT);
    }

    /**
     * Hide the same level fragment
     *
     * @param fragmentManager fragment manager
     */
    public static void hideFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null)
                hideFragment(fragment);
        }
    }

    /**
     * Show fragment
     *
     * @param fragment fragment
     * @return Show of fragment
     */
    public static Fragment showFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    /**
     * Show fragment
     *
     * @param fragment fragment
     * @return Show of fragment
     */
    public static Fragment hideAllShowFragment(@NonNull Fragment fragment) {
        hideFragments(fragment.getFragmentManager());
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    /**
     * After the first hidden fragment
     *
     * @param hideFragment Need to hide the Fragment
     * @param showFragment Need to show the Fragment
     * @return Show the Fragment
     */
    public static Fragment hideShowFragment(@NonNull Fragment hideFragment,
                                            @NonNull Fragment showFragment) {
        Args args = getArgs(hideFragment);
        if (args != null) {
            putArgs(hideFragment, new Args(args.id, true, args.isAddStack));
        }
        args = getArgs(showFragment);
        if (args != null) {
            putArgs(showFragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(showFragment.getFragmentManager(), hideFragment, showFragment, TYPE_HIDE_SHOW_FRAGMENT);
    }

    /**
     * Pass by
     *
     * @param fragment fragment
     * @param args     parameter
     */
    private static void putArgs(@NonNull Fragment fragment, Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    /**
     * Get the parameters
     *
     * @param fragment fragment
     */
    private static Args getArgs(@NonNull Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null || bundle.getInt(ARGS_ID) == 0)
            return null;
        return new Args(bundle.getInt(ARGS_ID), bundle.getBoolean(ARGS_IS_HIDE), bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    /**
     * operating fragment
     *
     * @param fragmentManager fragment manager
     * @param srcFragment     Source fragment
     * @param destFragment    Target fragment
     * @param type            Type of operation
     * @param sharedElements  Share elements
     * @return destFragment
     */
    private static Fragment operateFragment(@NonNull FragmentManager fragmentManager,
                                            Fragment srcFragment,
                                            @NonNull Fragment destFragment,
                                            int type,
                                            SharedElement... sharedElements) {
        if (srcFragment == destFragment)
            return null;
        if (srcFragment != null && srcFragment.isRemoving()) {
            Timber.e(srcFragment.getClass().getSimpleName() + " is isRemoving");
            return null;
        }
        String name = destFragment.getClass().getSimpleName();
        Bundle args = destFragment.getArguments();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (sharedElements == null || sharedElements.length == 0) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            for (SharedElement element : sharedElements) {// Add shared element animation
                ft.addSharedElement(element.shared_Element, element.name);
            }
        }
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                if (srcFragment != null)
                    ft.hide(srcFragment);
                if (destFragment.isAdded())
                    break;
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_HIDE))
                    ft.hide(destFragment);
                if (args.getBoolean(ARGS_IS_ADD_STACK))
                    ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                ft.remove(destFragment);
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                List<Fragment> fragments = getFragments(fragmentManager);
                for (int i = fragments.size() - 1; i >= 0; --i) {
                    Fragment fragment = fragments.get(i);
                    if (fragment == destFragment) {
                        if (srcFragment != null)
                            ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                ft.replace(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK))
                    ft.addToBackStack(name);
                break;
            case TYPE_POP_ADD_FRAGMENT:
                popFragment(fragmentManager);
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK))
                    ft.addToBackStack(name);
                break;
            case TYPE_HIDE_FRAGMENT:
                ft.hide(destFragment);
                break;
            case TYPE_SHOW_FRAGMENT:
                ft.show(destFragment);
                break;
            case TYPE_HIDE_SHOW_FRAGMENT:
                ft.hide(srcFragment).show(destFragment);
                break;
        }
        ft.commitAllowingStateLoss();
        return destFragment;
    }

    /**
     * Get the last fragment of the same level
     *
     * @param fragmentManager fragment manager
     * @return Finally joined the fragment
     */
    public static Fragment getLastAddFragment(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, false);
    }

    /**
     * Get the last fragment of the stack at the same level
     *
     * @param fragmentManager fragment manager
     * @return Finally joined the fragment
     */
    public static Fragment getLastAddFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, true);
    }

    /**
     * According to the stack parameters to get the same level of the last fragment
     *
     * @param fragmentManager fragment manager
     * @param isInStack       Whether it is in the stack
     * @return The last fragment in the stack
     */
    private static Fragment getLastAddFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return null;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * Get the top visible fragment
     *
     * @param fragmentManager fragment manager
     * @return Top visible fragment
     */
    public static Fragment getTopShowFragment(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, false);
    }

    /**
     * Get the top-level visible fragment on the stack
     *
     * @param fragmentManager fragment manager
     * @return The top of the stack is visible
     */
    public static Fragment getTopShowFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, true);
    }

    /**
     * Get the top-level visible fragment based on the stack parameters
     *
     * @param fragmentManager fragment manager
     * @param parentFragment  parent fragment
     * @param isInStack       Whether it is in the stack
     * @return The top of the stack is visible
     */
    private static Fragment getTopShowFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        Fragment parentFragment,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return parentFragment;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, true);
                    }
                } else {
                    return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, false);
                }
            }
        }
        return parentFragment;
    }

    /**
     * Get the same level fragment
     *
     * @param fragmentManager fragment manager
     * @return The same level of the fragment
     */
    public static List<Fragment> getFragments(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, false);
    }

    /**
     * Get the same level in the stack fragment
     *
     * @param fragmentManager fragment manager
     * @return Stack the same level fragment
     */
    public static List<Fragment> getFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, true);
    }

    /**
     * According to the stack parameters to obtain the same level fragment
     *
     * @param fragmentManager fragment manager
     * @param isInStack       Whether it is in the stack
     * @return Stack the same level fragment
     */
    private static List<Fragment> getFragmentsIsInStack(@NonNull FragmentManager fragmentManager, boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty())
            return Collections.emptyList();
        List<Fragment> result = new ArrayList<>();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(fragment);
                    }
                } else {
                    result.add(fragment);
                }
            }
        }
        return result;
    }

    /**
     * Get all the fragments
     *
     * @param fragmentManager fragment manager
     * @return All fragments
     */
    public static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), false);
    }

    /**
     * Gets all the fragments in the stack
     *
     * @param fragmentManager fragment manager
     * @return All fragments
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), true);
    }

    /**
     * Get all the fragments based on the stack parameters
     * <p>Before the need for the operation of the fragment are using the tool class</p>
     *
     * @param fragmentManager fragment manager
     * @param result          result
     * @param isInStack       Whether it is in the stack
     * @return All the fragments in the stack
     */
    private static List<FragmentNode> getAllFragmentsIsInStack(@NonNull FragmentManager fragmentManager,
                                                               List<FragmentNode> result,
                                                               boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty())
            return Collections.emptyList();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), true)));
                    }
                } else {
                    result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), false)));
                }
            }
        }
        return result;
    }

    /**
     * Gets the previous fragment of the target fragment
     *
     * @param destFragment Target fragment
     * @return The previous fragment of the target fragment
     */
    public static Fragment getPreFragment(@NonNull Fragment destFragment) {
        FragmentManager fragmentManager = destFragment.getFragmentManager();
        if (fragmentManager == null)
            return null;
        List<Fragment> fragments = getFragments(fragmentManager);
        boolean flag = false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (flag && fragment != null) {
                return fragment;
            }
            if (fragment == destFragment) {
                flag = true;
            }
        }
        return null;
    }

    /**
     * Find the fragment
     *
     * @param fragmentManager fragment manager
     * @param fragmentClass   fragment class
     * @return Find the fragment
     */
    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty())
            return null;
        return fragmentManager.findFragmentByTag(fragmentClass.getSimpleName());
    }

    /**
     * Handle the fragment back key
     * <p>If fragment implements the OnBackClickListener interface, returns {@code true}: indicates that the consumer has returned the key event, otherwise it is not consumed</p>
     * <p>See FragmentActivity for specific examples</p>
     *
     * @param fragment fragment
     * @return Whether to consume back events
     */

    public static boolean dispatchBackPress(@NonNull Fragment fragment) {
        return dispatchBackPress(fragment.getFragmentManager());
    }

    /**
     * Handle the fragment back key
     * <p>If fragment implements the OnBackClickListener interface, returns {@code true}: indicates that the consumer has returned the key event, otherwise it is not consumed</p>
     * <p>See FragmentActivity for specific examples</p>
     *
     * @param fragmentManager fragment manager
     * @return Whether to consume back events
     */
    public static boolean dispatchBackPress(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty())
            return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set background color
     *
     * @param fragment fragment
     * @param color    Background color
     */
    public static void setBackgroundColor(@NonNull Fragment fragment, @ColorInt int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * Set background resources
     *
     * @param fragment fragment
     * @param resId    Resource Id
     */
    public static void setBackgroundResource(@NonNull Fragment fragment, @DrawableRes int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * Set the background
     *
     * @param fragment   fragment
     * @param background background
     */
    public static void setBackground(@NonNull Fragment fragment, Drawable background) {
        View view = fragment.getView();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(background);
            } else {
                view.setBackgroundDrawable(background);
            }
        }
    }

    static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        Args(int id, boolean isHide, boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class SharedElement {
        View shared_Element;
        String name;

        public SharedElement(View shared_Element, String name) {
            this.shared_Element = shared_Element;
            this.name = name;
        }
    }

    static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(Fragment fragment, List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @Override
        public String toString() {
            return fragment.getClass().getSimpleName() + "->" + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }
}