package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

public class MilitaryGearsGameData implements IGameData {

    private ObjectMap<MilitaryGearSlot, ObjectMap<String, MilitaryGearsGameData>> militaryGearsSlotMap;

    @Override
    public void load (XmlReader.Element rootXml) {
        // loop through each category (weapon, melee, head, etc.)
        final int categoryCount = rootXml.getChildCount();
        for (int i = 0; i < categoryCount; i++) {
            final XmlReader.Element categoryElement = rootXml.getChild(i);
            final String categoryName = categoryElement.getName();
            // iterate over each item inside the category
            final int itemCount = categoryElement.getChildCount();
            for (int j = 0; j < itemCount; j++) {
                final XmlReader.Element itemElement = categoryElement.getChild(j);
                final String name = itemElement.getAttribute("name", "default");
                final String title = itemElement.getAttribute("title", "default");
                final String icon = itemElement.getAttribute("icon", "default");
                final String rarity = itemElement.getAttribute("rarity", "default");
                // print out item details; replace with your own data handling logic
                System.out.println("category: " + categoryName + " -> item: " + name +
                    ", title: " + title + ", icon: " + icon + ", rarity: " + rarity);
            }
        }
    }
}
