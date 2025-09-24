package com.github.users.schlabberdog.blocks.ui;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.mccs.Coord;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BoardView extends JComponent {
    private final Board board;

    public BoardView(Board b) {
		assert(b != null);

        board = b;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200,200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke stroke2 = new BasicStroke(2.0f);

	    g2.setColor(Color.black);

        Map<Block,Coord> save = board.getSave();

        //grid zeichnen
        double boxWidth  = (getWidth()-7) / board.width;
        double boxHeight = (getHeight()-7) / board.height;

        double xOffset = 3;
        double yOffset = 3;
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                g2.setStroke(stroke2);
                g2.drawRect((int)xOffset,(int)yOffset,(int)boxWidth,(int)boxHeight);

                xOffset += boxWidth;
            }
            yOffset += boxHeight;
            xOffset = 3;
        }

        //blocks zeichnen
        for (Map.Entry<Block,Coord> e : save.entrySet()) {
			Block blk = e.getKey();
            g2.setColor(blk.getColor());
            g2.fill(blk.drawShape(e.getValue(), boxWidth, boxHeight));
        }

    }
}
