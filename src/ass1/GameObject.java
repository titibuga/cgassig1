package ass1;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;


/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class GameObject {

    // the list of all GameObjects in the scene tree
    public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();
    
    // the root of the scene tree
    public final static GameObject ROOT = new GameObject();
    
    // the links in the scene tree
    private GameObject myParent;
    private List<GameObject> myChildren;

    // the local transformation
    //myRotation should be normalised to the range (-180..180)
    private double myRotation;
    private double myScale;
    private double[] myTranslation;
    
    // is this part of the tree showing?
    private boolean amShowing;

    /**
     * Special private constructor for creating the root node. Do not use otherwise.
     */
    private GameObject() {
        myParent = null;
        myChildren = new ArrayList<GameObject>();

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        amShowing = true;
        
        ALL_OBJECTS.add(this);
    }

    /**
     * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
     *  
     * New objects are created at the same location, orientation and scale as the parent.
     *
     * @param parent
     */
    public GameObject(GameObject parent) {
        myParent = parent;
        myChildren = new ArrayList<GameObject>();

        parent.myChildren.add(this);

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        // initially showing
        amShowing = true;

        ALL_OBJECTS.add(this);
    }

    /**
     * Remove an object and all its children from the scene tree.
     */
    public void destroy() {
        for (GameObject child : myChildren) {
            child.destroy();
        }
        
        myParent.myChildren.remove(this);
        ALL_OBJECTS.remove(this);
    }

    /**
     * Get the parent of this game object
     * 
     * @return
     */
    public GameObject getParent() {
        return myParent;
    }

    /**
     * Get the children of this object
     * 
     * @return
     */
    public List<GameObject> getChildren() {
        return myChildren;
    }

    /**
     * Get the local rotation (in degrees)
     * 
     * @return
     */
    public double getRotation() {
        return myRotation;
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(double rotation) {
        myRotation = MathUtil.normaliseAngle(rotation);
    }

    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(double angle) {
        myRotation += angle;
        myRotation = MathUtil.normaliseAngle(myRotation);
    }

    /**
     * Get the local scale
     * 
     * @return
     */
    public double getScale() {
        return myScale;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     */
    public void setScale(double scale) {
        myScale = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(double factor) {
        myScale *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     */
    public double[] getPosition() {
        double[] t = new double[2];
        t[0] = myTranslation[0];
        t[1] = myTranslation[1];

        return t;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        myTranslation[0] = x;
        myTranslation[1] = y;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(double dx, double dy) {
        myTranslation[0] += dx;
        myTranslation[1] += dy;
    }

    /**
     * Test if the object is visible
     * 
     * @return
     */
    public boolean isShowing() {
        return amShowing;
    }

    /**
     * Set the showing flag to make the object visible (true) or invisible (false).
     * This flag should also apply to all descendents of this object.
     * 
     * @param showing
     */
    public void show(boolean showing) {
        amShowing = showing;
    }

    /**
     * Update the object. This method is called once per frame. 
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param dt The amount of time since the last update (in seconds)
     */
    public void update(double dt) {
        // do nothing
    }

    /**
     * Draw the object (but not any descendants)
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param gl
     */
    public void drawSelf(GL2 gl) {
        // do nothing
    }

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * 
     * 
     * @param gl
     */
    public void draw(GL2 gl) {
        
        // don't draw if it is not showing
        if (!amShowing) {
            return;
        }

        // draw the object (Call drawSelf() to draw the object itself) 
        // and all its children recursively
        
        double[] myPos = this.getPosition();
        
        gl.glPushMatrix();
	        gl.glTranslated(myPos[0],myPos[1],0);
	        gl.glRotated(this.getRotation(),0,0,1);
	        gl.glScaled(this.getScale(), this.getScale(), 1);
	        this.drawSelf(gl);
	        for( GameObject child : this.myChildren )
	        	child.draw(gl);
        gl.glPopMatrix();
       
        
    }
    
    
    private double[][] getModelViewMatrix()
    {
    	double[][] m;
    	double[][] trans, rot, scale;
    	GameObject parent = this.getParent();
    	
    	// If ROOT, load identity
    	if(parent == null)
    	{
    		m = new double[3][3];
    		m[0][0] = m[1][1] = m[2][2] = 1;
    	}
    	else
    	{
    		//Not root, so get parents modelView Matrix
    		m = parent.getModelViewMatrix();    		
    	}
    	
    	
    	trans = MathUtil.translationMatrix(this.getPosition());
    	rot = MathUtil.rotationMatrix(this.getRotation());
    	scale = MathUtil.scaleMatrix(this.getScale());
    	
    	
    	// Multiply m*trans*rot*scale =
    	// = (m*trans)*(rot*scale)
    	m = MathUtil.multiply(m, trans);
    	rot = MathUtil.multiply(rot, scale);
    	m = MathUtil.multiply(m, rot);
    	
    	
    	
    	return m;
    }

    /**
     * Compute the object's position in world coordinates
     * 
     * 
     * 
     * @return a point in world coordinats in [x,y] form
     */
    public double[] getGlobalPosition() {
        double[] p = new double[2];
        double[][] modelView = this.getModelViewMatrix();
        
        //We just need to take the last column of the matrix
        
        p[0] = modelView[0][2];
        p[1] = modelView[1][2];
        
        
        return p; 
    }

    /**
     * Compute the object's rotation in the global coordinate frame
     * 
     * TODO: Write this method
     * 
     * @return the global rotation of the object (in degrees) and 
     * normalized to the range (-180, 180) degrees. 
     */
    public double getGlobalRotation() {
    	double alpha; 
    	GameObject parent;
    	
    	parent = this.getParent();
    	
    	if(parent == null) return this.getRotation();
    	else
    	{
    		alpha = parent.getGlobalRotation() + this.getRotation();
    		return MathUtil.normaliseAngle(alpha);
    	}
    	
    }

    /**
     * Compute the object's scale in global terms
     * 
     * 
     * 
     * @return the global scale of the object 
     */
    public double getGlobalScale() {
    	GameObject parent = this.getParent();
    	
    	if(parent == null)
    		return this.getScale();
    	else
    		return parent.getGlobalScale()*this.getScale();
    	
    }

    /**
     * Change the parent of a game object.
     * 
     * TODO: add code so that the object does not change its global position, rotation or scale
     * when it is reparented. You may need to add code before and/or after 
     * the fragment of code that has been provided - depending on your approach
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
    	
    	double[] globalPos = this.getGlobalPosition();
    	double globalRot = this.getGlobalRotation();
    	double globalScale = this.getGlobalScale();
    	
    	double parentScale = parent.getGlobalScale();
    	double parentRot = parent.getGlobalRotation();
    	double[] parentTrans = parent.getGlobalPosition();
    	
    	// Let's invert the parent global tranforms
    	// So we can tranform or global transforms
    	// to local transforms of the parent
    	
    	this.setRotation(globalRot - parentRot);
    	this.setScale(globalScale/parentScale);

    	double[][] rotInv = MathUtil.rotationMatrix(-parentRot);
    	double[][] scaleInv = MathUtil.scaleMatrix(1/parentScale);
    	double[] parentTransInv = new double[]{-parentTrans[0], -parentTrans[1]};
    	double[][] transInv = MathUtil.translationMatrix(parentTransInv);
    	
    	// m Will be the inverse modelView matrix from the
    	// parent
    	double[][] m = MathUtil.multiply(scaleInv, rotInv);
    	m = MathUtil.multiply(m, transInv);
    	// Need to change notation of pos (add 1 to the final line)
    	double[] tempGlobalPos = new double[]{globalPos[0],
    										globalPos[1],
    										1};
    	double[] newPos = MathUtil.multiply(m, tempGlobalPos);
    	this.setPosition(newPos[0], newPos[1]);
    	
    	
    	
        
        myParent.myChildren.remove(this);
        myParent = parent;
        myParent.myChildren.add(this);
        
    }
    

}
