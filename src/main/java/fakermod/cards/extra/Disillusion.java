package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.DisillusionPower;

import static fakermod.ModFile.makeID;

public class Disillusion extends AbstractEasyCard {
    public final static String ID = makeID("Disillusion");

    public Disillusion() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
        setDisplayRarity(CardRarity.RARE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DisillusionPower(p, secondMagic), secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
            }
}