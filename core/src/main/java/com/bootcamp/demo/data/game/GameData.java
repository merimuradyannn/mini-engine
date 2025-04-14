package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final MilitaryGearsGameData militaryGearsGameData;

    public GameData () {
        militaryGearsGameData = new MilitaryGearsGameData();
    }

    public void load () {
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/military-gear.xml")));
    }
}
