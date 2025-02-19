package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import fakermod.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.saber.SummonSaber;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class Caliburn extends AbstractEasyCard {
    public final static String ID = makeID("Caliburn");

    public Caliburn() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 20;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new SummonSaber();
        tags.add(customTag.PROJECTION);
        tags.add(customTag.SABER);
        tags.add(customTag.MELEE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player != null && !AbstractDungeon.player.hasPower("FakerMod:SummonSaberPower")) {
            this.exhaustOnUseOnce = true;
            this.addToBot(new ApplyPowerAction(p, p, new FrailPower(p, magicNumber, false), magicNumber));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void upp() {
        upgradeDamage(8);
        upgradeMagicNumber(-1);
    }
}