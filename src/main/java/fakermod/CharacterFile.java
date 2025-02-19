package fakermod;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fakermod.cards.*;
import fakermod.relics.ClassCard;
import fakermod.relics.CommandDeck;
import fakermod.relics.CommandSeal;
import fakermod.relics.RedPendant;

import java.util.ArrayList;
import java.util.List;

import static fakermod.CharacterFile.Enums.FAKER_COLOR;
import static fakermod.ModFile.*;

public class CharacterFile extends CustomPlayer {

    static final String ID = makeID("ModdedCharacter");
    static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;

    private static final String[] orbTextures = {
            makeCharacterPath("mainChar/orb/layer1.png"),
            makeCharacterPath("mainChar/orb/layer2.png"),
            makeCharacterPath("mainChar/orb/layer3.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer6.png"),
            makeCharacterPath("mainChar/orb/layer1d.png"),
            makeCharacterPath("mainChar/orb/layer2d.png"),
            makeCharacterPath("mainChar/orb/layer3d.png"),
            makeCharacterPath("mainChar/orb/layer4d.png"),
            makeCharacterPath("mainChar/orb/layer5d.png"),
    };

    public CharacterFile(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), null), null, null);
        initializeClass(FAKER_MAIN, SHOULDER1, SHOULDER1, CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                77, 77, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Buster.ID);
        retVal.add(Arts.ID);
        retVal.add(Arts.ID);
        retVal.add(Quick.ID);
        retVal.add(Quick.ID);
        retVal.add(Strengthen.ID);
        retVal.add(Trace.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(CommandDeck.ID);
        UnlockTracker.markRelicAsSeen(CommandDeck.ID);
        UnlockTracker.markRelicAsSeen(CommandSeal.ID);
        UnlockTracker.markRelicAsSeen(ClassCard.ID);
        UnlockTracker.markRelicAsSeen(RedPendant.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel(makeScenePath("scene1.png"), "CARD_EXHAUST"));
        panels.add(new CutscenePanel(makeScenePath("scene2.png")));
        panels.add(new CutscenePanel(makeScenePath("scene3.png")));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage(makeScenePath("sceneBG.png"));
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return FAKER_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Trace();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CharacterFile(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_FAKER;

        @SpireEnum(name = "Saber")
        public static AbstractCard.CardColor FAKERS_COLOR;
        @SpireEnum(name = "Saber")
        public static CardLibrary.LibraryType LIBRARYS_COLOR;

        @SpireEnum(name = "Projections")
        public static AbstractCard.CardColor FAKERP_COLOR;
        @SpireEnum(name = "Projections")
        public static CardLibrary.LibraryType LIBRARYP_COLOR;

        @SpireEnum(name = "Extra")
        public static AbstractCard.CardColor FAKERE_COLOR;
        @SpireEnum(name = "Extra")
        public static CardLibrary.LibraryType LIBRARYE_COLOR;

        @SpireEnum(name = "The Faker")
        public static AbstractCard.CardColor FAKER_COLOR;
        @SpireEnum(name = "The Faker")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
