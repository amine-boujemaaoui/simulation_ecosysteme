package main.Grille;

import javax.swing.JFrame;
import java.util.Random;
import java.awt.Color;


public class MainExempleNature 
{
	public static void main(String[] args)
	{
   	        int nbCasesL=5, nbCasesH=6;
		// On crée une Grille "Logique"  objet ChampGraphique de 50 cases de large, et 60 de haut de 20 pixels de côté
		GrilleNature grille = new GrilleNature(nbCasesL, nbCasesH, 100);
		
		Random r = new Random();
		
		int i,j;
	       	       
		for(i=0; i<nbCasesL; i++)
		    for (j=0;j<nbCasesH;j++) { 
			if (i<j)
			    grille.colorieFond(i,j,Color.GREEN);
		        else
			    grille.colorieFond(i,j,Color.BLUE);
		
		
		    }
		
		grille.redessine();

	        //Puis, pause de 2s
   	        try {Thread.sleep(500);} 
		catch (InterruptedException e){e.printStackTrace();}

		
		for(i=0; i<nbCasesL; i++)
		    for (j=0;j<nbCasesH;j++) {

			// Place des disques au hasard

			if (r.nextInt()%2==0)
   			    grille.addDisque(i,j,20,Color.BLACK); 
 		
		        //Puis, pause de 2s
	   	        try {Thread.sleep(500);} 
         		catch (InterruptedException e){e.printStackTrace();}

			grille.redessine();

		    
 	            }		
         

        
	}
}
