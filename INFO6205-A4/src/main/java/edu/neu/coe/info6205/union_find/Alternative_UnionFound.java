package edu.neu.coe.info6205.union_find;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Random;
import java.util.function.Supplier;
import edu.neu.coe.info6205.union_find.plotter;
import org.jfree.data.xy.XYSeries;

public class Alternative_UnionFound {
    public static void main(String[] args) {
        
    	
    	XYSeries series1 = new XYSeries("Size");
        XYSeries series2 = new XYSeries("Height");
        XYSeries series3 = new XYSeries("path compression");
        XYSeries series4 = new XYSeries("path compression and grandparent fix");
        
        
        
        int repeat = 6, n=5000;
   
        int n2 = n;

        // Bunch of benchmark timer objects to benchmark the four different cases
        Benchmark_Timer<UF_Wieighted> benchmarkWUFSize = new Benchmark_Timer<UF_Wieighted>("Benchmark for Weighted UF based on size",null, (a) -> count(a) ,null);
        Benchmark_Timer<UF_HWQUPC> benchmarkWUFHeight = new Benchmark_Timer<UF_HWQUPC>("Benchmark for Weighted UF based on height",null, (a) -> count(a) ,null);
        Benchmark_Timer<UF_Wieighted> benchmarkWUFPathCompression = new Benchmark_Timer<UF_Wieighted>("Benchmark for Weighted UF Path compression ",null, (a) -> count(a) ,null);
        Benchmark_Timer<UF_Wieighted> benchmarkWUFPathCompressionIntermediate = new Benchmark_Timer<UF_Wieighted>("Benchmark for Weighted UF Path compression Grandparent fix",null, (a) -> count(a) ,null);


        
        
        for(int i = 0; i<repeat; i++){
            // Final int to be used in suppliers
            final int el = n;

            // Supplier
            Supplier<UF_Wieighted> WUFsize = () -> new UF_Wieighted(el, false, false);
            // Storing benchmark time in a variable
            double random_time = benchmarkWUFSize.runFromSupplier(WUFsize, 20);
            System.out.println("Time: " + random_time);
            // Adding to the series
            series1.add(n,random_time);

            Supplier<UF_HWQUPC> WUFheight = () -> new UF_HWQUPC(el, false);
            random_time = benchmarkWUFHeight.runFromSupplier(WUFheight, 20);
            System.out.println("Time: " + random_time);
            series2.add(n, random_time);

            Supplier<UF_Wieighted> WUFPC2pass = () -> new UF_Wieighted(el, true, false);
            random_time = benchmarkWUFPathCompression.runFromSupplier(WUFPC2pass, 20);
            System.out.println("Time: " + random_time);
            series3.add(n, random_time);

            Supplier<UF_Wieighted> WUFPC1pass = () -> new UF_Wieighted(el, true, true);
            random_time = benchmarkWUFPathCompressionIntermediate.runFromSupplier(WUFPC1pass, 20);
            System.out.println("Time: " + random_time);
            series4.add(n, random_time);

            n *= 2;
        }

        n = n2;

        for(int i=0; i<repeat; i++){
        	UF_Wieighted wufsize          = new UF_Wieighted(n, false, false);
            UF_HWQUPC    wufheight        = new UF_HWQUPC(n, false);
            UF_Wieighted wufpc            = new UF_Wieighted(n, true, false);
            UF_Wieighted wufpcgrandparent = new UF_Wieighted(n, true, true);

            count(wufsize);
            count(wufheight);
            count(wufpc);
            count(wufpcgrandparent);

            System.out.println("Depth_size and n = " + n + " is = " +wufsize.finalDepth());
            System.out.println("Depth_height and n = " + n + " is = " +wufheight.finalDepth());
            System.out.println("Depth_CP and n = " + n + " is = " +wufpc.finalDepth());
            System.out.println("Depth_CP with Grandparent Fix and n = " + n + " is = " +wufpcgrandparent.finalDepth());
            n = n*2;
        }

        plotter plot = new plotter(" Time vs Size ","Time","Elements");
        plot.addSeries(series1);
        plot.addSeries(series2);
        plot.addSeries(series3);
        plot.addSeries(series4);
        plot.initUI();
        plot.setVisible(true);
    }

    /**
     * Count method used as the fRun in becnhmark for UF_HWQUPC objects
     * @param quickfind UF_HWQUPC object
     * @return number of pairs generated
     */
    public static int count(UF_HWQUPC quickfind) {
        int n = quickfind.components();
        Random r = new Random();

        // Counter for number of pairs
        int pairs = 0;

        // Looping until number of components is 1
        while(quickfind.components() != 1){
            int p = r.nextInt(n);
            int q = r.nextInt(n);
            pairs++;
            if(!quickfind.connected(p,q)) {
                quickfind.union(p, q);
            }
        }

        return pairs;
    }

    /**
     * Count method used as the fRun in benchmark for WeightedUF objects
     * @param quickfind WeightedUF object
     * @return number of pairs generated
     */
    public static int count(UF_Wieighted quickfind) {
        int n = quickfind.count();
        Random r = new Random();

        // Counter for number of pairs
        int pairs = 0;

        // Looping until number of components is 1
        while(quickfind.count() != 1){
            int p = r.nextInt(n);
            int q = r.nextInt(n);
            pairs++;
            if(!quickfind.connected(p,q)) {
                quickfind.union(p, q);
            }
        }

        return pairs;
    }
		
		
	}


