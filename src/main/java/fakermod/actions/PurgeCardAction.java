package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PurgeCardAction  extends AbstractGameAction {
    private AbstractCard cardToPurge;

    public PurgeCardAction(AbstractCard card) {
        this.cardToPurge = card;
    }
    @Override
    public void update() {
        if (cardToPurge != null) {
            AbstractDungeon.player.hand.group.remove(cardToPurge);
            AbstractDungeon.player.drawPile.group.remove(cardToPurge);
            AbstractDungeon.player.discardPile.group.remove(cardToPurge);
            AbstractDungeon.player.exhaustPile.group.remove(cardToPurge);
        }

        this.isDone = true;
    }
}