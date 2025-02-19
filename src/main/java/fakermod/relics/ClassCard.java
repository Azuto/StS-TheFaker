package fakermod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import fakermod.CharacterFile;
import fakermod.cards.extra.InstallArcher;

import static fakermod.ModFile.makeID;

public class ClassCard extends AbstractEasyRelic {
    public static final String ID = makeID("ClassCard");
    AbstractCard card = new InstallArcher();

    public ClassCard() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, CharacterFile.Enums.FAKER_COLOR);
        tips.add(new CardPowerTip(card));
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new MakeTempCardInHandAction(new InstallArcher()));
    }

    @Override
    public void onVictory() { stopPulse(); }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}