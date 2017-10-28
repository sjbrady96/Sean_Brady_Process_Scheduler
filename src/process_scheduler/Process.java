package process_scheduler;

public class Process implements Comparable<Process>
{
    String name;
    int arrival;
    int burst;
    
    public Process(String n, int a, int b)
    {
        name = n;
        arrival = a;
        burst = b;
    }
   
    public String getName()
    {
	return name;
    }

    public int getArrival()
    {
	return arrival;
    }

    public int getBurst()
    {
	return burst;
    }
    
    public void setBurst(int b)
    {
        burst = b;
    }

    //sort list by arrival time
    @Override
    public int compareTo(Process compareProc) 
    {
        int compareArrival = ((Process)compareProc).getArrival();
        return this.arrival - compareArrival;
    } 
}