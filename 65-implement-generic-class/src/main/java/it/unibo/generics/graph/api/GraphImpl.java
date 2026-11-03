package it.unibo.generics.graph.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphImpl<N> implements Graph<N> {

    private final Map<N, Set<N>> edges = new HashMap<>(); //map (node, node explorable form origin node)

    @Override 
    public void addNode(N node){
        if (!edges.containsKey(node)){
            edges.put(node, new HashSet<N>());
        }
    }

    @Override 
    public void addEdge(N source, N target){
        if (edges.containsKey(source)){
            edges.get(source).add(target);
        }
    }

    @Override
    public Set<N> nodeSet(){
        return edges.keySet();
    }

    @Override
    public Set<N> linkedNodes(N node){
        return edges.get(node);
    }

    @Override 
    public List<N> getPath(N source, N target){
        List<N> path = new ArrayList<N>();
        List<N> visited = new ArrayList<N>();
        path.add(source);
        visited.add(source);
        return dfs(target, path, visited);
    }
    
    private List<N> dfs (N target, List<N> path, List<N> visited){
        
        N current=path.get(path.size()-1);
        if (current==target){
            return new ArrayList<>(path);
        } else if (edges.get(current).contains(target)){
            path.add(target);
            return dfs(target, path, visited);
        } else{
            for (N n : edges.get(current)) {
                if (!visited.contains(n)){
                    path.add(n);
                    visited.add(n);
                    List<N> result = dfs(target, path, visited);
                    if (result!=null){
                        return result;
                    } else{
                        path.removeLast();
                    }
                }
            }
            return null;
        }
    }
}