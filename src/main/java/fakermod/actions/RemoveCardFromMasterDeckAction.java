package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class RemoveCardFromMasterDeckAction extends AbstractGameAction {
    private AbstractCard card;

    public RemoveCardFromMasterDeckAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.masterDeck.contains(card)) {
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card));
            AbstractDungeon.player.masterDeck.removeCard(card);
        }

        this.isDone = true;
    }
}