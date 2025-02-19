package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import fakermod.cards.projection.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TraceAlterAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private AbstractCard.CardType cardType = null;

    public TraceAlterAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 0;
    }

    public TraceAlterAction(int powerStack) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = powerStack;
    }

    private ArrayList<AbstractCard> generateCOMMON() {
        ArrayList<AbstractCard> list = new ArrayList<>(Arrays.asList(
                new KanshouBakuyaAlter(),
                new Sword(),
                new KanshouBakuyaRevolver(),
                new KanshouBakuyaCombined()

                ));

        if (amount >= 1) {
            for (AbstractCard card : list) {
                card.upgrade();
            }
        }

        return list;
    }

    private ArrayList<AbstractCard> generateUNCOMMON() {
        ArrayList<AbstractCard> list = generateCOMMON();

        list.addAll(Arrays.asList(
                new Hrunting(),
                new Caladbolg(),
                new KanshouBakuyaOveredge()
        ));

        if (amount >= 3) {
            for (AbstractCard card : list) {
                card.upgrade();
            }
        }

        return list;
    }

    private ArrayList<AbstractCard> generateRARE() {
        ArrayList<AbstractCard> list = generateUNCOMMON();

        list.addAll(Arrays.asList(
                new OriginBullet()
        ));

        if (amount >= 5) {
            for (AbstractCard card : list) {
                card.upgrade();
            }
        }

        return list;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> rarityList =
                (this.amount >= 4) ? generateRARE() :
                        (this.amount >= 2) ? generateUNCOMMON() :
                                generateCOMMON();

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(rarityList, CardRewardScreen.TEXT[1], this.cardType != null);

        } else if (!this.retrieveCard) {
            AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard;

            if (disCard != null) {
                disCard = disCard.makeStatEquivalentCopy();
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