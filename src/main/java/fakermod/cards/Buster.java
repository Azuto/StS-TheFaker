package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.extra.ExtraAttack;
import fakermod.powers.BusterPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Buster extends AbstractEasyCard {
    public final static String ID = makeID("Buster");

    public Buster() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 2;
        tags.add(customTag.MELEE);
        tags.add(customTag.COMMAND);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size()).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.addToBot(new ApplyPowerAction(p, p, new BusterPower(p, p, magicNumber)));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}