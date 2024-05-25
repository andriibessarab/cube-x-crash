package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public abstract class Screen extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    protected final int PANEL_WIDTH;
    protected int PANEL_HEIGHT;

    public Screen(int width, int height) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        initJPanel();
    }

    protected void initJPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public abstract void update();
}
