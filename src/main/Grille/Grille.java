package main.Grille;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Ecosysteme;

public class Grille extends JPanel {
	private static final long serialVersionUID = 1L;
	private int nbCasesL, nbCasesH;
	private int nbPixelCoteCase;
	private CaseGrille[][] m;
	private int cycle;
	private boolean contoure;
	private JFrame window;

	/**
	 * Constructeur.
	 * 
	 * @param nbCasesL        La largeur (en nombre de cases) de la grille.
	 * @param nbCasesH        La hauteur (en nombre de cases) de la grille.
	 * @param nbPixelCoteCase Nb de Pixel d'une case de la grille
	 **/
	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase, boolean contoure) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;
		this.setContoure(false);

		window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase, nbCasesH * nbPixelCoteCase + nbPixelCoteCase);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);
		window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE)
					Ecosysteme.simulate = !Ecosysteme.simulate;
				if (keyCode == KeyEvent.VK_S)
					setContoure(!isContoure());
			}
		});

		this.m = new CaseGrille[nbCasesL][nbCasesH];
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++)
				m[i][j] = new CaseGrille();
	}

	public void redessine() {
		repaint();
	}

	public void colorieFond(int i, int j, Color c) {
		m[i][j].setCouleur(c);
	}

	public void iconFond(int i, int j, Image icon) {
		m[i][j].setIcon(icon);
	}

	public void addDisque(Image icon, int i, int j, int rayon, Color c) {
		m[i][j].addDisque(rayon, c, icon);
	}

	public void resetlDisques(int i, int j) {
		m[i][j].resetlDisques();
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	public JFrame getWindow() {
		return window;
	}

	@Override
	// Fonction d'affichage de la grille appelée par repaint
	protected void paintComponent(Graphics g) {
		// Colorie les cases de casesAColorier
		super.paintComponent(g);
		int i, j;
		g.setFont(new Font("Sans-serif", Font.BOLD, 20));
		g.drawString("Cycle: " + cycle, 20, 25);
		g.drawString("Grille (Appuyer sur S): " + contoure, 700, 25);
		String etat = "";
		if (Ecosysteme.simulate)
			etat += "▶ en cours";
		else
			etat += "⏸ en pause";
		g.drawString("Etat (Appuyer sur espace): " + etat, 200, 25);
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++) {
				int cellX = (i * nbPixelCoteCase);
				int cellY = nbPixelCoteCase / 2 + (j * nbPixelCoteCase);
				// g.setColor(m[i][j].getCouleur());
				// g.fillRect(cellX, cellY, nbPixelCoteCase, nbPixelCoteCase);

				g.drawImage(m[i][j].getIcon(), cellX, cellY, null);

				// Place des disques
				int x = 15;
				for (Disque d : m[i][j].lDisques) {
					// g.setColor(d.getCouleur());
					// g.fillOval(cellX + 5, cellY + 5 + x, d.getRayon(), d.getRayon());
					// ICONE CENTRE g.drawImage(d.getIcon(), cellX
					// +nbPixelCoteCase/2-d.getRayon()/2, cellY +nbPixelCoteCase/2-d.getRayon()/2,
					// d.getRayon(), d.getRayon(), null);
					
					int rayon = d.getRayon();
					/*
					if (rayon > nbPixelCoteCase)
						rayon = nbPixelCoteCase;
					*/
					g.drawImage(d.getIcon(), cellX + x - rayon / 2, cellY + x - rayon / 2, rayon, rayon, null);
					x += 15;
				}
			}

		// Redessine la grille
		if (contoure) {
			for (i = 0; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
				g.drawLine(i, nbPixelCoteCase / 2, i, nbCasesH * nbPixelCoteCase + nbPixelCoteCase / 2);
			}
			for (j = 0; j <= nbCasesH * nbPixelCoteCase; j += nbPixelCoteCase) {
				g.drawLine(0, j + nbPixelCoteCase / 2, nbCasesL * nbPixelCoteCase, j + nbPixelCoteCase / 2);
			}
		}
	}

	public boolean isContoure() {
		return contoure;
	}

	public void setContoure(boolean contoure) {
		this.contoure = contoure;
	}

}
