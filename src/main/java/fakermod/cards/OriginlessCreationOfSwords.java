package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.ChangeImageAction;
import fakermod.cards.extra.TsumukariMuramasa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static fakermod.ModFile.makeID;

public class OriginlessCreationOfSwords extends AbstractEasyCard {
    public final static String ID = makeID("OriginlessCreationOfSwords");
    public float getTitleFontSize() {
        return 18.0F;
    }

    public OriginlessCreationOfSwords() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
        cardsToPreview = new TsumukariMuramasa();
        getTitleFontSize();
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

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : p.discardPile.group) {
            this.addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
        }
        this.addToBot(new MakeTempCardInHandAction(new TsumukariMuramasa()));
        if (hasAllKatanas() || p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"))) {
            this.addToBot(new ChangeImageAction());
        }
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}