package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import fakermod.cards.Avalon;
import fakermod.cards.Caliburn;
import fakermod.cards.saber.Excalibur;
import fakermod.cards.saber.StrikeAir;

import java.util.ArrayList;

public class CommandSealAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private AbstractCard.CardType cardType = null;

    public CommandSealAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    private ArrayList<AbstractCard> cardList() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new StrikeAir());
        list.add(new Excalibur());
        list.add(new Caliburn());
        list.add(new Avalon());

        return list;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = this.cardList();

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], this.cardType != null);

        } else if (!this.retrieveCard) {
            AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard;

            if (disCard != null) {
                disCard = disCard.makeStatEquivalentCopy();
                disCard.setCostForTurn(0);
                disCard.current_x = -1000.0F * Settings.xScale;
                float centerX = (float) Settings.WIDTH / 2.0F;
                float centerY = (float) Settings.HEIGHT / 2.0F;

                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, centerX, centerY));

                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, centerX, centerY));
                }

                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }

            this.retrieveCard = true;
        }

        this.tickDuration();
    }

}