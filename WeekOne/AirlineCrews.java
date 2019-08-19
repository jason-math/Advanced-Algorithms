import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class AirlineCrews {
    private static FastScanner in;
    private PrintWriter out;
    static int planes, crews;
    private static boolean[] marked;
    private static Edge[] edgeTo;

    public static void main(String[] args) throws IOException {
        new AirlineCrews().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        FlowGraph graph = readData();
        int[] matching = findMatching(graph, 0, graph.size()-1);
        writeResponse(matching);
        out.close();
    }

    static FlowGraph readData() throws IOException {
        planes = in.nextInt();
        crews = in.nextInt();
        FlowGraph graph = new FlowGraph(planes + crews + 2);
        //Create Edges from the source to each plane
		for(int u=0; u < planes; u++)
        {
        	graph.addEdge(0, u+1, 1);
        }
		//Create Edges from each crew to the sink
        for(int v=0; v < crews; v++)
        {
        	graph.addEdge(planes + 1 + v, planes + crews + 1, 1);
        }
		//Create Edges from each plane to it's availible crews
        for (int i = 0; i < planes; ++i)
            for (int j = 0; j < crews; ++j)
            {
                int edge = in.nextInt();
                if(edge == 1)
                {
                	graph.addEdge(i + 1, planes + j + 1, 1);
                }
                
            }    
        return graph;
    }

    private int[] findMatching(FlowGraph graph, int from, int to) {
		//Solution set
		int[] matching = new int[planes];
		Arrays.fill(matching, -1);
		
	 		
	        while(hasPath(graph, from ,to))
		        {
		        	int pathFlow = 1;
		        	
		        	for(int v=to; v!=from; v = edgeTo[v].other(v))
		        		edgeTo[v].addResidualFlowTo(v,pathFlow);
		        }
	        for(Edge e : graph.getEdges())
	        {
	        	if(e.flow > 0 && e.to != to && e.from != from)
	        	{	
	        		matching[e.from-1] = e.to - planes - 1;
	        	}
	        }
		
		return matching;	
    }
    
    private static boolean hasPath(FlowGraph graph, int from, int to)
    {
    	//Path
    	edgeTo = new Edge[graph.size()];
    	//Array to check if the vertex has been visited
    	marked = new boolean[graph.size()];
    	
    	Queue<Integer> queue = new ArrayDeque<Integer>();
    	queue.add(from);
    	marked[from] = true;
    	while(!queue.isEmpty())
    	{
    		int v = queue.poll();
    		for(Edge e : graph.adj(v))
    		{
    			int w = e.other(v);	
    			if(e.residualCapacityTo(w) > 0 && !marked[w])
    			{
    				edgeTo[w] = e;
    				marked[w] = true;
    				queue.add(w);
    			}
    		}
    	}
    	return marked[to];
    }

    static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
        //This method allows me to more efficiently find the other vertex of the edge
        public int other(int vertex)
        {
        	if(vertex == from)
        		return to;
        	else
        		return from;
        }
        //This method allows me to find the flow going in both directions
        public int residualCapacityTo(int vertex)
        {
        	if(vertex == from)
        		return flow;
        	else 
        		return capacity-flow;
        }
        //This method is used to change the residual flow in both directions
        public void addResidualFlowTo(int vertex, int deltaFlow)
        {
        	if(vertex == from)
        		flow -= deltaFlow;
        	else
        		flow += deltaFlow;
        }
        
        public int residual()
        {
        	return capacity-flow;
        }
        
        
    }

    /* This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly. 
	 
		EDIT: I used if/else statements to code the functionality of backward edges into the forward edges 
		which eliminates the need for backward edges therefore sacrificing a bit of time for memory
	 */
    static class FlowGraph {

        /* These adjacency lists store only indices of edges from the edges list 
         * EDIT: Rather than storing the index positions of the edges I chose to store just the edges to pretty much cut out the "middle man"
         */
        private List<Edge>[] graph;
        private List<Edge> edges = new ArrayList<Edge>();

        @SuppressWarnings("unchecked")
		public FlowGraph(int n) {
            this.graph = (ArrayList<Edge>[])new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<Edge>();
        }

        public void addEdge(int from, int to, int capacity) {
            Edge forwardEdge = new Edge(from, to, capacity);
            graph[from].add(forwardEdge);
            graph[to].add(forwardEdge);
            edges.add(forwardEdge);
        }
        public List<Edge> adj(int v)
        {
        	return graph[v];
        }

        public int size() {
            return graph.length;
        }
        public List<Edge> getEdges()
        {
        	return edges;
        }
    
    }

    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
