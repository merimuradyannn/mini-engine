
package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsList;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {

    @Override
    protected void constructContent(Table content) {
        final Table gameUIOverlay = new Table();
        gameUIOverlay.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#e19f66")));
        final Table mainUISegment = constructMainGameUISegment();
        mainUISegment.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#f5eae2")));
        final Table powerBarSegment = constructPowerBarSegment();

        content.add(gameUIOverlay).grow();
        gameUIOverlay.add(powerBarSegment).size(400, 100).expand().bottom();
        content.row();
        content.add(mainUISegment).growX();
    }

    private Table constructPowerBarSegment() {
        final Table bar = new Table();
        bar.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#99826f")));
        return bar;
    }

    private Table constructMainGameUISegment() {
        final Table statsSegment = constructStatsSegment();

        final Table segment = new Table();
        segment.defaults().growX();
        segment.add(statsSegment);
        segment.row();
        segment.add(constructGearSegment());
        segment.row();
        segment.add(constructButtonsSegment());
        return segment;

    }


    private Table constructStatsSegment() {
        StatsContainer container = new StatsContainer();

        final Table button = new Table();
        button.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#cbac97")));

        final Table segment = new Table();
        segment.pad(25).defaults().space(30);
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#f4e7de")));
        segment.add(container).growX();
        segment.add(button).size(150);
        return segment;
    }

    public static class StatsContainer extends WidgetsList<StatWidget> {

        public StatsContainer() {
            super(3);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ae9e91")));
            pad(30).defaults().space(30).growX();
            for (int i = 0; i < 9; i++) {
                StatWidget stat = new StatWidget();
                this.add(stat);
            }
        }

        public void setData() {

        }

    }

    public static class StatWidget extends Table {
        public StatWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#534b46")));
        }

        public void setData() {

        }
    }


    private Table constructGearSegment() {
        final Table equippedGearsSegment = constructEquippedGearsSegment();
        final Table tacticalFlagAnimalSegment = constructTacticalFlagAnimalSegment();

        final Table segment = new Table();
        segment.pad(25).defaults().space(30);
        segment.add(equippedGearsSegment).growX();
        segment.add(tacticalFlagAnimalSegment).growX();
        return segment;
    }

    private Table constructEquippedGearsSegment() {
        EquippedGearsContainer container = new EquippedGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.WHITE));
        segment.add(container).growX();
        return segment;
    }

    public static class EquippedGearsContainer extends WidgetsList<EquippedGearContainer> {
        public EquippedGearsContainer() {
            super(3);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cfcc")));
            pad(30).defaults().space(30).growX().size(225);
            for (int i = 0; i < 6; i++) {
                EquippedGearContainer widget = new EquippedGearContainer();
                this.add(widget);
            }
        }

        public void setData() {

        }
    }


    public static class EquippedGearContainer extends Table {
        public EquippedGearContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a390")));

        }

        public void setData() {

        }

    }

    private Table constructTacticalFlagSegment() {
        final Table tacticalContainer = new TacticalContainer();
        final Table flagContainer = new FlagContainer();

        final Table segment = new Table();
        segment.defaults().space(30).size(225);
        segment.add(tacticalContainer);
        segment.row();
        segment.add(flagContainer);

        return segment;
    }

    public static class TacticalContainer extends Table {
        public TacticalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        }

        public void setData() {

        }
    }

    public static class FlagContainer extends Table {
        public FlagContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        }

        public void setData() {
        }
    }

    private Table constructTacticalFlagAnimalSegment() {
        final Table segment = new Table();
        final Table tacticalFlagSegment = constructTacticalFlagSegment();
        final Table animalContainer = new AnimalContainer();
        segment.defaults().space(30);
        segment.add(tacticalFlagSegment).grow();
        segment.add(animalContainer).width(225).expand().fillY();
        return segment;
    }


    public static class AnimalContainer extends Table {
        public AnimalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        }

        public void setData() {

        }
    }

    private static class ButtonSegment extends Table {
        public ButtonSegment() {

        }

        public void setData() {

        }
    }


    private Table constructButtonsSegment() {
        final Table tableForButtons = new Table();
        tableForButtons.defaults().height(150).pad(35).grow();
        ButtonSegment cell1 = new ButtonSegment();
        cell1.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#dfbc78")));
        ButtonSegment cell2 = new ButtonSegment();
        cell2.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#a4da8c")));
        ButtonSegment cell3 = new ButtonSegment();
        cell3.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.valueOf("#c2c2c2")));
        tableForButtons.add(cell1);
        tableForButtons.add(cell2);
        tableForButtons.add(cell3);
        return tableForButtons;
    }
}


