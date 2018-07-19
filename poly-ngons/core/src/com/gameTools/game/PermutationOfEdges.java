package com.gameTools.game;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;
import com.polygonParts.game.Edge;

public class PermutationOfEdges {
	private HashMap<String, Integer> intMap = new HashMap<String, Integer>();
	
	public PermutationOfEdges(){
		intMap.put("a", 1);
		intMap.put("b", 2);
		intMap.put("c", 3);
		intMap.put("d", 4);
		intMap.put("e", 5);
		intMap.put("f", 6);
		intMap.put("g", 7);
		intMap.put("h", 8);
		intMap.put("i", 9);
		intMap.put("j", 10);
		intMap.put("k", 11);
		intMap.put("l", 12);
		intMap.put("m", 13);
		intMap.put("n", 14);
		intMap.put("o", 15);
		intMap.put("p", 16);
		intMap.put("q", 17);
		intMap.put("r", 18);
		intMap.put("s", 19);
		intMap.put("t", 20);
		intMap.put("u", 21);
		intMap.put("v", 22);
		intMap.put("w", 23);
		intMap.put("x", 24);
		intMap.put("y", 25);
		intMap.put("z", 26);
	}
	
	public static Array<String> permutation(String str) { 
	    return permutation("", str); 
	}

	private static Array<String> permutation(String prefix, String str) {
		Array<String> permutedString = new Array<String>();
		
	    int n = str.length();
	    if (n == 0) permutedString.add(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	    
	    return permutedString;
	}
	
	public Array<Array<Edge>> permuteObjects(Array<Edge> edges){
		Array<Array<Edge>> permutedEdges = new Array<Array<Edge>>();
		
		String id = "";
		for (Edge edge : edges){
			for (String str : intMap.keySet()){
				if (edges.indexOf(edge, true) + 1 == intMap.get(str)){
					id += str;
				}
			}
		}
		
		Array<String> permutedID = permutation(id);
		for (String str : permutedID){
			Array<Edge> arrangedEdges = new Array<Edge>();
			arrangedEdges.add(edges.get(intMap.get(str) - 1));
		}
		
		return permutedEdges;
	}
}
