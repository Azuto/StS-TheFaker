package fakermod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class MyoujingiriMuramasa extends AbstractEasyCard {
    public final static String ID = makeID("MyoujingiriMuramasa");

    public float getTitleFontSize() {
        return 17.0F;
    }

    public static int countCards() {
        int count = 0;

        for (int i = 0; i < AbstractDungeon.player.exhaustPile.group.size(); i++) {
            count++;
        }

        return count;
    }

    public MyoujingiriMuramasa() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = 7;
        tags.add(customTag.MELEE);
        getTitleFontSize();
    }

    @Override
    public void triggerOnExhaust() {
        if (this.upgraded) {
            this.addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile,
                    (card) -> card.cardID.equals(this.cardID), 1));
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upp() { }
}