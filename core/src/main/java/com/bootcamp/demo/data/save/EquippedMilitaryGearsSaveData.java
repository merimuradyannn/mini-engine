package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.MilitaryGearSlot;
import lombok.Getter;

import java.util.Locale;

public class EquippedMilitaryGearsSaveData implements Json.Serializable {

    @Getter
    private final ObjectMap<MilitaryGearSlot, MilitaryGearSaveData> equippedMilitaryGears = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<MilitaryGearSlot, MilitaryGearSaveData> entry : equippedMilitaryGears.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        equippedMilitaryGears.clear();

        for (JsonValue value : jsonValue) {
            final MilitaryGearSlot slot = MilitaryGearSlot.valueOf(value.name().toUpperCase(Locale.ENGLISH));
            final MilitaryGearSaveData militaryGearSaveData = json.readValue(MilitaryGearSaveData.class, value);
            equippedMilitaryGears.put(slot, militaryGearSaveData);
        }
    }
}
