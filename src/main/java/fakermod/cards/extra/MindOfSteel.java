package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import fakermod.CharacterFile;
import fakermod.ModFile;
import fakermod.actions.ChangeImageAction;
import fakermod.actions.PurgeCardAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.Trace;
import fakermod.cards.TraceAlter;
import fakermod.powers.IdealWhitePower;

import java.util.ArrayList;

import static fakermod.ModFile.makeID;

public class MindOfSteel extends AbstractEasyCard {
    public final static String ID = makeID("MindOfSteel");

    public MindOfSteel() {
        super(ID, -2, CardType.CURSE, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new TraceAlter();
        setDisplayRarity(CardRarity.RARE);
        setBannerTexture(ModFile.BANNER_MYTHIC, ModFile.BANNER_MYTHIC_PORTRAIT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) { }

    public void replaceTraceInAllPiles() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractCard cardA = new Trace().makeCopy();
        AbstractCard cardB = new TraceAlter().makeCopy();

        replaceTraceInPile(p.drawPile, cardA, cardB);
        replaceTraceInPile(p.hand, cardA, cardB);
        replaceTraceInPile(p.discardPile, cardA, cardB);
    }

    private void replaceTraceInPile(CardGroup pile, AbstractCard cardA, AbstractCard cardB) {
        ArrayList<AbstractCard> toReplace = new ArrayList<>();

        for (AbstractCard c : pile.group) {
            if (c.cardID.equals(cardA.cardID)) {
                AbstractCard newCard = cardB.makeCopy();
                if (c.upgraded) {
                    newCard.upgrade();
                }
                toReplace.add(c);

                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(newCard));
            }
        }

        for (AbstractCard oldCard : toReplace) {
            AbstractCard newCard = cardB.makeCopy();
            if (oldCard.upgraded) {
                newCard.upgrade();
            }

            if (pile == AbstractDungeon.player.hand) {
                AbstractDungeon.player.hand.moveToExhaustPile(oldCard);
                AbstractDungeon.player.hand.addToTop(newCard);
                newCard.lighten(true);

            } else {
                pile.removeCard(oldCard);
                pile.addToTop(newCard);
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractPlayer p = AbstractDungeon.player;
        replaceTraceInAllPiles();
        this.addToBot(new ApplyPowerAction(p, p, new IdealWhitePower(p, magicNumber)));
        this.addToTop(new PurgeCardAction(this));
        this.addToBot(new ChangeImageAction());
    }

    @Override
    public boolean canUpgrade() { return false; }

    public AbstractCard makeCopy() { return new MindOfSteel(); }

    public void upp() { }
}