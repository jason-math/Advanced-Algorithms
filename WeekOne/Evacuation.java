import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Evacuation {
    private static FastScanner in;
    private static boolean[] marked;
    private static Edge[] edgeTo;
    public static void main(String[] args) throws IOException {
        in = new FastScanner();

        FlowGraph graph = readGraph();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));
    }

	
    private static int maxFlow(FlowGraph graph, int from, int to) {
        int maxflow = 0;
 		
        while(hasPath(graph, from ,to))
	        {
	        	int pathFlow = Integer.MAX_VALUE;
	        	//Finds the minimum flow through the path
	        	for(int v = to; v != from; v = edgeTo[v].other(v))
	        		pathFlow = Math.min(pathFlow,  edgeTo[v].residualCapacityTo(v));
	        	//Adds flow in the main path
	        	for(int v=to; v!=from; v = edgeTo[v].other(v))
	        		edgeTo[v].addResidualFlowTo(v,pathFlow);
	        	//Counts the total flow
	        	maxflow += pathFlow;
	        }
	    return maxflow;
    }
    
    //Classic Breadth-First-Search to avoid the "slow example"
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
    
    static FlowGraph readGraph() throws IOException {
        int vertex_count = in.nextInt();
        int edge_count = in.nextInt();
        FlowGraph graph = new FlowGraph(vertex_count);

        for (int i = 0; i < edge_count; ++i) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
            graph.addEdge(from, to, capacity);
        }
        return graph;
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

        @SuppressWarnings("unchecked")
		public FlowGraph(int n) {
            this.graph = (ArrayList<Edge>[])new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<Edge>();
        }

        public void addEdge(int from, int to, int capacity) {
            Edge forwardEdge = new Edge(from, to, capacity);
            //Edge backwardEdge = new Edge(to, from, 0);
            graph[from].add(forwardEdge);
            graph[to].add(forwardEdge);
        }
        public List<Edge> adj(int v)
        {
        	return graph[v];
        }

        public int size() {
            return graph.length;
        }
    
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
