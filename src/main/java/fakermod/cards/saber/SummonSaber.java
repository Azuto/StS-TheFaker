package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.ModFile;
import fakermod.actions.ChangeImageAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.orbs.AP;
import fakermod.powers.SummonSaberPower;
import fakermod.tags.customTag;

import java.util.ArrayList;
import java.util.Collections;

import static fakermod.ModFile.makeID;
import static fakermod.ModFile.makeImagePath;

public class SummonSaber extends AbstractEasyCard {
    public final static String ID = makeID("SummonSaber");
    public static final String SABER = makeImagePath("512/power_Saber.png");
    public static final String SABER_L= makeImagePath("1024/power_Saber.png");

    public boolean usedThisCombat = false;

    public SummonSaber() {
        super(ID, -2, CardType.STATUS, CardRarity.BASIC, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        purgeOnUse = true;
        tags.add(customTag.SABER);
        tags.add(customTag.SPECIAL);
        setBannerTexture(ModFile.BANNER_BLANK, ModFile.BANNER_BLANK_PORTRAIT);
        setPortraitTextures(SABER, SABER_L);
    }

    private void addCards() {
        this.addToBot(new MakeTempCardInDrawPileAction(new SaberArts(), 2, true, false, false));
        this.addToBot(new MakeTempCardInDrawPileAction(new SaberBuster(), 2, true, false, false));
        this.addToBot(new MakeTempCardInDrawPileAction(new SaberQuick(), 1, true, false, false));
        this.addToBot(new MakeTempCardInDrawPileAction(new ManaTransfer(), 1, true, false, false));
        this.addToBot(new MakeTempCardInDrawPileAction(new StrikeAir(), 1, true, false, false));
    }

    private void addSkills() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Charisma());
        list.add(new Instinct());
        list.add(new ManaBurst());
        list.add(new DragonsCore());
        list.add(new ShiningPath());
        list.add(new InvisibleAir());
        Collections.shuffle(list);

        AbstractCard c;
        for (int i = 0; i < 3 ; i++) {
            c = list.get(i);
            this.addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false, false));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = p.maxOrbs; i < 3; i++) {
            p.increaseMaxOrbSlots(1, false);
            p.channelOrb(new AP());
        }
        this.addToTop(new ApplyPowerAction(p, p, new SummonSaberPower(p, p, 1)));
        this.addToBot(new ChangeImageAction());
        addCards();
        addSkills();
        p.drawPile.shuffle();
        usedThisCombat = true;
    }

    public AbstractCard makeCopy() {
        return new SummonSaber();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {}
}