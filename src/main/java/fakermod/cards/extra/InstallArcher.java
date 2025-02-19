package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.ModFile;
import fakermod.actions.ChangeImageAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.InstallArcherPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.ModFile.makeImagePath;

public class InstallArcher extends AbstractEasyCard {
    public final static String ID = makeID("InstallArcher");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] VERSE = cardStrings.EXTENDED_DESCRIPTION;
    public static final String CARD = makeImagePath("512/power_Card.png");
    public static final String CARD_L= makeImagePath("1024/power_Card.png");
 
    public InstallArcher() {
        super(ID, -2, CardType.STATUS, CardRarity.BASIC, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 5;
        retain = true;
        purgeOnUse = true;
        cardsToPreview = new GradationAir();
        tags.add(customTag.SPECIAL);
        setBannerTexture(ModFile.BANNER_BLANK,ModFile.BANNER_BLANK_PORTRAIT);
        setPortraitTextures(CARD, CARD_L);
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.maxHealth <= 3) {
            cantUseMessage = VERSE[1];
            return false;

        } else {
            return true;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new TalkAction(true, VERSE[0], 3.0f, 2.0f));
        p.decreaseMaxHealth(3);
        this.addToBot(new ApplyPowerAction(p, p, new InstallArcherPower(p, p, baseMagicNumber), baseMagicNumber));
        this.addToBot(new MakeTempCardInHandAction(new GradationAir(), 1));
        this.addToBot(new ChangeImageAction());
    }

    public AbstractCard makeCopy() {
        return new InstallArcher();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() { }
}