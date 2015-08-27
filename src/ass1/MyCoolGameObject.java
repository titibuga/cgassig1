package ass1;

import javax.media.opengl.GL2;

public class MyCoolGameObject extends GameObject {

	public MyCoolGameObject(GameObject parent) {
		super(parent);
		
		double[] black = new double[]{0,0,0,0};
		//Rocket's body as our child
		PolygonalGameObject body = new PolygonalGameObject(this,
						new double[]{0,0,2,0,2,5,0,5},
						new double[]{0.5,0.5,0.5,0},
						black);
		//Triangles as children of body
		
		//Right triangle
		PolygonalGameObject tr = new PolygonalGameObject(body,
				new double[]{0,0,1,0,0,1},
				new double[]{0.3,0.3,0.3,0},
				black);
		
		tr.translate(2, 0);
		
		//Left triangle
		 tr = new PolygonalGameObject(body,
				new double[]{0,0,1,0,0,1},
				new double[]{0.3,0.3,0.3,0},
				black);
		
		tr.rotate(90);
		
		//Top triangle
		tr = new PolygonalGameObject(body,
				new double[]{0,0,2,0,1,2},
				new double[]{0.9,0.1,0.1,0},
				black);
		
		tr.translate(0, 5);
		
		
		//Scaling and translating everything down to look good on 
		//default camera
		
		this.scale(1.0f/4);
		this.translate(-0.5, -0.9);
		
	}
	
	public MyCoolGameObject()
	{
		this(GameObject.ROOT);
	}
	
	

}
