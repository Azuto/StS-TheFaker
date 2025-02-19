package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.CustomChangeStanceAction;
import fakermod.stances.RangedStance;

import static fakermod.ModFile.makeID;

public class MindsEye extends AbstractEasyCard {
    public final static String ID = makeID("MindsEye");

    public MindsEye() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.drawPile.isEmpty()) {
            this.cantUseMessage = "There are no cards the deck.";
            return false;
        }
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.drawPile.isEmpty()) {
            AbstractDungeon.gridSelectScreen.open(p.drawPile, 1, "Choose a card to add to your hand", false);
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        AbstractCard selected = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        p.drawPile.moveToHand(selected, p.drawPile);
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    }

                    this.tickDuration();
                }
            });
        }

        this.addToBot(new CustomChangeStanceAction(RangedStance.STANCE_ID));
    }

    public void upp() {
        exhaust = false;
    }
}