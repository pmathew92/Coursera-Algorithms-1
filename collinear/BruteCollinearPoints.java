
import java.util.ArrayList;
import java.util.Arrays;
public class BruteCollinearPoints {
	
	private ArrayList <LineSegment> segmentList = new ArrayList<>();
	public BruteCollinearPoints(Point[] points) {

		if(points == null) {
			throw new NullPointerException();
		}

		for(Point p:points) {
			if(p == null) {
				throw new NullPointerException();
			}
		}

		Point[] pointCopy= Arrays.copyOf(points, points.length); 
		Arrays.sort(pointCopy);
		int length=pointCopy.length;
		
		for(int i=0;i<length-1;i++) {
			if(pointCopy[i].compareTo(pointCopy[i+1]) == 0) {
				throw new IllegalArgumentException();
			}
		}

		for( int i=0;i<length;i++) {
			for (int j=i+1;j<length;j++) {
				for (int k=j+1;k<length;k++) {
					for (int m=k+1;m<length;m++) {
						Point p1=pointCopy[i],p2=pointCopy[j],p3=pointCopy[k],p4=pointCopy[m];
						if(p1.slopeTo(p2)  ==p1.slopeTo(p3) && p1.slopeTo(p2)==p1.slopeTo(p4)) {
							segmentList.add(new LineSegment(p1,p4));
						}
					}
				}
			}
		}

	}


	public int numberOfSegments() {
		return segmentList.size(); 
	}

	public LineSegment[] segments() {
		LineSegment[] segmentArr=new LineSegment[segmentList.size()];
		return segmentList.toArray(segmentArr);
	}	

}