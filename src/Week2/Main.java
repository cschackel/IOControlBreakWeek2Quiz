package Week2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static FileInput meals = new FileInput("meals_data.csv");

    public static void main(String[] args) {

        //Variable Init
        String line;  //Stores File Lines
        String[] fields;  //Stores Fields from file Line
        double average = 0;  //Average
        int total = 0;
        int count = 0;
        int min=0;
        int max=0;
        ArrayList<String> subItemList = new ArrayList<String>(); //List of item Cals by Category
        double median = 0;
        int grandTotal = 0;
        int grandCount = 0;
        int grandMin = 0;
        int grandMax=0;
        double grandmedian = 0;
        ArrayList<String> totItemList = new ArrayList<String>();
        String oldMealType="NA";
        System.out.format("%-15s  %-15s %-15s %-15s %-15s %-15s %-15s\n","Meal Type", "Average Calories","Number of Items","Total Calories", "Min", "Max", "Median");
        System.out.format("%-15s  %-15s %-15s %-15s %-15s %-15s %-15s\n","==============", "===============","==============","==============","==============","==============","==============");
        while ((line = meals.fileReadLine()) != null) {
            fields = line.split(",");
            if (!oldMealType.equals(fields[0])) {
                if (!oldMealType.equals("NA")) {
                    average = total / (double)count;
                    median = median(subItemList);
                    System.out.format("%-20s %10.2f %10d %15d %15d %15d %15.2f\n",oldMealType, average, count, total,min,max, median);
                }
                grandMax=max>grandMax?max:grandMax;  //Adjust Grand max
                grandMin=min<grandMin?min:grandMin;  //Adjust Grand Min
                oldMealType=fields[0];
                //Reset Fields
                count = 0;
                total = 0;
                min=0;
                max=0;
                subItemList=new ArrayList<String>();
                median=0;

            }
            //Starting # for Max Min
            if(count == 0)
            {
                min = Integer.parseInt(fields[2]);
                max = Integer.parseInt(fields[2]);
            }
            //Starting # for Grand max min
            if(grandCount==0)
            {
                grandMin=Integer.parseInt(fields[2]);
                grandMax=Integer.parseInt(fields[2]);
            }
            //Set Fields
            min = Integer.parseInt(fields[2])<min?Integer.parseInt(fields[2]):min;
            max = Integer.parseInt(fields[2])>max?Integer.parseInt(fields[2]):max;
            total += Integer.parseInt(fields[2]);
            count++;
            grandTotal += Integer.parseInt(fields[2]);
            grandCount++;
            subItemList.add(fields[2]);
            totItemList.add(fields[2]);
        }
        average = total / count;
        double totalAverage = grandTotal / (double)grandCount;
        median=median(subItemList);
        //System.out.println("-------------------");
        grandmedian=median(totItemList);
        System.out.format("%-20s %10.2f %10d %15d %15d %15d %15.2f\n",oldMealType, average, count, total, min,max, median);
        System.out.format("%-15s  %-15s %-15s %-15s %-15s %-15s %-15s\n","==============", "===============","==============","==============","==============","==============", "==============");
        System.out.format("%-20s %10.2f %10d %15d %15d %15d %15.2f\n","Grand Total", totalAverage, grandCount,grandTotal,grandMin,grandMax,grandmedian);
    }

    //Calculates Medium
    public static double median(ArrayList<String> list)
    {
        double median=0;
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(String num:list)
        {
            try
            {
                values.add(Integer.parseInt(num));
                //System.out.println(num);
            }
            catch(Exception e)
            {

            }
        }
        bubbleSort(values);
        /*
        for(int i=0;i<values.size();i++)
        {
            System.out.println("------"+values.get(i));
        }
        */
        if(values.size()%2==0)
        {
            int num1 = values.get(values.size()/2);
            int num2 = values.get((values.size()/2)-1);
            median = (double)(num1 + num2) / 2;
        }
        else
        {
            median = values.get((values.size()-1)/2);
        }
        //System.out.println("---------"+median);
        return median;

    }
 //Bubble Sorts
    public static void bubbleSort( ArrayList<Integer> values)
    {
        for (int i = 0; i < values.size(); i++)
            for (int j = 0; j < values.size()-1; j++)
                if (values.get(j) > values.get(j+1))
                {
                    int temp = values.get(j);
                    values.set(j,values.get(j+1));
                    values.set(j+1,temp);
                }
    }
}