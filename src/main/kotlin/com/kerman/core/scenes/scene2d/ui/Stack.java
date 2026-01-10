package com.kerman.core.scenes.scene2d.ui;

import com.kerman.core.scenes.scene2d.Actor;
import com.kerman.core.scenes.scene2d.Touchable;
import com.kerman.core.scenes.scene2d.utils.Layout;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.SnapshotArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.ui.Stack".
 * <p>
 * A stack is a container that sizes its children to its size and positions them at 0,0 on top of each other.
 * <p>
 * The preferred and min size of the stack is the largest preferred and min size of any children. The max size of the stack is the
 * smallest max size of any children.
 */
public class Stack extends WidgetGroup {
    private float prefWidth, prefHeight, minWidth, minHeight, maxWidth, maxHeight;
    private boolean sizeInvalid = true;

    public Stack() {
        setTransform(false);
        setWidth(150);
        setHeight(150);
        setTouchable(Touchable.childrenOnly);
    }

    public Stack(Actor... actors) {
        this();
        for (Actor actor : actors)
            addActor(actor);
    }

    public void invalidate() {
        super.invalidate();
        sizeInvalid = true;
    }

    private void computeSize() {
        sizeInvalid = false;
        prefWidth = 0;
        prefHeight = 0;
        minWidth = 0;
        minHeight = 0;
        maxWidth = 0;
        maxHeight = 0;
        SnapshotArray<Actor> children = getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = children.get(i);
            float childMaxWidth, childMaxHeight;
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                prefWidth = Math.max(prefWidth, layout.getPrefWidth());
                prefHeight = Math.max(prefHeight, layout.getPrefHeight());
                minWidth = Math.max(minWidth, layout.getMinWidth());
                minHeight = Math.max(minHeight, layout.getMinHeight());
                childMaxWidth = layout.getMaxWidth();
                childMaxHeight = layout.getMaxHeight();
            } else {
                prefWidth = Math.max(prefWidth, child.getWidth());
                prefHeight = Math.max(prefHeight, child.getHeight());
                minWidth = Math.max(minWidth, child.getWidth());
                minHeight = Math.max(minHeight, child.getHeight());
                childMaxWidth = 0;
                childMaxHeight = 0;
            }
            if (childMaxWidth > 0) maxWidth = maxWidth == 0 ? childMaxWidth : Math.min(maxWidth, childMaxWidth);
            if (childMaxHeight > 0) maxHeight = maxHeight == 0 ? childMaxHeight : Math.min(maxHeight, childMaxHeight);
        }
    }

    public void add(Actor actor) {
        addActor(actor);
    }

    public void layout() {
        if (sizeInvalid) computeSize();
        float width = getWidth(), height = getHeight();
        KermanArray<Actor> children = getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = children.get(i);
            child.setBounds(0, 0, width, height);
            if (child instanceof Layout) ((Layout) child).validate();
        }
    }

    public float getPrefWidth() {
        if (sizeInvalid) computeSize();
        return prefWidth;
    }

    public float getPrefHeight() {
        if (sizeInvalid) computeSize();
        return prefHeight;
    }

    public float getMinWidth() {
        if (sizeInvalid) computeSize();
        return minWidth;
    }

    public float getMinHeight() {
        if (sizeInvalid) computeSize();
        return minHeight;
    }

    public float getMaxWidth() {
        if (sizeInvalid) computeSize();
        return maxWidth;
    }

    public float getMaxHeight() {
        if (sizeInvalid) computeSize();
        return maxHeight;
    }
}
