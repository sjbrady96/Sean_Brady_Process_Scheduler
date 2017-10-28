package process_scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 * This program simulates the first-come-first-serve (FCFS), shortest-remaining-time-first (SRTF), and round-robin (RR)
 * scheduling algorithms. 
 * Input data sets are read from a text file. Output is displayed in the console.
 * 
 * @author Sean Brady
 * @version 1.0 10/25/2017
 */

public class Driver 
{
    static File f;
    static ArrayList<Process> fcfsList = new ArrayList<>();
    static ArrayList<Process> srtfList = new ArrayList<>();
    static ArrayList<Process> rrList = new ArrayList<>();
    
    public static void main(String[] args) throws IOException 
    {
        //select input file
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select source file");
        chooser.showOpenDialog(null);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f = chooser.getSelectedFile();
        read(f);
        
        //call scheduling algorithms
        Scheduler s = new Scheduler();
        printBorder(); s.FCFS();
        printBorder(); s.SRTF();
        printBorder(); s.RR();
    }
    
    public static void printBorder()
    {
        System.out.println("---------------------------------------------");
    }
    
    //read input file into list structures
    public static void read(File f) throws IOException
    {
        String name;
        int arrival;
        int burst;
                
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        
        while((line = br.readLine()) != null)
        {
            if(!line.contains("#")) //will not read any lines containting the character '#'
            {
                String data[] = line.split(" ");
                
                name = data[0];
                arrival = Integer.parseInt(data[1]);
                burst = Integer.parseInt(data[2]);
                
                //three identical lists for each algorithm
                fcfsList.add(new Process(name, arrival, burst));
                srtfList.add(new Process(name, arrival, burst));
                rrList.add(new Process(name, arrival, burst));
            }
        }
        
        //print input data
        System.out.print("Inputs:\n\nname\tarrival time\tburst time\n");
        for(Process process : fcfsList)
        {
            System.out.println(process.getName() + "\t" +
                                process.getArrival() + "\t\t" +
                                process.getBurst());
        }
    } 
}