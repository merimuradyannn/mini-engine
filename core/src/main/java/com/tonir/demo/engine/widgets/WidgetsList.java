package com.tonir.demo.engine.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;
import lombok.Setter;

public class WidgetsList<T extends Table> extends Table {

    @Getter
    protected final Array<T> widgets = new Array<>();
    private final Array<Cell<T>> currentRowCells = new Array<>();
    private boolean reserveCells = true;

    @Getter @Setter
    private int widgetPerRow;

    @Setter
    private IWidgetReset<T> widgetReset;

    public WidgetsList (int widgetSize, int widgetPerRow, int space) {
        this(widgetSize, widgetSize, widgetPerRow, space);
    }

    public WidgetsList (int widgetWidth, int widgetHeight, int widgetPerRow, int space) {
        this(widgetWidth, widgetHeight, widgetPerRow, space, space);
    }

    public WidgetsList (int widgetWidth, int widgetHeight, int widgetPerRow, int verticalSpace, int horizontalSpace) {
        this.widgetPerRow = widgetPerRow;

        top().left().defaults().size(widgetWidth, widgetHeight).space(verticalSpace, horizontalSpace, verticalSpace, horizontalSpace);
    }

    public WidgetsList (int widgetPerRow, int space) {
        this.widgetPerRow = widgetPerRow;

        top().left().defaults().space(space);
    }

    public WidgetsList (int widgetPerRow) {
        this.widgetPerRow = widgetPerRow;
        top().left();
    }

    public WidgetsList () {
        top().left();
    }

    public void add (T widget) {
        if (widgets.size % widgetPerRow == 0) {
            row();
            if (reserveCells) {
                reserveCells();
            }
        }
        getACell().setActor(widget);
        widgets.add(widget);
    }

    private Cell<T> getACell () {
        if (reserveCells) {
            return currentRowCells.get(widgets.size % widgetPerRow);
        }
        return super.add();
    }

    private void reserveCells () {
        currentRowCells.clear();
        for (int i = 0; i < widgetPerRow; i++) {
            currentRowCells.add(super.add());
        }
    }

    @Override
    public void clearChildren() {
        super.clearChildren();
        widgets.clear();
        currentRowCells.clear();
    }

    public void freeChildren () {
        for (Actor child : getChildren()) {
            if (widgetReset != null) widgetReset.resetState((T) child);
            Pools.free(child);
        }
        clearChildren();
    }

    public WidgetsList<T> reserveCells (boolean reserveCells) {
        this.reserveCells = reserveCells;
        return this;
    }

    public interface IWidgetReset<T> {
        void resetState(T widget);
    }
}

