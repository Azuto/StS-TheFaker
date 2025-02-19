package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.saber.SummonSaber;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class ProjectedExcalibur extends AbstractEasyCard {
    public final static String ID = makeID("ProjectedExcalibur");

    public ProjectedExcalibur() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 40;
        purgeOnUse = true;
        cardsToPreview = new SummonSaber();
        tags.add(customTag.PROJECTION);
        tags.add(customTag.SABER);
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (AbstractDungeon.player != null && !AbstractDungeon.player.hasPower("FakerMod:SummonSaberPower")) {
            this.addToBot(new LoseHPAction(p,p, 99999));
        }
    }

    public AbstractCard makeCopy() {
        return new ProjectedExcalibur();
    }

    @Override
    public void upp() {
        upgradeDamage(10);
    }
}