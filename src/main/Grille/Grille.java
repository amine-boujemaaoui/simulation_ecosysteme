package main.Grille;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
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
	public Random r = new Random();
	private Ecosysteme ecosysteme;
	private int caseX, caseY;
	private boolean showCase = false;
	private int decalageAffichage = 100;

	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase, boolean contoure, Ecosysteme ecosysteme) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;
		this.ecosysteme = ecosysteme;
		this.setContoure(false);

		window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 16, nbCasesH * nbPixelCoteCase + 139);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setTitle("Simulation ecosysteme");
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					Ecosysteme.simulate = !Ecosysteme.simulate;
				}
				if (keyCode == KeyEvent.VK_S && showCase == false) {
					setContoure(!isContoure());
					ecosysteme.redessine();
				}
				if (keyCode == KeyEvent.VK_ENTER)
					ecosysteme.nextCycle();
				if (keyCode == KeyEvent.VK_UP) {
					ecosysteme.augmenterVistesseSimulation();
					ecosysteme.redessine();
				}
				if (keyCode == KeyEvent.VK_DOWN) {
					ecosysteme.diminuerVistesseSimulation();
					ecosysteme.redessine();
				}
				if (keyCode == KeyEvent.VK_R) {
					ecosysteme.initSimulation();
					ecosysteme.redessine();
				}

			}

		});

		window.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int mouseCode = e.getButton();
				if (mouseCode == MouseEvent.BUTTON1) {
					caseX = e.getX() / nbPixelCoteCase;
					caseY = e.getY() / nbPixelCoteCase - 128 / nbPixelCoteCase;
					if (caseX >= 0 && caseX < nbCasesL && caseY >= 0 && caseY < nbCasesH) {
						showCase = !showCase;
						if (contoure)
							setContoure(false);
						ecosysteme.redessine();
					} else if (e.getX() < 105 && e.getY() < 127)
						Ecosysteme.simulate = !Ecosysteme.simulate;
					else if (e.getX() > 105 && e.getX() < 205 && e.getY() < 127) {
						ecosysteme.initSimulation();
						ecosysteme.redessine();
					}

				}
			}

		});

		this.m = new CaseGrille[nbCasesL][nbCasesH];
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++)
				m[i][j] = new CaseGrille();
	}

	public int nextIntBounds(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	public void redessine() {
		repaint();
	}

	public void colorieFond(int j, int i, Color c) {
		m[i][j].setCouleur(c);
	}

	public void iconFond(int j, int i, ImageIcon icon) {
		m[i][j].setIcon(icon);
	}

	public void addDisqueAnimaux(ImageIcon icon, int j, int i, int rayon, Color c) {
		m[i][j].addDisqueAnimaux(rayon, c, icon);
	}

	public void addDisqueVegetaux(ImageIcon icon, int j, int i, int rayon, Color c) {
		m[i][j].addDisqueVegetaux(rayon, c, icon);
	}

	public void resetlDisques(int j, int i) {
		m[i][j].resetlDisques();
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public JFrame getWindow() {
		return window;
	}

	public void paintInfosBar(Graphics g) {
		g.setColor(new Color(137, 165, 255));
		g.fillRect(0, 0, decalageAffichage, decalageAffichage);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 2));
		String etat = (Ecosysteme.simulate) ? "⏸" : "▶";
		g.drawString(etat, decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setColor(new Color(135, 255, 151));
		g.fillRect(decalageAffichage, 0, decalageAffichage * 2, decalageAffichage);
		g.setColor(Color.BLACK);
		g.drawString("↺", decalageAffichage + decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setColor(new Color(255, 124, 124));
		g.fillRect(decalageAffichage * 2, 0, decalageAffichage * 3, decalageAffichage);
		g.setColor(Color.BLACK);
		g.drawString("Cycle: " + cycle, decalageAffichage * 2 + decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setColor(new Color(255, 207, 160));
		g.fillRect(decalageAffichage * 5, 0, nbPixelCoteCase * nbCasesL - decalageAffichage * 5, decalageAffichage);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 5));
		String sGrille = (contoure) ? "Masquer " : "Afficher ";
		sGrille += (showCase) ? "la grille (Appuyer sur S) ❌" : "la grille (Appuyer sur S)";
		g.drawString("Cycle suivant (Appuyer sur Entrer)", decalageAffichage * 5 + 4, 14);
		g.drawString(sGrille + " ", decalageAffichage * 5 + 4, 34);
		g.drawString("Vitesse (Appuyer sur ↑ ↓): " + ecosysteme.getVitesseSimulation() + " ms",
				decalageAffichage * 5 + 4, 54);
		g.drawString("Relancer la simulation (Appuyer sur R)", decalageAffichage * 5 + 4, 74);
		g.drawString("Cliquer sur une case pour plus d'infos", decalageAffichage * 5 + 4, 94);
	}

	public void paintCaseDisques(Graphics g, LinkedList<Disque> l, int cellX, int cellY) {
		for (Disque d : l) {
			int rayon = d.getRayon();
			g.drawImage(d.getIcon().getImage(),
					cellX + nbPixelCoteCase / 2 - rayon / 2
							+ nextIntBounds(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
					cellY + nbPixelCoteCase / 2 - rayon / 2
							+ nextIntBounds(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
					rayon, rayon, null);
		}
	}

	public void paintCaseDisquesZoom(Graphics g, LinkedList<Disque> l, int cellX, int cellY, int offsetX, int offsetY) {

		for (Disque d : l) {
			int rayon = d.getRayon() * 5;
			g.drawImage(d.getIcon().getImage(),
					cellX + offsetX / 2 + nextIntBounds(-offsetX / 2, offsetX / 2 - rayon / 10),
					cellY + offsetY / 2 + nextIntBounds(-offsetY / 2, offsetY / 2 - rayon / 10), rayon, rayon, null);
		}
	}

	public void paintCaseInfos(Graphics g, int caseX, int caseY) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(226, 201, 255));
		g.fillRect(nbPixelCoteCase - 16, decalageAffichage + nbPixelCoteCase - 32, 260, 260);
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g.drawLine(nbPixelCoteCase - 16, decalageAffichage + nbPixelCoteCase - 32, nbPixelCoteCase - 16,
				decalageAffichage + nbPixelCoteCase - 32 + 260);
		g.drawLine(nbPixelCoteCase - 16, decalageAffichage + nbPixelCoteCase - 32, nbPixelCoteCase - 16 + 260,
				decalageAffichage + nbPixelCoteCase - 32);
		g.drawLine(nbPixelCoteCase - 16 + 260, decalageAffichage + nbPixelCoteCase - 32, nbPixelCoteCase - 16 + 260,
				decalageAffichage + nbPixelCoteCase - 32 + 260);
		g.drawLine(nbPixelCoteCase - 16, decalageAffichage + nbPixelCoteCase - 32 + 260, nbPixelCoteCase - 16 + 260,
				decalageAffichage + nbPixelCoteCase - 32 + 260);

		g2.setStroke(new BasicStroke(1));
		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 4));
		g.drawString("Zone " + caseX + " " + caseY, nbPixelCoteCase, decalageAffichage + nbPixelCoteCase);
		g.drawString("Niveau d'eau: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getEau(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase+ 40);
		g.drawString("Temperature: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getTemperature(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 60);
		g.drawString("Nombre d'insectes: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getNbInsecte(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 100);
		g.drawString("Nombre de mammiferes: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getNbMammifere(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 120);
		g.drawString("Nombre d'oiseaux: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getNbOiseau(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 160);
		g.drawString("Nombre d'arbres: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getNbArbre(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 180);
		g.drawString("Nombre de vivaces: " + this.ecosysteme.getZoneForGrille(caseX, caseY).getNbVivace(), nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 200);
	}

	public void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g.drawLine(0, decalageAffichage, nbCasesL * nbPixelCoteCase - 1, decalageAffichage);
		g.drawLine(nbCasesL * nbPixelCoteCase - 1, 0, nbCasesL * nbPixelCoteCase - 1,
				nbCasesH * nbPixelCoteCase + decalageAffichage - 1);
		g.drawLine(0, 0, 0, nbCasesH * nbPixelCoteCase + decalageAffichage - 1);
		g.drawLine(0, nbCasesH * nbPixelCoteCase + decalageAffichage - 1, nbCasesL * nbPixelCoteCase - 1,
				nbCasesH * nbPixelCoteCase + decalageAffichage - 1);
		g2.setStroke(new BasicStroke(1));
	}

	public void paintGrille(Graphics g) {
		if (contoure) {
			for (int i = 0; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
				g.drawLine(i, decalageAffichage, i, nbCasesH * nbPixelCoteCase + decalageAffichage);
			}
			for (int j = 0; j <= nbCasesH * nbPixelCoteCase; j += nbPixelCoteCase) {
				g.drawLine(0, j + decalageAffichage, nbCasesL * nbPixelCoteCase, j + decalageAffichage);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintInfosBar(g);
		if (!showCase) {
			for (int i = 0; i < nbCasesL; i++)
				for (int j = 0; j < nbCasesH; j++) {
					int cellX = (i * nbPixelCoteCase);
					int cellY = decalageAffichage + (j * nbPixelCoteCase);
					g.drawImage(m[i][j].getIcon().getImage(), cellX, cellY, nbPixelCoteCase, nbPixelCoteCase, null);
					paintCaseDisques(g, m[i][j].lAnimaux, cellX, cellY);
					paintCaseDisques(g, m[i][j].lVegetaux, cellX, cellY);
				}
		} else {
			int cellX = 0;
			int cellY = decalageAffichage;
			int showCaseNbPixelCoteCaseL = nbPixelCoteCase * nbCasesL;
			int showCaseNbPixelCoteCaseH = nbPixelCoteCase * nbCasesH;
			g.drawImage(m[caseX][caseY].getIcon().getImage(), cellX, cellY, showCaseNbPixelCoteCaseL,
					showCaseNbPixelCoteCaseH, null);

			paintCaseDisquesZoom(g, m[caseX][caseY].lAnimaux, cellX, cellY, showCaseNbPixelCoteCaseL,
					showCaseNbPixelCoteCaseH);
			paintCaseDisquesZoom(g, m[caseX][caseY].lVegetaux, cellX, cellY, showCaseNbPixelCoteCaseL,
					showCaseNbPixelCoteCaseH);
			paintCaseInfos(g, caseX, caseY);

		}
		paintBorder(g);
		paintGrille(g);
	}

	public boolean isContoure() {
		return contoure;
	}

	public void setContoure(boolean contoure) {
		this.contoure = contoure;
	}

}
