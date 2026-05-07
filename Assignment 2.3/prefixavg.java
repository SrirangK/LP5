import mpi.*;
import java.util.Random;

public class prefixavg{
	public static void main(String args[]){
		MPI.Init(args);
		
		int rank=MPI.COMM_WORLD.Rank();
		int size=MPI.COMM_WORLD.Size();
		int N=8;
		
		int[] nums=new int[N];
		int chunkSize=N/size;
		int[] recv=new int[chunkSize];
		
		if(rank==0){
			Random rand=new Random();
			
			for(int i=0;i<N;i++){
				nums[i]=rand.nextInt(10)+1;
				System.out.println("Number generated: "+nums[i]);
			}
		}
		
		MPI.COMM_WORLD.Scatter(nums,0,chunkSize,MPI.INT,recv,0,chunkSize,MPI.INT,0);
		
		int localSum=0;
		for(int i=0;i<chunkSize;i++) localSum+=recv[i];
		
		double localAvg=(double)localSum/chunkSize;
		
		double[] gathered=new double[size];
		
		MPI.COMM_WORLD.Gather(new double[]{localAvg},0,1,MPI.DOUBLE,gathered,0,1,MPI.DOUBLE,0);
		
		double globalAvg=0;
		
		if(rank==0){
			for(int i=0;i<size;i++) globalAvg+=gathered[i];
			
			globalAvg=(double)globalAvg/size;
			
			System.out.println("Gathered average: "+globalAvg);
		}
		
		MPI.Finalize();
	}
}
		
		