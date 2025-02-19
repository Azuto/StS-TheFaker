package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.projection.OathUnderSnow;
import fakermod.powers.UBWBPower;

import static fakermod.ModFile.makeID;

public class UBW_B extends AbstractEasyCard {
    public final static String ID = makeID("UBW_B");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] VERSE = cardStrings.EXTENDED_DESCRIPTION;

    public float getTitleFontSize() {
        return 18.0F;
    }
 
    public UBW_B() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        getTitleFontSize();
        setDisplayRarity(CardRarity.RARE);
        cardsToPreview = new OathUnderSnow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new TalkAction(true, VERSE[0], 4.0f, 2.0f));
        this.addToBot(new ApplyPowerAction(p, p, new UBWBPower(p)));
        this.addToBot(new MakeTempCardInHandAction(new OathUnderSnow()));
        if (p.hasPower("FakerMod:IncantationPower")){
            this.addToBot(new RemoveSpecificPowerAction(p, p, "FakerMod:IncantationPower"));
        }
    }

    @Override
    public boolean canUpgrade() { return false; }

    public AbstractCard makeCopy() { return new UBW_B(); }

        @Override
    public void upp() { }
}