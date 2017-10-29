import java.util.ArrayList;
import java.util.Arrays;
public class FastCollinearPoints {
	private ArrayList <LineSegment> segmentList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
    	if(points == null) {
			throw new NullPointerException();
		}

		for(Point p:points) {
			if(p == null) {
				throw new NullPointerException();
			}
		}

		Point[] pointCopy=Arrays.copyOf(points, points.length);
		Arrays.sort(pointCopy);
		int length=pointCopy.length;
		

		for(int i=0;i<length-1;i++) {
			if(pointCopy[i].compareTo(pointCopy[i+1]) == 0) {
				throw new IllegalArgumentException();
			}
		}


		for(int i=0;i<length-2;i++) {
			Point origin=pointCopy[i];
			ArrayList<Point> pts = new ArrayList<>();

            for (int j = i + 1; j < pointCopy.length; j++) {
                pts.add(pointCopy[j]);
            }

            Point[] ptsArr = pts.toArray(new Point[pts.size()]);
			Arrays.sort(ptsArr,origin.slopeOrder());

			for(int j=0;j<ptsArr.length-1;j++) {
				int counter=1;
				Point endPoint=null;
				double slope1=origin.slopeTo(ptsArr[j]);

				for(int k=j+1;k<ptsArr.length;k++) {
					if(slope1==origin.slopeTo(ptsArr[k])) {
						counter++;
						if(counter >= 3) {
							endPoint=ptsArr[k];
							j=k;
						}
					} else {
						break;
					}

				}

				if(endPoint!=null){
					segmentList.add(new LineSegment(origin,endPoint));
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
