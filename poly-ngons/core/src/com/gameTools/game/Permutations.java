package com.gameTools.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.utils.Array;

public class Permutations {

	public Permutations(){
		
	}

	public static Array<Array<Object>> getPermutedObjects(Array<Object> objects){
		final Array<Object> arrangement = new Array<Object>();
		final Array<Array<Object>> permutedObjects = new Array<Array<Object>>();
		
		Integer[] objectNums = new Integer[objects.size];
		for (int s = 1; s <= objects.size; s++){
			objectNums[s - 1] = s;
		}
		
		ArrayList<Integer[]> int_perms = permutations(objectNums);
		for ( Integer[] p : int_perms ) {
			for (Integer pSub : p){
				arrangement.add(objects.get(pSub - 1));
			}
			permutedObjects.add(arrangement);
		}

		return permutedObjects;
	}

	static <E> ArrayList<E[]> permutations(E[] arr) {
		final ArrayList<E[]> resultList = new ArrayList<E[]>();
		final int l = arr.length;
		if ( l == 0 ) return resultList;
		if ( l == 1 )
		{
			resultList.add( arr );
			return resultList;
		}

		E[] subClone = Arrays.copyOf( arr, l - 1);
		System.arraycopy( arr, 1, subClone, 0, l - 1 );

		for ( int i = 0; i < l; ++i ){
			E e = arr[i];
			if ( i > 0 ) subClone[i-1] = arr[0];
			final ArrayList<E[]> subPermutations = permutations( subClone );
			for ( E[] sc : subPermutations )
			{
				E[] clone = Arrays.copyOf( arr, l );
				clone[0] = e;
				System.arraycopy( sc, 0, clone, 1, l - 1 );
				resultList.add( clone );
			}
			if ( i > 0 ) subClone[i-1] = e;
		}
		return resultList;
	}
}
