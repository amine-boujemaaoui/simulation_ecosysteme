package main.test;

import java.awt.Color;
import java.util.Random;

import main.Grille.GrilleNature;

public class MainTest {

	public static void main(String[] args)
	{
   	    int nbCasesL=5, nbCasesH=6;
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
