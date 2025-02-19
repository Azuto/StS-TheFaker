package fakermod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fakermod.CharacterFile;
import fakermod.cards.extra.Resolution;

import static fakermod.ModFile.makeID;

public class RedPendant extends AbstractEasyRelic {
    public static final String ID = makeID("RedPendant");
    AbstractCard card = new Resolution();
    AbstractPlayer p = AbstractDungeon.player;

    public RedPendant() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, CharacterFile.Enums.FAKER_COLOR);
        tips.add(new CardPowerTip(card));

    }

    @Override
    public String getUpdatedDescription() {
        if (this.usedUp) {
            return DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[0];
        }
    }

    @Override
    public void setCounter(int setCounter) {
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }
    }

    @Override
    public void onTrigger() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        int healAmt = AbstractDungeon.player.maxHealth / 4;
        if (healAmt < 1) {
            healAmt = 1;
        }

        flash();
        AbstractDungeon.player.heal(healAmt, true);
        p.masterDeck.group.add(new Resolution());
        tips.clear();

        this.setCounter(-2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RedPendant();
    }
}