package edu.neu.coe.info6205.union_find;

import io.cucumber.java.bs.A;

import java.util.ArrayList;
import java.util.Random;
import edu.neu.coe.info6205.WalkPlotter;
import org.jfree.data.xy.XYSeries;

public class UFClient {
	
	public static int count(int n) {

        UF_HWQUPC quickfind = new UF_HWQUPC(n, true);
        Random r = new Random();

        int pairs = 0;
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
    
	
	public static void main(String[] args) {
		  XYSeries series1 = new XYSeries("Objects(n)");
	      XYSeries series2 = new XYSeries("0.5 * n * log(n)");
       
	    int n = 5001, s=0, r=200;
        for(int i = s; i < n; i = i + 200) {
            int m = 0;
            for(int j = 0; j < r; j++) {
                if(i == 0){
                    m += count(1);
                } else {
                    m += count(i);
                }
            }
            double m_average = m/r;

            double l = 0.5 * Math.log(i) * i;
            System.out.println("n = " +i+ " average number of pairs generated over 200 runs were:  " + m_average);

            double slope = (m_average) / (i);
            System.out.println("Slope: " + slope);

            series1.add(i, m_average);
            series2.add(i, l);
        }
        WalkPlotter plotter = new WalkPlotter(series1,series2,null,"Objects  Pairs","Objects","Pairs");
        plotter.setVisible(true);
    }

    
    
}
