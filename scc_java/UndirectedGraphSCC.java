
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

public class TestClass {
    public static void main(String args[] ) throws Exception {
        
        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<List<Integer>> edges = new ArrayList<>();

            String temp  = br.readLine();
            String val[] = temp.split(" ");
            int n = Integer.parseInt(val[0]);
            int m = Integer.parseInt(val[1]);

        while(m-->0) {
             temp  = br.readLine();
             val = temp.split(" ");
             int a = Integer.parseInt(val[0]);
             int b = Integer.parseInt(val[1]);
             List<Integer> connect = new ArrayList<>();
             connect.add(a);
             connect.add(b);
             edges.add(connect);
        }



        UndirectedGraph ug = new UndirectedGraph();
        List<Integer> graph[] = ug.createGraph(n,edges);
         ug.findCutVertex(n,graph);
        ug.findBridges(n,graph);

       

    }

}
class UndirectedGraph {

    public List<Integer>[] createGraph(int n,List<List<Integer>> connections) {
         
        List<Integer> graph[] = new ArrayList[n];
        for(int i=0;i<connections.size();i++) {
            int a= connections.get(i).get(0);
            int b = connections.get(i).get(1);
            
            if(graph[a]==null) graph[a] = new ArrayList<>();
            graph[a].add(b);
            if(graph[b]==null) graph[b] = new ArrayList<>(); 
            graph[b].add(a);
        }

        return graph;
    } 

    int id = 0;
    List<List<Integer>> bridges;
    public void findBridges(int n,List<Integer> graph[]) {
        id = 0;
        bridges = new ArrayList<>();
    
        int rank[] = new int[n]; // rank[i] == -1 means unvisited;
        Arrays.fill(rank,-1);
        int low[] = new int[n];
        computeBridge(graph,rank,low,0,-1);
        System.out.println(bridges.size());
        for(List<Integer> element: bridges) 
        System.out.println(element.get(0)+" "+element.get(1));
        
    }
    
    public void computeBridge(List<Integer> graph[], int rank[], int low[], int index, int parent) {
        rank[index] = id;
        low[index] = id;
        id+=1;
       
        for(int next: graph[index]) {
            if(next==parent) continue;
            if(rank[next]==-1) {
                computeBridge(graph,rank,low,next,index);
                low[index] = Math.min(low[index],low[next]);
            } else low[index] = Math.min(low[index],rank[next]);
            if(low[next]>rank[index]) bridges.add(0,Arrays.asList(index,next));
        }
        
    }

    Set<Integer> cutVertex;
    int timer = 0;
    public void findCutVertex(int n,List<Integer> []graph) {
        timer = 0;
        boolean visited[] = new boolean[n];
        cutVertex = new HashSet<>();
        int rank[] = new int[n];
        int low[] = new int[n];

        computeCutVertex(graph,rank,low,0,-1,visited);
        System.out.println(cutVertex.size());
        for(int vertex: cutVertex) System.out.print(vertex+" ");
        System.out.println();
        
    }

    public void computeCutVertex(List<Integer> graph[], int rank[], int low[],int currentNode, int parent,boolean visited[]) {
        
        visited[currentNode] = true;
        rank[currentNode] = timer;
        low[currentNode] = timer++;
        int count = 0;
        for(int next: graph[currentNode]) {
            if(next==parent) continue;
            if(!visited[next]) {
                count++;
                computeCutVertex(graph,rank,low,next,currentNode,visited);
                low[currentNode] = Math.min(low[currentNode],low[next]);
                if(low[next]>=rank[currentNode]&&parent!=-1) cutVertex.add(currentNode); 
            }
            else {
                low[currentNode] = Math.min(low[currentNode],rank[next]);
            }

           
        }
         if(count>1&&parent==-1) cutVertex.add(currentNode);
    }
        
       
}
