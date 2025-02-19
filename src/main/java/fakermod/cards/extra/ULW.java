package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.OriginBulletPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class ULW extends AbstractEasyCard {
    public final static String ID = makeID("ULW");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] VERSE = cardStrings.EXTENDED_DESCRIPTION;

    public float getTitleFontSize() {
        return 18.0F;
    }

    public ULW() {
        super(ID, 5, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERE_COLOR);
        baseDamage = 1;
        retain = true;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        setDisplayRarity(CardRarity.RARE);
        getTitleFontSize();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.cardID.equals(makeID("OriginBullet"))) {
                return true;
            }
        }

        this.cantUseMessage = "There is no mark left by Origin Bullet.";
        return false;
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(OriginBulletPower.POWER_ID)) {
            this.addToBot(new TalkAction(true, VERSE[0], 4.0f, 2.0f));
            for (int i = 0; i < p.drawPile.group.size() / 3; i++) {
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    public void upp() {
        upgradeBaseCost(4);
    }
}