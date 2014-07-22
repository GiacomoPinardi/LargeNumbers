/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package largenumbers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author Giacomo
 */
public class Worker {
    
    private FileReader f1;
    private BufferedReader fIN;
    
    private FileWriter f2;
    private PrintWriter fOUT;
    
    private Random estrattore = new Random();
    
    private float percentuali[] = new float[10];
    private double nEstraz[] = new double[10];
    // result
    String[] r= new String[10]; 
    
    public void start(long loops) {
        try {
            f1 = new FileReader("settings.txt");
            fIN = new BufferedReader(f1);
            
            String riga = fIN.readLine();
            String[] s;
            s = riga.split(";");
            for (int i = 0; i < 10; i++) {
                percentuali[i] = Float.valueOf(s[i]);
            }
            fIN.close();
            f1.close();            
        }
        catch (FileNotFoundException ex) {
            System.out.println("ERROR: File Not Found - Check the file 'settings.txt'");
        }
        catch (IOException ec) {
            System.out.println("ERROR: Input/Output - Check the file 'numbers.txt'");
        }
        
        for (int i = 0; i < 10; i++) {
            nEstraz[i] = 0;
        }
        
        // intervallo
        int i[] = new int[10];
        for (int j = 0; j < 10; j++) {
            i[j] = (int)((percentuali[j]/100)*100000);
            if ((j > 0)){
                i[j] = i[j] + i[j-1];
            }
        }
        for (int j = 0; j < 10; j++) {
            if (percentuali[j] == 0) {
                i[j] = 0;
            }
        }
                
        int n;
        for (long j = 0; j < loops; j++) {
            n = estrattore.nextInt(100000) + 1;
            for (int k = 0; k < 10; k++) {
                if (n <= i[k]) {
                    nEstraz[k] ++;
                    break;
                }
            }
        }       
         
        for (int j = 0; j < 10; j++) {
                nEstraz[j] = (nEstraz[j] / loops) * 100;
                r[j] = String.format("%.3f" , nEstraz[j]);
        }
        this.writeResults();
    }
    
    private void writeResults () {
        try {
            f2 = new FileWriter("results.txt");
            fOUT = new PrintWriter(f2);            
            fOUT.print(r[0] + ";" + r[1] + ";" + r[2] + ";" + r[3] + ";" + r[4] + ";" + r[5] + ";" + r[6] + ";" + r[7] + ";" + r[8] + ";" + r[9]);
            fOUT.close();
            f2.close();
        }
        catch (IOException IOE) {
            System.out.println("ERROR: Input/Output Error. Check the file 'results.txt'");
        }
    }
    
}
