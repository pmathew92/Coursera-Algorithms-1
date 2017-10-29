import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree{
	private  static final boolean VERTICAL=false;
	private static final boolean HORIZONTAL=true;

	private Node root;
	private int size;
    private Point2D nearestPoint;
    private double minDistance;

	private class Node{
		private final Point2D point;
		private final RectHV rect;
		private Node right;
		private Node left;
		private final boolean orientation;

		public Node(Point2D point,RectHV rect,boolean orientation){
			this.point=point;
			this.rect=rect;
			this.orientation=orientation;
			this.right=null;
			this.left=null;
		}
	}

	public KdTree(){
		root=null;
		size=0;
	}

	public boolean isEmpty(){
		return size==0;
	}

	public int size(){
		return size;
	}


	public void insert(Point2D p){
		if(root == null){
			root=new Node(p,new RectHV(0,0,1.0,1.0),VERTICAL);
			size+=1;
			return;
		}

		if(!contains(p)){
			addNewNode(root,p);
			size+=1;
		}
	}

	public boolean contains(Point2D p){
		return containsPoint(root,p);
	} 


	private boolean containsPoint(Node current,Point2D p){
		if (current == null) {
            return false;
        }

        if (current.point.equals(p)) {
            return true;
        }

        double diff=-1;	
        if(current.orientation==VERTICAL){
        	diff=p.x()-current.point.x();
        }else{
        	diff=p.y()-current.point.y();
        }

        if(diff >= 0)
        	return containsPoint(current.right,p);
        else
        	return containsPoint(current.left,p);
	}



	private void addNewNode(Node current,Point2D p){
		if(current.orientation == VERTICAL){

			double diff = p.x() - current.point.x();
            if (diff >= 0) { // right
                if (current.right == null) {
                    current.right=new Node(p,new RectHV(current.point.x(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax()),HORIZONTAL);
                } else {
                    addNewNode(current.right, p);
                }
            }else{
            	if(current.left==null){
            		 current.left=new Node(p,new RectHV(current.rect.xmin(), current.rect.ymin(), current.point.x(), current.rect.ymax()),HORIZONTAL);
            	}else{
            		addNewNode(current.left, p);
            	}
            }

		}else{
			double diff = p.y() - current.point.y();
            if (diff >= 0) { // right
                if (current.right == null) {
                    current.right=new Node(p,new RectHV(current.rect.xmin(), current.point.y(), current.rect.xmax(), current.rect.ymax()),VERTICAL);
                } else {
                    addNewNode(current.right, p);
                }
            }else{
            	if(current.left==null){
            		 current.left=new Node(p,new RectHV(current.rect.xmin(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax()),VERTICAL);
            	}else{
            		addNewNode(current.left, p);
            	}
            }
		}
	}


	public void draw(){
		drawHelper(root);
	}



    private void drawHelper(Node current) {
        if (current.orientation == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(current.rect.xmin(), current.point.y(), current.rect.xmax(), current.point.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(current.point.x(), current.rect.ymin(), current.point.x(), current.rect.ymax());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        current.point.draw();

        // recursively draw the left and right nodes:
        if (current.left != null) {
            drawHelper(current.left);
        }

        if (current.right != null) {
            drawHelper(current.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        ArrayList<Point2D> pointList = new ArrayList<>();
        rangeHelper(root, pointList, rect);
        return pointList;
    }

    private void rangeHelper(Node current,ArrayList<Point2D> pointList,RectHV rect){
        if(current == null)
            return;
        
    	if (rect.contains(current.point)) {
            pointList.add(current.point);
        }

        // recursively search left/bottom
        if (current.left != null && rect.intersects(current.left.rect)) {
            rangeHelper(current.left, pointList, rect);
        }

        // recursively search right/top
        if (current.right != null && rect.intersects(current.right.rect)) {
            rangeHelper(current.right, pointList, rect);
        }
    }



    public Point2D nearest(Point2D p) {
        nearestPoint = null;
        minDistance = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return nearestPoint;
    }

    private void nearest(Node current, Point2D p) {
        if (current == null) return;

        double distance = p.distanceTo(current.point);
        if (distance < minDistance) {
            minDistance = distance;
            nearestPoint = current.point;
        }

        if (current.orientation == VERTICAL) {
            if (p.x() < current.point.x()) {
                nearest(current.left, p);
                if (minDistance >= current.point.x() - p.x())
                    nearest(current.right, p);
            } else {
                nearest(current.right, p);
                if (minDistance >= p.x() - current.point.x())
                    nearest(current.left, p);
            }
        } else {
            if (p.y() < current.point.y()) {
                nearest(current.left, p);
                if (minDistance >= current.point.y() - p.y())
                    nearest(current.right, p);
            } else {
                nearest(current.right, p);
                if (minDistance >= p.y() - current.point.y())
                    nearest(current.left, p);
            }
        }
    }
    

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}