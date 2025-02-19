package fakermod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.cardvars.AbstractEasyDynamicVariable;
import fakermod.events.FatedNightEvent;
import fakermod.monsters.Assassin;
import fakermod.monsters.Lancer;
import fakermod.relics.AbstractEasyRelic;
import fakermod.stances.MeleeStance;
import fakermod.stances.RangedStance;
import fakermod.util.CustomStanceRegistry;
import fakermod.util.ProAudio;
import fakermod.util.TexLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModFile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber {

    public static final Logger logger = LogManager.getLogger(ModFile.class.getName());

    public static final String modID = "FakerMod";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Properties DefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "FakerMod";
    private static final String AUTHOR = "Azuto";
    private static final String DESCRIPTION = "Play as Emiya Shirou from Fate.";

    public static Color characterColor = CardHelper.getColor(200.0F, 200.0F, 200.0F);

    public static final String SHOULDER1 = makeCharacterPath("mainChar/shoulder.png");
    public static final String SHOULDER2 = makeCharacterPath("mainChar/shoulderA.png");
    public static final String SHOULDER3 = makeCharacterPath("mainChar/shoulderB.png");
    public static final String SHOULDER4 = makeCharacterPath("mainChar/shoulderC.png");
    public static final String SHOULDER5 = makeCharacterPath("mainChar/shoulderD.png");
    public static final String CORPSE = makeCharacterPath("mainChar/corpse.png");

    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String ATTACKE_S_ART = makeImagePath("512/attack_E.png");
    private static final String SKILLE_S_ART = makeImagePath("512/skill_E.png");
    private static final String POWERE_S_ART = makeImagePath("512/power_E.png");
    private static final String ATTACKP_S_ART = makeImagePath("512/attack_P.png");
    private static final String SKILLP_S_ART = makeImagePath("512/skill_P.png");
    private static final String POWERP_S_ART = makeImagePath("512/power_P.png");
    private static final String ATTACKS_S_ART = makeImagePath("512/attack_S.png");
    private static final String SKILLS_S_ART = makeImagePath("512/skill_S.png");
    private static final String POWERS_S_ART = makeImagePath("512/power_S.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String CARD_ENERGYE_S = makeImagePath("512/energyE.png");
    private static final String CARD_ENERGYS_S = makeImagePath("512/energyS.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");

    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String ATTACKE_L_ART = makeImagePath("1024/attack_E.png");
    private static final String SKILLE_L_ART = makeImagePath("1024/skill_E.png");
    private static final String POWERE_L_ART = makeImagePath("1024/power_E.png");
    private static final String ATTACKP_L_ART = makeImagePath("1024/attack_P.png");
    private static final String SKILLP_L_ART = makeImagePath("1024/skill_P.png");
    private static final String POWERP_L_ART = makeImagePath("1024/power_P.png");
    private static final String ATTACKS_L_ART = makeImagePath("1024/attack_S.png");
    private static final String SKILLS_L_ART = makeImagePath("1024/skill_S.png");
    private static final String POWERS_L_ART = makeImagePath("1024/power_S.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CARD_ENERGYE_L = makeImagePath("1024/energyE.png");
    private static final String CARD_ENERGYS_L = makeImagePath("1024/energyS.png");

    public static final String BANNER_BLANK = makeImagePath("ui/banner_blank.png");
    public static final String BANNER_BLANK_PORTRAIT = makeImagePath("ui/banner_blank_p.png");
    public static final String BANNER_MYTHIC = makeImagePath("ui/banner_mythic.png");
    public static final String BANNER_MYTHIC_PORTRAIT = makeImagePath("ui/banner_mythic_p.png");

    private static final String CHARSELECT_BUTTON = makeImagePath("charSelect/charButton.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("charSelect/charBG.png");
    public static final String BADGE_IMAGE = makeImagePath("charSelect/charBadge.png");

    public static final String FAKER_MAIN = makeCharacterPath("mainChar/main.png");
    public static final String FAKER_SERVANT = makeCharacterPath("mainChar/Ascension/AS1.png");
    public static final String FAKER_SERVANT2 = makeCharacterPath("mainChar/Ascension/AS2.png");
    public static final String FAKER_JACKET = makeCharacterPath("mainChar/Ascension/B1.png");
    public static final String FAKER_INSTALL = makeCharacterPath("mainChar/Ascension/B2.png");
    public static final String FAKER_UNBROKEN = makeCharacterPath("mainChar/Ascension/B3.png");
    public static final String ARCHER = makeCharacterPath("mainChar/Ascension/C1.png");
    public static final String ARCHER2 = makeCharacterPath("mainChar/Ascension/C2.png");
    public static final String ARCHER_SERVANT = makeCharacterPath("mainChar/Ascension/CS1.png");
    public static final String ARCHER_SERVANT2 = makeCharacterPath("mainChar/Ascension/CS2.png");
    public static final String MURAMASA = makeCharacterPath("mainChar/Ascension/D1.png");
    public static final String MURAMASA2 = makeCharacterPath("mainChar/Ascension/D2.png");
    public static final String MURAMASA_SERVANT = makeCharacterPath("mainChar/Ascension/DS1.png");
    public static final String MURAMASA_SERVANT2 = makeCharacterPath("mainChar/Ascension/DS2.png");
    public static final String ALTER = makeCharacterPath("mainChar/Ascension/E1.png");
    public static final String ALTER2 = makeCharacterPath("mainChar/Ascension/E2.png");
    public static final String ALTER_SERVANT = makeCharacterPath("mainChar/Ascension/ES1.png");
    public static final String ALTER_SERVANT2 = makeCharacterPath("mainChar/Ascension/ES2.png");

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }

        return "eng";
    }

    public ModFile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(CharacterFile.Enums.FAKER_COLOR,
                characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART, CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CharacterFile.Enums.FAKERE_COLOR,
                characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACKE_S_ART, SKILLE_S_ART, POWERE_S_ART, CARD_ENERGYE_S,
                ATTACKE_L_ART, SKILLE_L_ART, POWERE_L_ART, CARD_ENERGYE_L, TEXT_ENERGY);

        BaseMod.addColor(CharacterFile.Enums.FAKERP_COLOR,
                characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACKP_S_ART, SKILLP_S_ART, POWERP_S_ART, CARD_ENERGY_S,
                ATTACKP_L_ART, SKILLP_L_ART, POWERP_L_ART, CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CharacterFile.Enums.FAKERS_COLOR,
                characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACKS_S_ART, SKILLS_S_ART, POWERS_S_ART, CARD_ENERGYS_S,
                ATTACKS_L_ART, SKILLS_L_ART, POWERS_L_ART, CARD_ENERGYS_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) { return modID + "Resources/" + resourcePath; }
    public static String makeImagePath(String resourcePath) { return modID + "Resources/images/" + resourcePath; }
    public static String makeRelicPath(String resourcePath) { return modID + "Resources/images/relics/" + resourcePath; }
    public static String makePowerPath(String resourcePath) { return modID + "Resources/images/powers/" + resourcePath; }
    public static String makeCharacterPath(String resourcePath) { return modID + "Resources/images/char/" + resourcePath; }
    public static String makeEventPath(String resourcePath) { return modID + "Resources/images/events/" + resourcePath; }
    public static String makeCardPath(String resourcePath) { return modID + "Resources/images/cards/" + resourcePath; }
    public static String makeOrbPath(String resourcePath) { return modID + "Resources/images/orbs/" + resourcePath; }
    public static String makeScenePath(String resourcePath) { return modID + "Resources/images/scene/" + resourcePath; }

    public static void initialize() { ModFile fakermod = new ModFile(); }

    @Override
    public void receivePostInitialize() {
        List<String> hiddenCards = Arrays.asList(
                "FakerMod:BraveShine",
                "FakerMod:Disillusion",
                "FakerMod:IdealWhite",
                "FakerMod:LastStardust",
                "FakerMod:MindOfSteel"
        );
/*
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (hiddenCards.contains(card.cardID)) {
                card.isSeen = false;
            }
        }

 */
        Texture badgeTexture = TexLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.addMonster(Lancer.ID, () -> new Lancer(-40, 0));
        BaseMod.addMonster(Assassin.ID, () -> new Assassin(0, 0));

        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(Lancer.ID, 0));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(Assassin.ID, 0));

        BaseMod.addEvent(new AddEventParams.Builder(FatedNightEvent.ID, FatedNightEvent.class)
                .dungeonID(Exordium.ID)
                .playerClass(CharacterFile.Enums.THE_FAKER)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        CustomStanceRegistry.registerStance(RangedStance.STANCE_ID, new RangedStance());
        CustomStanceRegistry.registerStance(MeleeStance.STANCE_ID, new MeleeStance());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new CharacterFile(CharacterFile.characterStrings.NAMES[1], CharacterFile.Enums.THE_FAKER),
            CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, CharacterFile.Enums.THE_FAKER);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);

                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }

                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyDynamicVariable.class)
                .any(DynamicVariable.class, (info, var) ->
                        BaseMod.addDynamicVariable(var));

        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, modID + "Resources/localization/" + getLangString() + "/Eventstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
    }

    @Override
    public void receiveAddAudio() {
        for (ProAudio a : ProAudio.values())
            BaseMod.addAudio(makeID(a.name()), makePath("audio/" + a.name().toLowerCase() + ".ogg"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
