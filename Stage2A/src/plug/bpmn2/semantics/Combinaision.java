package plug.bpmn2.semantics;

import java.util.ArrayList;
import java.util.List;

public class Combinaision {
	
	public static int factoriel(int n) {
		if (n==0) 
			return 1;
		else 
			return n*factoriel(n-1);
		
	}
	
	public static List<List<Integer>> combi(List<Integer> list, int k){
		List<List<Integer>> p = new ArrayList<List<Integer>>();
		int i = 0;
		int imax=(int) (Math.pow(2,list.size())-1);
		
		while(i<=imax) {
			List<Integer> s = new ArrayList<Integer>();
			int j = 0;
			int jmax=list.size()-1;
			while(j<=jmax) {
				if (((i>>j) & 1)==1) {
					s.add(list.get(j));
				}
				j=j+1;
			}
			if (s.size()==k) {
				p.add(s);
			}
			
			i=i+1;
		}
		return p;
	}

	public static void main (String[] args) {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(list);
		System.out.println(factoriel(10));
		System.out.println(combi(list,2));
		
	}
}
