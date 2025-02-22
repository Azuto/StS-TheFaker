package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import fakermod.ModFile;

import java.util.*;

public class ChangeImageAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;

    public void reloadTexture(String key) {
        Map<String, String> textureMap = new HashMap<>();
        textureMap.put("MAIN", ModFile.FAKER_MAIN);
        textureMap.put("FAKER_SERVANT", ModFile.FAKER_SERVANT);
        textureMap.put("FAKER_SERVANT2", ModFile.FAKER_SERVANT2);
        textureMap.put("JACKET", ModFile.FAKER_JACKET);
        textureMap.put("INSTALL", ModFile.FAKER_INSTALL);
        textureMap.put("UNBROKEN", ModFile.FAKER_UNBROKEN);
        textureMap.put("ARCHER", ModFile.ARCHER);
        textureMap.put("ARCHER2", ModFile.ARCHER2);
        textureMap.put("ARCHER_SERVANT", ModFile.ARCHER_SERVANT);
        textureMap.put("ARCHER_SERVANT2", ModFile.ARCHER_SERVANT2);
        textureMap.put("MURAMASA", ModFile.MURAMASA);
        textureMap.put("MURAMASA2", ModFile.MURAMASA2);
        textureMap.put("MURAMASA_SERVANT", ModFile.MURAMASA_SERVANT);
        textureMap.put("MURAMASA_SERVANT2", ModFile.MURAMASA_SERVANT2);
        textureMap.put("ALTER", ModFile.ALTER);
        textureMap.put("ALTER2", ModFile.ALTER2);
        textureMap.put("ALTER_SERVANT", ModFile.ALTER_SERVANT);
        textureMap.put("ALTER_SERVANT2", ModFile.ALTER_SERVANT2);

        if (textureMap.containsKey(key)) {
            p.img = ImageMaster.loadImage(textureMap.get(key));
        }
    }

    public void reloadShoulderTexture(String key) {
        Map<String, String> textureMap = new HashMap<>();
        textureMap.put("MAIN", ModFile.SHOULDER1);
        textureMap.put("FAKER_SERVANT", ModFile.SHOULDER2);
        textureMap.put("FAKER_SERVANT2", ModFile.SHOULDER2);
        textureMap.put("JACKET", ModFile.SHOULDER3);
        textureMap.put("INSTALL", ModFile.SHOULDER3);
        textureMap.put("UNBROKEN", ModFile.SHOULDER1);
        textureMap.put("ARCHER", ModFile.SHOULDER4);
        textureMap.put("ARCHER2", ModFile.SHOULDER4);
        textureMap.put("ARCHER_SERVANT", ModFile.SHOULDER4);
        textureMap.put("ARCHER_SERVANT2", ModFile.SHOULDER4);
        textureMap.put("MURAMASA", ModFile.SHOULDER5);
        textureMap.put("MURAMASA2", ModFile.SHOULDER5);
        textureMap.put("MURAMASA_SERVANT", ModFile.SHOULDER5);
        textureMap.put("MURAMASA_SERVANT2", ModFile.SHOULDER5);
        textureMap.put("ALTER", ModFile.SHOULDER5);
        textureMap.put("ALTER2", ModFile.SHOULDER5);
        textureMap.put("ALTER_SERVANT", ModFile.SHOULDER5);
        textureMap.put("ALTER_SERVANT2", ModFile.SHOULDER5);

        if (textureMap.containsKey(key)) {
            p.shoulderImg = ImageMaster.loadImage(textureMap.get(key));
            p.shoulder2Img = ImageMaster.loadImage(textureMap.get(key));
        }
    }

    private boolean hasAllKatanas() {
        ArrayList<String> requiredCards = new ArrayList<>(Arrays.asList(
                "FakerMod:BlessedKatana", "FakerMod:CursedKatana", "FakerMod:DestructiveKatana", "FakerMod:PurifyingKatana", "FakerMod:MyoujingiriMuramasa"
        ));

        HashSet<String> masterDeckIDs = new HashSet<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            masterDeckIDs.add(card.cardID);
        }

        return masterDeckIDs.containsAll(requiredCards);
    }


    public ChangeImageAction() { }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasClassCard = p.hasRelic("FakerMod:ClassCard");
        boolean hasRedPendant = p.hasRelic("FakerMod:RedPendant");
        boolean hasSaber = p.hasPower("FakerMod:SummonSaberPower");
        boolean hasArrival = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Arrival"));
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasBraveShine = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:BraveShine"));
        boolean hasMindOfSteel = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:MindOfSteel"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasInstall = p.hasPower("FakerMod:InstallArcherPower");

        String textureCall = "MAIN";  // Default texture

        if (hasInstall) {
            textureCall = "INSTALL";


        } else if (hasSaber) {
                if (hasOriginless && hasForge) {
                    textureCall = hasAllKatanas() ? "MURAMASA_SERVANT2" : "MURAMASA_SERVANT";

                } else if (hasRedPendant) {
                        if (hasBraveShine) {
                            textureCall = "ARCHER_SERVANT2";

                        } else if (hasResolution) {
                            textureCall = "ARCHER_SERVANT";

                        } else {
                            textureCall = "FAKER_SERVANT2";
                        }

                } else if (hasMindOfSteel) {
                    textureCall = (hasResolution || hasArrival) ? "ALTER_SERVANT2" : "ALTER_SERVANT";
                } else {
                    textureCall = "FAKER_SERVANT";
                }

        } else if (hasOriginless && hasForge) {
            textureCall = hasAllKatanas() ? "MURAMASA2" : "MURAMASA";

        } else if (hasClassCard) {
            textureCall = (hasResolution || hasArrival) ? "UNBROKEN" : "JACKET";

        } else if (hasRedPendant) {
                if (hasBraveShine) {
                    textureCall = "ARCHER2";

                } else if (hasResolution) {
                    textureCall = "ARCHER";
                }

        } else if (hasMindOfSteel) {
            textureCall = (hasResolution || hasArrival) ? "ALTER2" : "ALTER";

        } else {
            textureCall = "MAIN";
        }

        reloadTexture(textureCall);
        reloadShoulderTexture(textureCall);
        this.isDone = true;
    }
}