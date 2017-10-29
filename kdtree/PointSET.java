import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET{
	private final TreeSet<Point2D> pointSet;
	public PointSET(){
		pointSet=new TreeSet<>();
	}

	public boolean isEmpty(){
		return pointSet.isEmpty();
	}

	public int size(){
		return pointSet.size();
	}


	public void insert(Point2D p){
		pointSet.add(p);
	}

	public boolean contains(Point2D p){
		return pointSet.contains(p);
	}


	public void draw(){
		for(Point2D p:pointSet)
			p.draw();
	}


	public Iterable<Point2D> range(RectHV rect){
		
		ArrayList<Point2D> points=new ArrayList<>();
		for(Point2D p:pointSet){
			if(rect.contains(p))
				points.add(p);
		}

		return points;
	}

	public Point2D nearest(Point2D p){
	 
	 	Point2D nearest=null;
	 	double minDistance=Double.POSITIVE_INFINITY;

	 	for(Point2D point:pointSet){
	 		if(p.distanceSquaredTo(point) < minDistance){
	 			minDistance=p.distanceSquaredTo(point);
	 			nearest=point;
	 		}
	 	}

	 	return nearest;
	 } 


	public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}