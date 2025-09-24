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
 *  |X
 *  |XX
 */
public class LUBlock extends Block {

    public LUBlock() {
        super(2,3);
    }

    @Override
    public void printOntoMap(Coord pos,char[][] m) {
        char c = getRepresentation();
        m[pos.y][pos.x] = c;
        m[pos.y][pos.x+1] = c;
        m[pos.y+1][pos.x] = c;
        m[pos.y+2][pos.x] = c;
        m[pos.y+2][pos.x+1] = c;
    }

    @Override
    public Color getColor() {
        return Color.magenta;
    }

    @Override
    public String toString() {
        return "[LU]";
    }

    @Override
    public Rect[] getRectSet(Coord pos) {
        return new Rect[]{
                new Rect(pos.x,pos.y  ,2,1),
                new Rect(pos.x,pos.y  ,1,3),
                new Rect(pos.x,pos.y+2,2,1)
        };
    }

    @Override
    public char getRepresentation() {
        return 'C';
    }

    @Override
    public void addAlts(Coord pos,Board board, ArrayList<IMove> alts) {
        //oben
        {
            if(pos.y > 0 && !board.intersectsWithRect(new Rect(pos.x,pos.y-1,2,1)) && board.getBlockCovering(pos.x + 1, pos.y + 1) == null)
                alts.add(new Move(this,0,-1,pos+": LUBlock Up"));
        }
        //links
        {
            if(pos.x > 0 && !board.intersectsWithRect(new Rect(pos.x-1,pos.y,1,3)))
                alts.add(new Move(this,-1,0,pos+": LUBlock Left"));
        }
        //rechts
        {
            if(pos.x+width < board.width && board.getBlockCovering(pos.x + 1, pos.y + 1) == null && board.getBlockCovering(pos.x + 2, pos.y) == null && board.getBlockCovering(pos.x + 2, pos.y + 2) == null)
                alts.add(new Move(this,1,0,pos+": LUBlock Right"));
        }
        //unten
        {
            if(pos.y+height < board.height && board.getBlockCovering(pos.x + 1, pos.y + 1) == null && !board.intersectsWithRect(new Rect(pos.x,pos.y+height,width,1)))
                alts.add(new Move(this,0,1,pos+": LUBlock Down"));
        }
        // hier gibt es die zusätzliche besonderheit, dass wenn in der Auskerbung ein LBlock steckt,
        // wir den ggf. mitbewegen können (nur nach oben, unten, rechts)
        Block insert = board.getBlockOriginatingAt(pos.x+1,pos.y+1);
        if(insert instanceof LBlock) {
	        Coord is = board.getBlockCoord(insert);
            //vielleicht können wir die zwei nach rechts bewegen?
            if(is.x+insert.width < board.width) {
                //sind die felder rechts davon frei?
                if(
                        board.getBlockCovering(pos.x + 2, pos.y) == null &&
                        board.getBlockCovering(is.x + insert.width, is.y) == null &&
                        board.getBlockCovering(pos.x + 2, pos.y + 2) == null) {
                    MultiMove mm = new MultiMove();
                    mm.add(insert,+1,0);
                    mm.add(this,+1,0);
                    alts.add(mm);
                }
            }
            //vielleicht nach oben?
            if(pos.y > 0) {
                //sind die felder oben davon frei?
                if(
                        board.getBlockCovering(pos.x, pos.y - 1) == null &&
                        board.getBlockCovering(pos.x + 1, pos.y - 1) == null &&
                        board.getBlockCovering(is.x + 1, is.y - 1) == null) {
                    MultiMove mm = new MultiMove(this,0,-1);
                    mm.add(insert,0,-1);
                    alts.add(mm);
                }
            }
            //vielleicht nach unten?
            if(pos.y+3 < board.height) {
                //sind die felder darunter frei?
                if(
                        board.getBlockCovering(pos.x, pos.y + 3) == null &&
                        board.getBlockCovering(pos.x + 1, pos.y + 3) == null &&
                        board.getBlockCovering(is.x + 1, is.y + 1) == null) {
                    MultiMove mm = new MultiMove(this,0,+1);
                    mm.add(insert,0,+1);
                    alts.add(mm);
                }
            }
        }
    }

}
