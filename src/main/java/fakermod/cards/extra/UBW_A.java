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
import fakermod.cards.projection.UnlimitedBladeBurst;
import fakermod.powers.UBWAPower;

import static fakermod.ModFile.makeID;

public class UBW_A extends AbstractEasyCard {
    public final static String ID = makeID("UBW_A");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] VERSE = cardStrings.EXTENDED_DESCRIPTION;

    public float getTitleFontSize() {
        return 18.0F;
    }
 
    public UBW_A() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        getTitleFontSize();
        setDisplayRarity(CardRarity.RARE);
        cardsToPreview = new UnlimitedBladeBurst();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new TalkAction(true, VERSE[0], 4.0f, 2.0f));
        this.addToBot(new ApplyPowerAction(p, p, new UBWAPower(p)));
        this.addToBot(new MakeTempCardInHandAction(new UnlimitedBladeBurst()));
        if (p.hasPower("FakerMod:IncantationPower")){
            addToBot(new RemoveSpecificPowerAction(p, p, "FakerMod:IncantationPower"));
        }
    }

    @Override
    public boolean canUpgrade() { return false; }

    public AbstractCard makeCopy() { return new UBW_A(); }

        @Override
    public void upp() { }
}