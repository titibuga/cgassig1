package ass1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;


/**
 * The camera is a GameObject that can be moved, rotated and scaled like any other.
 * 
 * 
 *       The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class Camera extends GameObject {

    private float[] myBackground;

    public Camera(GameObject parent) {
        super(parent);

        myBackground = new float[4];
    }

    public Camera() {
        this(GameObject.ROOT);
    }
    
    public float[] getBackground() {
        return myBackground;
    }

    public void setBackground(float[] background) {
        myBackground = background;
    }

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
   
    
    public void setView(GL2 gl) {
    	
    	float[] bg = this.getBackground();
    	double scl = this.getGlobalScale();
    	double rot = this.getGlobalRotation();
    	double[] pos = this.getGlobalPosition();
        
        //  clear the view to the background colour
    	gl.glClearColor(bg[0], bg[1], bg[2], bg[3]); // Background colour
    	gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        //  set the view matrix to account for the camera's position  
    	gl.glMatrixMode(GL2.GL_MODELVIEW);
    	gl.glLoadIdentity();
    	// apply the view transform
    	gl.glScaled(1.0f/scl, 1.0f/scl, 1.0f);
    	gl.glRotated(-rot, 0, 0, 1);
    	gl.glTranslated(-pos[0], -pos[1], 0);
    }

    public void reshape(GL2 gl, int x, int y, int width, int height) {
        
        // match the projection aspect ratio to the viewport
        // to avoid stretching
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        double top, bottom, left, right;
        
        if (width > height) {
            double aspect = (1.0 * width) / height;
            top = 1.0;
            bottom = -1.0;
            left = -aspect;
            right = aspect;            
        }
        else {
            double aspect = (1.0 * height) / width;
            top = aspect;
            bottom = -aspect;
            left = -1;
            right = 1;                        
        }        
        GLU myGLU = new GLU();
        // coordinate system (left, right, bottom, top)
        myGLU.gluOrtho2D(left, right, bottom, top);                
    }
}
