package fakermod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import fakermod.CharacterFile;
import fakermod.actions.ChangeImageAction;
import fakermod.cards.extra.ExtraAttack;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class CommandDeck extends AbstractEasyRelic {
    public static final String ID = makeID("CommandDeck");
    AbstractCard card = new ExtraAttack();

    public CommandDeck() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.FAKER_COLOR);
        tips.add(new CardPowerTip(card));
    }

    @Override
    public void atPreBattle() {
        this.addToBot(new ChangeImageAction());
    }


    @Override
    public void atTurnStart() {
        counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(customTag.COMMAND)) {
            ++counter;
            if (counter == 4) {
                counter = 1;
            }

            if (counter == 3) {
                flash();
                addToBot(new MakeTempCardInHandAction(new ExtraAttack(), 1));
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}