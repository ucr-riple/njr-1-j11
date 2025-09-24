package com.github.users.schlabberdog.blocks.r010;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.moves.IMove;
import com.github.users.schlabberdog.blocks.board.moves.Move;
import com.github.users.schlabberdog.blocks.board.moves.MultiMove;
import com.github.users.schlabberdog.blocks.mccs.Coord;
import com.github.users.schlabberdog.blocks.mccs.Rect;

import java.awt.*;
import java.util.ArrayList;


/*  |XX
 *  | X
 *  |XX
 */
public class RUBlock extends Block {

    public RUBlock() {
        super(2,3);
    }

    @Override
    public void printOntoMap(Coord pos,char[][] m) {
        char c = getRepresentation();
        m[pos.y][pos.x] = c;
        m[pos.y][pos.x+1] = c;
        m[pos.y+1][pos.x+1] = c;
        m[pos.y+2][pos.x] = c;
        m[pos.y+2][pos.x+1] = c;
    }

    @Override
    public Color getColor() {
        return Color.blue;
    }


    @Override
    public String toString() {
        return "[RU]";
    }

    @Override
    public Rect[] getRectSet(Coord pos) {
        return new Rect[]{
                new Rect(pos.x  ,pos.y  ,2,1),
                new Rect(pos.x+1,pos.y  ,1,3),
                new Rect(pos.x  ,pos.y+2,2,1)
        };
    }

    @Override
    public char getRepresentation() {
        return 'U';
    }

    @Override
    public void addAlts(Coord pos, Board board, ArrayList<IMove> alts) {
        //oben
        {
            if(pos.y > 0 && !board.intersectsWithRect(new Rect(pos.x,pos.y-1,2,1)) && board.getBlockCovering(pos.x, pos.y + 1) == null)
                alts.add(new Move(this,0,-1,pos+": RUBlock Up"));
        }
        //links
        {
            if(pos.x > 0 && board.getBlockCovering(pos.x - 1, pos.y) == null && board.getBlockCovering(pos.x, pos.y + 1) == null && board.getBlockCovering(pos.x - 1, pos.y + 2) == null)
                alts.add(new Move(this,-1,0,pos+": RUBlock Left"));
        }
        //rechts
        {
            if(pos.x+width < board.width && !board.intersectsWithRect(new Rect(pos.x+2,pos.y,1,3)))
                alts.add(new Move(this,1,0,pos+": RUBlock Right"));
        }
        //unten
        {
            if(pos.y+height < board.height && board.getBlockCovering(pos.x, pos.y + 1) == null && !board.intersectsWithRect(new Rect(pos.x,pos.y+3,2,1)))
                alts.add(new Move(this,0,1,pos+": RUBlock Down"));
        }
        // hier gibt es die zusätzliche besonderheit, dass wenn in der Auskerbung ein LBlock steckt,
        // wir den ggf. mitbewegen können (nur nach oben, unten, links)

        Block insert = board.getBlockCovering(pos.x,pos.y+1);
        if(insert instanceof LBlock) {
			Coord is = board.getBlockCoord(insert);
            //vielleicht können wir die zwei nach links bewegen?
            if(is.x > 0) {
                //sind die felder links davon frei?
                if(
                        board.getBlockCovering(pos.x - 1, pos.y) == null &&
                        board.getBlockCovering(is.x - 1, is.y) == null &&
                        board.getBlockCovering(pos.x - 1, pos.y + 2) == null) {
                    MultiMove mm = new MultiMove(this,-1,0);
                    mm.add(insert,-1,0);
                    alts.add(mm);
                }
            }
            //vielleicht nach oben?
            if(pos.y > 0) {
                //sind die felder oben davon frei?
                if(
                        board.getBlockCovering(pos.x, pos.y - 1) == null &&
                        board.getBlockCovering(pos.x + 1, pos.y - 1) == null &&
                        board.getBlockCovering(is.x, is.y - 1) == null) {
                    MultiMove mm = new MultiMove(this,0,-1);
                    mm.add(insert,0,-1);
                    alts.add(mm);
                }
            }
            //vielleicht nach unten?
            if(pos.y+height < board.height) {
                //sind die felder darunter frei?
                if(
                        board.getBlockCovering(pos.x, pos.y + height) == null &&
                        board.getBlockCovering(pos.x + 1, pos.y + height) == null &&
                        board.getBlockCovering(is.x, is.y + 1) == null) {
                    MultiMove mm = new MultiMove(this,0,+1);
                    mm.add(insert,0,+1);
                    alts.add(mm);
                }
            }
        }
    }
}
