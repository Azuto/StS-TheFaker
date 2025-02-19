package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fakermod.ModFile.makeID;

public class Reload extends AbstractEasyCard {
    public final static String ID = makeID("Reload");

    public Reload() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void atTurnStart() {
        if (this.upgraded) { this.retain = true; }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = p.hand.size();

        for (int i = 0; i < count; ++i) {
            this.addToBot(new ExhaustAction(1, true, true));
        }
        this.addToBot(new DrawCardAction(count));
    }

    public void upp() {
        retain = true;
    }
}