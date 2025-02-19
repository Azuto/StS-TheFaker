package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class TsumukariMuramasa extends AbstractEasyCard {
    public final static String ID = makeID("TsumukariMuramasa");

    public float getTitleFontSize() {
        return 17.0F;
    }

    public TsumukariMuramasa() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CharacterFile.Enums.FAKERE_COLOR);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = 7;
        exhaust = true;
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.RARE);
        getTitleFontSize();
    }

    public static int countCards() {
        int count = 0;

        for (int i = 0; i < AbstractDungeon.player.exhaustPile.group.size(); i++) {
            count++;
        }

        return count;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new TalkAction(true, "Take this! This is my NL Tsumukari Muramasa—!!", 6.0f, 4.0f));
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public AbstractCard makeCopy() {
        return new TsumukariMuramasa();
    }

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upp() { }
}