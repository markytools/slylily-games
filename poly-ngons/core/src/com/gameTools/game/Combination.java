package com.gameTools.game;

import com.badlogic.gdx.utils.Array;
import com.polygonParts.game.Edge;

public class Combination {
    private Array<Edge> edgeComb = new Array<Edge>();
    private Array<Array<Edge>> setOfEdges;
    private Array<Edge> edges;
    private int combLim;
    
    public Combination(){
    	
    }
    
    public Combination(Array<Edge> edges, int combNum){
    	this.edges = edges;
    	combLim = combNum;
    	setOfEdges = new Array<Array<Edge>>();
    }
    
    public Array<Array<Edge>> getCombination(Array<Edge> edges, int combNum){
    	this.edges = edges;
    	combLim = combNum;
    	setOfEdges = new Array<Array<Edge>>();
    	combine();
    	return setOfEdges;
    }

    public void combine() { combine( 0 ); }
    private void combine(int start){
        for( int i = start; i < edges.size; ++i ){
            
            edgeComb.add(edges.get(i));
            if (edgeComb.size == combLim) setOfEdges.add(new Array<Edge>(edgeComb));
            if ( i <= edges.size) 
            combine( i + 1);
            edgeComb.removeIndex(edgeComb.size - 1);
        }
    }
    
    public Array<Array<Edge>> getSetOfEdges(){ return setOfEdges; }
}
