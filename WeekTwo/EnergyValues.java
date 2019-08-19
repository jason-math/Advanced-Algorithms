import java.io.IOException;
import java.util.Scanner;

class Equation {
    Equation(double a[][], double b[]) {
        this.a = a;
        this.b = b;
    }

    double a[][];
    double b[];
}

class Position {
    Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int column;
    int row;
}

class EnergyValues {
	static int size;
    static Equation ReadEquation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        size = scanner.nextInt();

        double a[][] = new double[size][size];
        double b[] = new double[size];
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column)
                a[row][column] = scanner.nextInt();
            b[row] = scanner.nextInt();
        }
        scanner.close();
        return new Equation(a, b);
    }

//Returns the position of the element with the largest coefficient as the pivot
    static Position SelectPivotElement(double a[][], boolean used_rows[], boolean used_columns[]) {
        
    	Position pivot = new Position(0,0);
    	while(used_rows[pivot.row])
    		++pivot.row;
    	while(used_columns[pivot.column])
    		++pivot.column;
    	double max = 0;
    	for(int from = pivot.row; from < size; from++)
    	{
    		if(Math.abs(a[from][pivot.column]) > Math.abs(max))
    		{
    			max = a[from][pivot.column];
    			pivot.row = from;
    		}
    	}
    	return pivot;
    }

//Swaps two rows of the matrix for organization
    static void SwapLines(double a[][], double b[], boolean used_rows[], Position pivot_element) {
        int size = a.length;

        for (int column = 0; column < size; ++column) {
            double tmpa = a[pivot_element.column][column];
            a[pivot_element.column][column] = a[pivot_element.row][column];
            a[pivot_element.row][column] = tmpa;
        }

        double tmpb = b[pivot_element.column];
        b[pivot_element.column] = b[pivot_element.row];
        b[pivot_element.row] = tmpb;

        boolean tmpu = used_rows[pivot_element.column];
        used_rows[pivot_element.column] = used_rows[pivot_element.row];
        used_rows[pivot_element.row] = tmpu;

        pivot_element.row = pivot_element.column;
    }

    static void ProcessPivotElement(double a[][], double b[], Position pivot_element) {
        double mult = 0.0;
        
        scalePivot(a, b, pivot_element);
        
        for(int i=pivot_element.row + 1; i< size; i++)
        {
        	mult = a[i][pivot_element.column];
        	for(int j= pivot_element.column; j < size; j++)
        		a[i][j] -= (a[pivot_element.row][j] * mult);
        	b[i] -= (b[pivot_element.row] * mult);
        }
        
    }
    
//Scales the rest of the equations to the pivot
    static void scalePivot(double a[][], double b[], Position pivot_element)
    {
    	double div = a[pivot_element.row][pivot_element.column];
    	
    	for(int i=pivot_element.column; i<size; i++)
    		a[pivot_element.row][i] /= div ;
    	b[pivot_element.row] /= div;
    }

//Marks the already used equations to avoid repetition
    static void MarkPivotElementUsed(Position pivot_element, boolean used_rows[], boolean used_columns[]) {
        used_rows[pivot_element.row] = true;
        used_columns[pivot_element.column] = true;
    }
    
    static void backSubstitution(double a[][], double b[])
    {
    	for(int i=size-1; i > 0; i--)
    	{
    		double v = b[i];
    		for(int j=0; j != i; j++)
    		{
    			b[j] -= a[j][i] * v;
    			a[j][i] = 0;
    		}
    	}
    }

    static double[] SolveEquation(Equation equation) {
        double a[][] = equation.a;
        double b[] = equation.b;
        int size = a.length;

        boolean[] used_columns = new boolean[size];
        boolean[] used_rows = new boolean[size];
        for (int step = size; step >0 ; step--) {
        	Position pivot_element = SelectPivotElement(a, used_rows, used_columns);
            SwapLines(a, b, used_rows, pivot_element);
            ProcessPivotElement(a, b, pivot_element);
            MarkPivotElementUsed(pivot_element, used_rows, used_columns);
        }
        
	//Solves for the remaining values using the ones already obtained
        backSubstitution(a,b);

        return b;
    }

    static void PrintColumn(double column[]) {
        int size = column.length;
        for (int row = 0; row < size; ++row)
            System.out.printf("%.20f\n", column[row]);
    }

    public static void main(String[] args) throws IOException {
        Equation equation = ReadEquation();
        double[] b = SolveEquation(equation);
        PrintColumn(b);
    }
}
