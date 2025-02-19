package fakermod.util;

import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

import java.util.HashMap;
import java.util.Map;

public class CustomStanceRegistry {
    private static final Map<String, AbstractStance> stanceMap = new HashMap<>();

    public static void registerStance(String stanceID, AbstractStance stance) {
        stanceMap.put(stanceID, stance);
    }

    public static AbstractStance getStance(String stanceID) {
        return stanceMap.getOrDefault(stanceID, new NeutralStance());
    }
}