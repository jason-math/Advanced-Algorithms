import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

public class CircuitDesign {
    private static InputReader reader = new InputReader(System.in);
    private static OutputWriter writer = new OutputWriter(System.out);

    public CircuitDesign(InputReader reader, OutputWriter writer) {
        CircuitDesign.reader = reader;
        CircuitDesign.writer = writer;
    }

    public static void main(String[] args) throws IOException{ 
    	new Thread(null, new Runnable() {
            public void run() {
                new CircuitDesign(reader, writer).run();
                writer.writer.flush();
            }
        }, "1", 1 << 26).start();
    }

    class Clause {
        int firstVar;
        int secondVar;
    }

    class TwoSatisfiability {
        int numVars;
        Clause[] clauses;
        int postOrderCounter;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        @SuppressWarnings("unchecked")
		public boolean isSatisfiable(int[] result) {
        	Arrays.fill(result, -1);
        	//Sets of variables that are possible solutions
            ArrayList<HashSet<Integer>> components = new ArrayList<HashSet<Integer>>();
            
            ArrayList<Integer>[] g = (ArrayList<Integer>[]) new ArrayList[2 * numVars];
            ArrayList<Integer>[] gR = (ArrayList<Integer>[]) new ArrayList[2 * numVars];
            for (int i = 0; i < 2 * numVars; i++) {
                g[i] = new ArrayList<Integer>();
                gR[i] = new ArrayList<Integer>();
            }

            buildImplicationGraph(g, gR);

            boolean visited[] = new boolean[2 * numVars];
            int postOrder[] = new int[2 * numVars];
            postOrderCounter = 2 * numVars;

            for (int i = 0; i < 2 * numVars; i++) 
            {
                if (!visited[i])
                    buildPostOrder(gR, i, visited, postOrder);
            }

            visited = new boolean[2 * numVars];

            //Follows the post order to find the set of variables needed for satisfiability
            for (Integer componentStart: postOrder) 
            {
                if (!visited[componentStart])
                {
                    HashSet<Integer> newComponent = new HashSet<Integer>();
                    buildCurrentComponent(componentStart, g, visited, newComponent);
                    components.add(newComponent);
                }
            }

            //Checks if any component contains a variable and its inverse
            for (HashSet<Integer> component: components) 
            {
                for (Integer vertex: component) 
                {
                    if (vertex >= numVars && component.contains(vertex - numVars))
                        return false;
                    if (vertex < numVars && component.contains(vertex + numVars))
                        return false;
                }
            }

            //Changes the variables to a boolean form and adds it to the result
            for (HashSet<Integer> component: components) 
            {
                for (Integer var: component) 
                {
                    int reducedVar = var >= numVars ? (var - numVars) : var;
                    if (result[reducedVar] == -1)
                    {
                    	int bool = var >= numVars ? 1 : 0;
                    	result[reducedVar] = bool;
                    }   
                }
            }
            return true;
        }
        
      //Builds an "Implication Graph" which contains which variables must be true if the other is false in order to satisfy the clause
        private void buildImplicationGraph(ArrayList<Integer>[] g, ArrayList<Integer>[] gR) {
            for (Clause clause : clauses) {
            	//Stagger the not clauses by numVars indices
            	int x = clause.firstVar > 0 ? Math.abs(clause.firstVar) : Math.abs(clause.firstVar) + numVars;
                int y = clause.secondVar > 0 ? Math.abs(clause.secondVar) : Math.abs(clause.secondVar) + numVars;
                int notX = clause.firstVar < 0 ? Math.abs(clause.firstVar) : Math.abs(clause.firstVar) + numVars;
                int notY = clause.secondVar < 0 ? Math.abs(clause.secondVar) : Math.abs(clause.secondVar) + numVars;
                //Convert from (1,n) to (0,n-1) and add it into the implications graph
                // if the first clause is false the second must be true and vice-versa
                g[--notX].add(--y);
                g[--notY].add(--x);
                gR[y].add(notX);
                gR[x].add(notY);
            }
        }

        private void buildPostOrder(ArrayList<Integer>[] gR, int v, boolean[] visited, int[] postOrder) {
            visited[v] = true;
            for (Integer w : gR[v])
                if (!visited[w])
                    buildPostOrder(gR, w, visited, postOrder);
                
            postOrder[--postOrderCounter] = v;
        }
        
        private void buildCurrentComponent(Integer v, ArrayList<Integer>[] g, boolean[] visited, HashSet<Integer> currComponent) {
        	visited[v] = true;
            currComponent.add(v);
            for (Integer w : g[v]) {
                if (!visited[w]){
                    buildCurrentComponent(w, g, visited, currComponent);
                }
            }
        }

        
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = reader.nextInt();
            twoSat.clauses[i].secondVar = reader.nextInt();
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiable(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i-1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
