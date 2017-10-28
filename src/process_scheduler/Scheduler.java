package process_scheduler;

import java.util.Collections;
import static process_scheduler.Driver.srtfList;
import static process_scheduler.Driver.rrList;
import static process_scheduler.Driver.fcfsList;

public class Scheduler
{    
    //round robin
    public void RR()
    {       
        final int procMAX = 10; //maximum number of processes
        final int timeQUANT = 2;
        final int listSIZE = rrList.size();
        
	int flag = 0;
	int waitTime;
	int turnaroundTime;
        int time;   //keeps track of time
        int count;  //keeps track of current process
        int remaining = listSIZE;   //keeps track of remaining processes
	
	int[] arrivalTime = new int[procMAX];
	int[] burstTime = new int[procMAX];
	int[] remainingTime = new int[procMAX];
        
        System.out.println("Round Robin (q = 2):\n");
	
        Collections.sort(rrList);
        
	for(int i = 0; i < listSIZE; i++)
	{
            Process process = rrList.get(i);
            
            arrivalTime[i] = process.getArrival();
            burstTime[i] = process.getBurst();
            remainingTime[i] = burstTime[i]; 
	}
	
	for(time = 0, count = 0; remaining != 0;) //loops until no more process remain
	{
            Process process = rrList.get(count);
                           
            if(remainingTime[count] <= timeQUANT && remainingTime[count] > 0)
            {
                time += remainingTime[count];
		remainingTime[count] = 0;
		flag = 1;
            }
            else if(remainingTime[count] > 0)
            {
                remainingTime[count] -= timeQUANT;
		time += timeQUANT;
            }

            if(remainingTime[count] == 0 && flag == 1)
            {
                process.setBurst(-1); //used to keep track of which processes are finished
                remaining--;
                
                waitTime = time - arrivalTime[count] - burstTime[count]; 
                turnaroundTime = time - arrivalTime[count]; 
                
                System.out.println("name: " + process.getName() +
                                    "\ntotal time: " + time +
                                    "\nwait time: " + waitTime +
                                    "\nturnaround time: " + turnaroundTime + "\n");
		flag = 0;
            }
            
            if(process.getBurst() != -1 && time > 0)
            {
                System.out.println("name: " + process.getName() +
                                    "\nnot finished yet\n");
            }
            
            if(count == listSIZE - 1)
            {
                count = 0;  //starts the cycle over
            }
            else if(arrivalTime[count + 1] <= time)
            {
		count++;
            }
            else
            {
                count = 0;
            }
        }
    }
	
    //shortest remaining time first
    public void SRTF()
    {
        final int procMAX = 10; //maximum amount of processes
        final int burstMAX = 10000;
        
        int[] arrivalTime = new int[procMAX];
	int[] burstTime = new int[procMAX];
	int[] temp = new int[procMAX];
	
	int listSize = srtfList.size();
	int count = 0;
	int waitTime;
	int turnaroundTime;
        int smallest;
	int end;
        
        System.out.println("Shortest Remaining Time First:\n");
	
	for(int i = 0; i < listSize; i++)
	{
            Process process = srtfList.get(i);
            
            arrivalTime[i] = process.getArrival();
            burstTime[i] = process.getBurst();
            temp[i] = burstTime[i];
	}
	
	burstTime[procMAX-1] = burstMAX;
        
	for(int time = 0; count < listSize; time++)
	{
            smallest = procMAX-1;
            for(int i = 0; i < listSize; i++)
            {
		if((arrivalTime[i] <= time) && (burstTime[i] < burstTime[smallest]) && (burstTime[i] > 0))
		{
                    smallest = i;
                }
            }
            Process process = srtfList.get(smallest);
            
            burstTime[smallest]--;
            process.setBurst(process.getBurst()-1);
            
            System.out.println("name: " + process.getName() +
                                "\nremaining burst time: " + process.getBurst() +
                                "\ntotal time: " + (time + 1));

            if(burstTime[smallest] == 0)
            {
                count++;
                end = time + 1;
                waitTime = end - arrivalTime[smallest] - temp[smallest];
                turnaroundTime = end - arrivalTime[smallest];
                System.out.println("wait time: " + waitTime +
                                    "\nturnaround time: " + turnaroundTime + "\n");
            }
            else
            {
                System.out.println("not finished yet\n");
            }
        }
    }
	    
    //first come first serve
    public void FCFS()
    {
        int remainingBurst;
        int time = 0;
        int waitTime;
        int turnaroundTime;
        
        System.out.println("First Come First Serve:\n");
        
        Collections.sort(fcfsList);
        
        for(int i = 0; i < fcfsList.size(); i++) //loop through each process
        {
            Process process = fcfsList.get(i);
            
            remainingBurst = process.getBurst();
            time += process.getBurst();
            
            waitTime = time - process.getArrival() - process.getBurst();
            turnaroundTime = time - process.getArrival();
            
            System.out.println("name: " + process.getName() +             
                               "\nburst time: " + process.getBurst() +
                               "\ntotal time: " + time +
                               "\nwait time: " + waitTime +        
                               "\nturnaround time: " + turnaroundTime + "\n");
            
            //decrement burst time of the current process until it is finished processing
            while(remainingBurst > 0)
            {
                process.setBurst(remainingBurst--);
            }
        }
    }
}