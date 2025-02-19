package fakermod.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class RapidStrike extends AbstractEasyCard {
    public final static String ID = makeID("RapidStrike");
    private static int COST = 1;

    public RapidStrike() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.STRIKE);
        tags.add(customTag.MELEE);
        MultiCardPreview.add(this, new Strike(), new FollowUpStrike());
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (hasPlayedStrike()) {
            this.costForTurn = 0;

        } else {
            this.costForTurn = COST;
        }
    }

    private boolean hasPlayedStrike() {
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.hasTag(CardTags.STRIKE)) {
                return true;
            }
        }

        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void upp() {
        upgradeDamage(4);
    }
}