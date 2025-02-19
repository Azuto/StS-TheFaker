package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import fakermod.cards.projection.Caladbolg;
import fakermod.cards.projection.Hrunting;
import fakermod.cards.projection.KanshouBakuyaOveredge;
import fakermod.cards.projection.RhoAias;

import java.util.ArrayList;

public class TraceFractalAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private AbstractCard.CardType cardType = null;
    private boolean upgraded;

    public TraceFractalAction(boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        this.upgraded = upgraded;
    }

    public TraceFractalAction(boolean upgraded, int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.upgraded = upgraded;
    }

    private ArrayList<AbstractCard> generateUNCOMMON() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new RhoAias());
        list.add(new Hrunting());
        list.add(new Caladbolg());
        list.add(new KanshouBakuyaOveredge());

        if (amount >= 3) {
            for (AbstractCard card : list) {
                card.upgrade();
            }
        }

        return list;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> rarityList = generateUNCOMMON();

        //Discovery action
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(rarityList, CardRewardScreen.TEXT[1], this.cardType != null);

        } else if (!this.retrieveCard) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard;

                if (disCard != null) {
                    disCard = disCard.makeStatEquivalentCopy();
                    if (this.upgraded) { disCard.setCostForTurn(0); }

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