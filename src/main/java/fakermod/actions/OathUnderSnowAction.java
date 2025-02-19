package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import fakermod.cards.projection.BladeDance;

public class OathUnderSnowAction extends AbstractGameAction {

    public OathUnderSnowAction() {
        this.duration = 0.5F;
    }

    @Override
    public void update() {
        for (int i = 0; i < AbstractDungeon.player.drawPile.group.size(); i++) {
            AbstractDungeon.player.drawPile.moveToExhaustPile(AbstractDungeon.player.drawPile.getTopCard());
        }
        for (int i = 0; i < AbstractDungeon.player.exhaustPile.group.size(); i++) {
            addToBot(new MakeTempCardInDrawPileAction(new BladeDance(), 1, true, true));
        }

        this.tickDuration();
    }
}