package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.ChangeImageAction;
import fakermod.actions.CommandSealAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.orbs.AP;
import fakermod.powers.SummonSaberPower;

import java.util.ArrayList;
import java.util.Collections;

import static fakermod.ModFile.makeID;

public class CommandSealCard extends AbstractEasyCard {
    public final static String ID = makeID("CommandSealCard");

    public CommandSealCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        purgeOnUse = true;
        cardsToPreview = new SummonSaber();
        setDisplayRarity(CardRarity.RARE);
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

    private void removeSaber() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals("FakerMod:SummonSaber")) {
                p.hand.removeCard(c);
                break;
            }
        }

        for (AbstractCard c : p.drawPile.group) {
            if (c.cardID.equals("FakerMod:SummonSaber")) {
                p.drawPile.removeCard(c);
                break;
            }
        }

        for (AbstractCard c : p.discardPile.group) {
            if (c.cardID.equals("FakerMod:SummonSaber")) {
                p.discardPile.removeCard(c);
                break;
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = p.maxOrbs; i < 3; i++) {
            p.increaseMaxOrbSlots(1, false);
            p.channelOrb(new AP());
        }

        this.addToTop(new ApplyPowerAction(p, p, new SummonSaberPower(p, p, 1)));
        this.addToBot(new ChangeImageAction());
        removeSaber();
        addCards();
        addSkills();
        p.drawPile.shuffle();
        this.addToBot(new CommandSealAction());
    }

    public AbstractCard makeCopy() {
        return new CommandSealCard();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() { }
}