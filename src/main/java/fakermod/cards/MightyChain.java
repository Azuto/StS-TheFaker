package fakermod.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import fakermod.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.List;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class MightyChain extends AbstractEasyCard {
    public final static String ID = makeID("MightyChain");

    AbstractCard q = new Quick();
    AbstractCard a = new Arts();
    AbstractCard b = new Buster();

    public MightyChain() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
        MultiCardPreview.add(this, q, a, b);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : Arrays.asList(q, a, b)) {
            if (this.upgraded) {
                card.upgrade();
            }
            card.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(card));
        }
    }

    @Override
    public void upp() {
        MultiCardPreview.clear(this);
        q.upgrade();
        a.upgrade();
        b.upgrade();
        MultiCardPreview.add(this, q, a, b);
    }
}