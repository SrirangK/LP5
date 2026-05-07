import mpi.*;

public class prefixsummpi{
    public static void main(String[] args) throws Exception{
        MPI.Init(args);
        int rank=MPI.COMM_WORLD.Rank();
        int size=MPI.COMM_WORLD.Size();    

        int[] arr=new int[size];
        int[] recv=new int[1];
        int sum=0;

        if(rank==0)
        {
            for(int i=0;i<size;i++)
            {
                arr[i]=i+1;
            }
            System.out.println("array intialized");
        }

        MPI.COMM_WORLD.Scatter(arr,0,1,MPI.INT,recv,0,1,MPI.INT,0);
        int data=recv[0];

        if (rank==0)
        {
            sum=data;
            if(size>1)
            {
                MPI.COMM_WORLD.Send(new int[]{sum},0,1,MPI.INT,rank+1,0);
            }
        }
            else{
                int[] temp =new int[1];
                MPI.COMM_WORLD.Recv(temp,0,1,MPI.INT,rank-1,0);
                sum=temp[0]+data;
                if(rank!=size-1){
                MPI.COMM_WORLD.Send(new int[]{sum},0,1,MPI.INT,rank+1,0);
            }
            }
            
        System.out.println("Process " + rank + " Intermediate Sum = " + sum);

        MPI.Finalize();
    }
}