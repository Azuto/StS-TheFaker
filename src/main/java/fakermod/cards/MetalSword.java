package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class MetalSword extends AbstractEasyCard {
    public final static String ID = makeID("MetalSword");

    public MetalSword() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 9;
        baseMagicNumber = magicNumber = 1;
        tags.add(customTag.MELEE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        drawNextAttack();
    }

    public void drawNextAttack() {
        CardGroup drawPile = AbstractDungeon.player.drawPile;

        for (AbstractCard card : drawPile.group) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                drawPile.moveToHand(card);
                return;
            }
        }
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBaseCost(0);
    }
}