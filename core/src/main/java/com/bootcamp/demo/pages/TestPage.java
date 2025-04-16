
package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {
    private static final float WIDGET_SIZE = 225f;

    @Override
    protected void constructContent(Table content) {
        final Table gameUIOverlay = new Table();
        gameUIOverlay.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#e19f66")));
        final Table mainUISegment = constructMainGameUISegment();
        final Table powerBarSegment = constructPowerBarSegment();

        content.add(gameUIOverlay).grow();
        gameUIOverlay.add(powerBarSegment).size(400, 100).expand().bottom();
        content.row();
        content.add(mainUISegment).growX();

    }

    private Table constructPowerBarSegment() {
        final Table bar = new Table();
        bar.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#99826f")));
        final Table border = new Table();
        border.setBackground(Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.valueOf("#f5eae2")));
        border.setFillParent(true);

        bar.addActor(border);
        return bar;
    }

    private Table constructMainGameUISegment() {
        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae2")));

        final Table statsSegment = constructStatsSegment();
        final Table equippedGearAndSecondaryGearsSegment = constructEquippedGearAndSecondaryGearsSegment();
        final Table buttonsSegment = constructButtonsSegment();

        final Table segment = new Table();
        segment.defaults().growX();
        segment.add(statsSegment);
        segment.row();
        segment.add(equippedGearAndSecondaryGearsSegment);
        segment.row();
        segment.add(buttonsSegment);
        return segment;

    }

    private Table constructStatsSegment() {
        StatsContainer container = new StatsContainer();

        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cbac97")));

        final Table segment = new Table();
        segment.pad(25).defaults().space(30);
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae2")));
        segment.add(container).growX();
        segment.add(button).size(150);
        return segment;
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {

        public StatsContainer() {
            super(3);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ae9e91")));
            pad(30).defaults().space(30).growX();
            for (int i = 0; i < 9; i++) {
                StatWidget stat = new StatWidget();
                add(stat);
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

    private Table constructEquippedGearAndSecondaryGearsSegment() {
        final Table equippedGearsSegment = constructEquippedGearsSegment();
        final Table secondaryGearsSegment = constructSecondaryGearsSegment();

        final Table segment = new Table();
        segment.pad(25).defaults().space(30);
        segment.add(equippedGearsSegment).growX();
        segment.add(secondaryGearsSegment).growX();
        return segment;
    }

    private Table constructEquippedGearsSegment() {
        EquippedGearsContainer container = new EquippedGearsContainer();

        final Table segment = new Table();
        segment.add(container).growX();
        return segment;
    }

    private Table constructSecondaryGearsSegment() {
        final Table tacticalsContainer = new TacticalsContainer();
        final Table flagContainer = new FlagContainer();

        final Table tacticalFlagSegment = new Table();
        tacticalFlagSegment.defaults().space(30).size(WIDGET_SIZE);
        tacticalFlagSegment.add(tacticalsContainer);
        tacticalFlagSegment.row();
        tacticalFlagSegment.add(flagContainer);

        final Table borderForFlag = new Table();
        borderForFlag.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForFlag.setFillParent(true);
        flagContainer.addActor(borderForFlag);

        final Table animalContainer = new AnimalContainer();
        final Table animalInnerButton = new Table();
        animalInnerButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ddb56d")));

        final Table borderForAnimalInnerButton = new Table();
        borderForAnimalInnerButton.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForAnimalInnerButton.setFillParent(true);
        animalInnerButton.addActor(borderForAnimalInnerButton);
        animalContainer.add(animalInnerButton).height(30).expand().growX().bottom();

        final Table borderForAnimal = new Table();
        borderForAnimal.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForAnimal.setFillParent(true);
        animalContainer.addActor(borderForAnimal);

        final Table segment = new Table();
        segment.defaults().space(30);
        segment.add(tacticalFlagSegment).grow();
        segment.add(animalContainer).width(WIDGET_SIZE).expand().fillY();

        return segment;
    }

    public static class EquippedGearsContainer extends WidgetsContainer<EquippedGearContainer> {
        public EquippedGearsContainer() {
            super(3);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cfcc")));
            pad(30).defaults().space(30).growX().size(WIDGET_SIZE);

            for (int i = 0; i < 6; i++) {
                EquippedGearContainer widget = new EquippedGearContainer();
                add(widget);
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


    public static class TacticalsContainer extends WidgetsContainer<TacticalContainer> {
        public TacticalsContainer() {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
            pad(30).defaults().space(30);

            for (int i = 0; i < 4; i++) {
                TacticalContainer container = new TacticalContainer();
                add(container);
            }
        }
        public void setData() {

        }

    }

    public static class TacticalContainer extends Table {
        public TacticalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a390")));
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

    public static class AnimalContainer extends Table {
        public AnimalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        }

        public void setData() {

        }
    }

    private Table constructButtonsSegment() {
       /* final BorderedTable borderedTable = new BorderedTable();
        borderedTable.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        borderedTable.setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));*/

        final Table lootLevelButton = new Table();
        lootLevelButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c9c0b9")));
        final Table lootButton = new Table();
        lootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a4da8c")));
        final Table autoLootButton = new Table();
        autoLootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2c2c2")));

        final Table borderForLootLevelButton = new Table();
        borderForLootLevelButton.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForLootLevelButton.setFillParent(true);
        final Table borderForLootButton = new Table();
        borderForLootButton.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForLootButton.setFillParent(true);
        final Table borderForAutoLootButton = new Table();
        borderForAutoLootButton.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#807165")));
        borderForAutoLootButton.setFillParent(true);


        lootLevelButton.addActor(borderForLootLevelButton);
        lootButton.addActor(borderForLootButton);
        autoLootButton.addActor(borderForAutoLootButton);

        final Table segment = new Table();
        segment.defaults().height(150).pad(35).grow();

        segment.add(lootLevelButton);
        segment.add(lootButton);
        segment.add(autoLootButton);
        return segment;
    }
}


