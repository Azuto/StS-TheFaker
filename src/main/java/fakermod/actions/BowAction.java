package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import fakermod.cards.projection.Arrow;

public class BowAction extends AbstractGameAction {
    private boolean upgrade;

    public BowAction(boolean upgraded) {
        this.upgrade = upgraded;
    }

    @Override
    public void update() {
        if (this.upgrade) {
            AbstractCard card = (new Arrow()).makeCopy();
            card.upgrade();
            this.addToTop(new MakeTempCardInHandAction(card));

        } else {
            this.addToTop(new MakeTempCardInHandAction(new Arrow()));
        }

        this.tickDuration();
    }
}