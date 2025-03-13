package com.tonir.demo.pages.core;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.tonir.demo.events.page.PageClosedEvent;
import com.tonir.demo.events.page.PageOpenedEvent;
import com.tonir.demo.managers.API;
import com.tonir.demo.presenters.UI;

public class PageManager implements Disposable {

    private final ObjectMap<Class<? extends APage>, APage> cache = new ObjectMap<>();

    public <T extends APage> T getPage (Class<T> clazz) {
        if (!cache.containsKey(clazz)) {
            try {
                final APage page = ClassReflection.newInstance(clazz);
                cache.put(clazz, page);
                return (T) page;
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) cache.get(clazz);
    }

    public void show (Class<? extends APage> clazz) {
        final Cell<APage> pageCell = API.get(UI.class).getMainPageCell();
        // get the page to show
        final APage page = getPage(clazz);
        // close currently opened page before showing the next page
        hide();
        // visually show the page
        pageCell.setActor(page);
        page.show(null);

        PageOpenedEvent.fire(clazz);
    }

    public void hide () {
        final Cell<APage> pageCell = API.get(UI.class).getMainPageCell();
        final APage currentPage = pageCell.getActor();
        if (currentPage == null) return;
        currentPage.hide(() -> {
            pageCell.setActor(null);
        });

        PageClosedEvent.fire(currentPage.getClass());
    }

    @Override
    public void dispose () {
        cache.clear();
    }
}
