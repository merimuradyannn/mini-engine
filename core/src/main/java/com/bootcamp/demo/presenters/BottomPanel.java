package com.bootcamp.demo.presenters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.dialogs.TestDialog;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.PageManager;
import com.bootcamp.demo.events.core.EventHandler;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.pages.MissionsPage;
import com.bootcamp.demo.pages.TestPage;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.engine.Resources;
import lombok.Setter;

public class BottomPanel extends Table implements EventListener {

    private final Array<BottomButton> buttons;
    private BottomButton currentButton;

    public BottomPanel () {
        API.get(EventModule.class).registerListener(this);

        setBackground(Resources.getDrawable("basics/white-pixel", Color.WHITE));

        buttons = new Array<>();

        defaults().size(250).expandX();
        for (int i = 0; i < 4; i++) {
            // init button
            final BottomButton bottomButton = new BottomButton();
            buttons.add(bottomButton);

            // add to panel
            add(bottomButton);
        }
    }

    @EventHandler
    public void onGameStartedEvent (GameStartedEvent event) {
        buttons.get(0).setPageClass(TestPage.class);
        buttons.get(1).setPageClass(null);
        buttons.get(2).setPageClass(MissionsPage.class);

        // by default select button 1
        buttons.get(1).select();
    }

    private class BottomButton extends OffsetButton {
        @Setter
        private Class<? extends APage> pageClass;
        @Setter
        private boolean isSelected;

        public BottomButton () {
            super(Style.GREEN_35);

            final Image icon = new Image(Resources.getDrawable("basics/white-pixel", Color.RED), Scaling.fit);
            frontTable.add(icon).size(100);

            setOnClick(() -> {
                if (isSelected) {
                    deselect();
                } else {
                    select();
                }
            });
        }

        public void select () {
            // update current button
            if (currentButton != null) {
                currentButton.deselect();
            }
            currentButton = this;

            // select
            isSelected = true;
            // show page if any page exists otherwise just hide
            final PageManager pageManager = API.get(PageManager.class);
            if (pageClass == null) {
                pageManager.hide();
            } else {
                pageManager.show(pageClass);
            }

            visuallySelect();
        }

        public void deselect () {
            isSelected = false;
            API.get(PageManager.class).hide();

            visuallyDeselect();
        }

        private void visuallySelect () {

        }

        private void visuallyDeselect () {

        }
    }
}
