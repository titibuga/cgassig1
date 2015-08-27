package ass1;

import javax.media.opengl.GL2;

public class LineGameObject extends GameObject {
	
	private double[][] points;
	private double[] lineColour;
	
	//Create a LineGameObject from (0,0) to (1,0)
	public LineGameObject(GameObject parent, double[] lineColour)
	{
		super(parent);
		this.setLineColour(lineColour);
		this.setPoints(new double[][]{{0,0},
								{1,0}});
	}

	//Create a LineGameObject from (x1,y1) to (x2,y2)
	public LineGameObject(GameObject parent,  double x1, double y1,
	                                          double x2, double y2,
	                                          double[] lineColour)
	{
		super(parent);
		this.setLineColour(lineColour);
		this.setPoints(new double[][]{{x1,y1},
								{x2,y2}});
	}

	/**
	 * @return the points
	 */
	public double[][] getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(double[][] points) {
		this.points = points;
	}

	/**
	 * @return the lineColour
	 */
	public double[] getLineColour() {
		return lineColour;
	}

	/**
	 * @param lineColour the lineColour to set
	 */
	public void setLineColour(double[] lineColour) {
		this.lineColour = lineColour;
	}
	
	public void drawSelf(GL2 gl) {
    	
    	double[] colour = this.getLineColour();
    	double[][] points = this.getPoints();
    	double[] p1 = points[0], p2 = points[1];
    	
    	if(colour != null)
    	{
    		gl.glColor4d(colour[0], colour[1], colour[2], colour[3]);
	    	gl.glBegin(GL2.GL_LINES);
	    		gl.glVertex2d(p1[0], p1[1]);
	    		gl.glVertex2d(p2[0], p2[1]);
			gl.glEnd();
    	}
    	
    
    }


}
