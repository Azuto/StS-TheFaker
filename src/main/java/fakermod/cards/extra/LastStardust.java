package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import fakermod.CharacterFile;
import fakermod.ModFile;
import fakermod.actions.PurgeCardAction;
import fakermod.cards.AbstractEasyCard;

import static fakermod.ModFile.makeID;

public class LastStardust extends AbstractEasyCard {
    public final static String ID = makeID("LastStardust");
 
    public LastStardust() {
        super(ID,  -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 10;
        setDisplayRarity(CardRarity.RARE);
        setBannerTexture(ModFile.BANNER_MYTHIC, ModFile.BANNER_MYTHIC_PORTRAIT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void triggerWhenDrawn() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        this.addToBot(new HealAction(p, p, secondMagic));
    }

    @Override
    public boolean canUpgrade() { return false; }

    public AbstractCard makeCopy() { return new LastStardust(); }

        @Override
    public void upp() { }
}