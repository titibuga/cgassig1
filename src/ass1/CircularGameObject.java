package ass1;

import javax.media.opengl.GL2;

public class CircularGameObject extends GameObject{
	
	private double[] centre;
	private double radius;
	private double[] myFillColour;
	private double[] myLineColour;

	
	
	//Create a CircularGameObject with centre 0,0 and radius 1
	public CircularGameObject(GameObject parent, double[] fillColour,
	                                             double[] lineColour)
	{
		super(parent);
		centre = new double[]{0,0};
		this.radius = 1;
		this.myFillColour = fillColour;
		this.myLineColour = lineColour;
	}

	//Create a CircularGameObject with centre 0,0 and a given radius
	public CircularGameObject(GameObject parent, double radius,double[] fillColour,
	                                                           double[] lineColour)
	{
		super(parent);
		centre = new double[]{0,0};
		this.radius = radius;
		this.myFillColour = fillColour;
		this.myLineColour = lineColour;
	}



	public double[] getCentre() {
		return centre;
	}



	public void setCentre(double[] centre) {
		this.centre = centre;
	}



	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}



	/**
	 * @param radious the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}



	/**
	 * @return the myFillColour
	 */
	public double[] getFillColour() {
		return myFillColour;
	}



	/**
	 * @param myFillColour the myFillColour to set
	 */
	public void setFillColour(double[] myFillColour) {
		this.myFillColour = myFillColour;
	}



	/**
	 * @return the myLineColour
	 */
	public double[] getLineColour() {
		return myLineColour;
	}



	/**
	 * @param myLineColour the myLineColour to set
	 */
	public void setLineColour(double[] myLineColour) {
		this.myLineColour = myLineColour;
	}
	
	
	 @Override
	    public void drawSelf(GL2 gl) {
	    	double radius = this.getRadius();
	    	double[] colour;
	    	
	    	if((colour = this.getFillColour()) != null)
	    	{
		    	gl.glColor4d(colour[0], colour[1], colour[2], colour[3]);
		    	gl.glPolygonMode(
		    	GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); // For safety
		    	gl.glBegin(GL2.GL_POLYGON);
		    	for (int i = 0; i < 32; i++) {
	                  double angle = 2*Math.PI/32 * i;
	                  gl.glVertex2d( radius*Math.cos(angle), radius*Math.sin(angle));
	              }				
				gl.glEnd();
	    	}
	    	if((colour = this.getLineColour()) != null)
	    	{
	    		gl.glColor4d(colour[0], colour[1], colour[2], colour[3]);
		    	gl.glPolygonMode(
		    	GL2.GL_FRONT_AND_BACK, GL2.GL_LINE); // For safety
		    	gl.glBegin(GL2.GL_POLYGON);
		    	for (int i = 0; i < 32; i++) {
	                  double angle = 2*Math.PI/32 * i;
	                  gl.glVertex2d( radius*Math.cos(angle), radius*Math.sin(angle));
	              }				
				gl.glEnd();
	    	}
	    	gl.glPolygonMode(
	    	    	GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); // Back to default
	    
	    }
	
}
