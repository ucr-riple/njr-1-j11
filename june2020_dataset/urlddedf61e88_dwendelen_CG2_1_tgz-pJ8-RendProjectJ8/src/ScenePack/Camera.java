
package ScenePack;

import Vectormath.*;

/** Class Camera defines the camera needed to generate an image. Camera is
 * not only the position of the camera in the 3d scene, but also specifiactions
 * for the image plane.
 */
public class Camera extends Object {
  public static final int PERSPECTIVE = 1;
  public static final int ORTHOGRAPHIC = 2;
  public static final int FISHEYE = 3;

  public FrameOfReference cameraFrame;

  private int xres = 100;
  private int yres = 100;

  private Vector3 eyePoint;
  private Vector3 lookDirection; //normalized
  private Vector3 lookAtPoint;
  private Vector3 upDirection;

  private double distanceToScreen;
  private Vector3 lowerLeftCorner; //vector relative to eyepoint
  private Vector3 upperRightCorner; // vector relative to eyepoint

  private double imageWidth;
  private double imageHeight;
  private double leftWidth, rightWidth, upperHeight, lowerHeight, maxWidthHeight;

  private Vector3 widthOffset;
  private Vector3 heightOffset;

  private int type;

  private TransformationMatrix projectionMatrix;



  /** Creates new Camera
   * @param eyePoint Location of the eyepoint
   * @param lookAtPoint point to which the camera is looking (!= direction in which the camera is looking)
   * @param upDirection direction indicating where 'up' is
   */
  public Camera(Vector3 eyePoint,Vector3 lookAtPoint,Vector3 upDirection) {
    cameraFrame = new FrameOfReference();
    OrthoNormalBasis onb = new OrthoNormalBasis();

    this.eyePoint = eyePoint.makeCopy();
    this.lookAtPoint = lookAtPoint.makeCopy();
    this.lookDirection = Vector3.normalize(Vector3.subtract(lookAtPoint,eyePoint));
    this.upDirection = upDirection.makeCopy();
    // now we have to set the frame correctly
    cameraFrame.setOrigin(eyePoint);
    onb.constructWVFrom(lookDirection.scale(-1.0),upDirection);
    cameraFrame.setOrthoNormalBasis(onb);

    this.setScreen(Math.PI/2.0, Math.PI/2.0);
    this.setResolution(100, 100);

    this.type = PERSPECTIVE;

    this.setProjectionMatrix();


  }

  /** Set the resolution of the image to be generated
   * @param xres Horizontal resolution
   * @param yres Vertical resolution
   */
  public void setResolution(int xres,int yres) {
    this.xres = xres;
    this.yres = yres;

    this.widthOffset = this.cameraFrame.onb.u.scale(this.imageWidth/(double)xres);
    this.heightOffset = this.cameraFrame.onb.v.scale(this.imageHeight/(double)yres);

    this.setProjectionMatrix();

  }

  /** Set the size of the viewing window.
   * If the viewing window is square and centered around the viewing axis, all
   * numbers should have the same value.
   * @param distance distance between the eypoint and the eyepoint of the camera
   * @param leftWidth width of the lefthand side of the viewing window. The width is measured from the point on the viewing window where the centre-axis of the camera intersects with the viewing plane.
   * @param rightWidth width of the righthand side of the viewing window
   * @param upperHeight height of the upper side of the viewing window
   * @param lowerHeight height of the lower side of the viewing window
   */
  public void setScreen(double distance,double leftWidth,double rightWidth,double upperHeight,double lowerHeight) {
    Vector3 tmp1, tmp2, tmp3;

    this.distanceToScreen = distance;

    tmp1 = this.cameraFrame.onb.w.scale(-distance); // point on screenplane we're looking at
    tmp2 = this.cameraFrame.onb.u.scale(rightWidth);
    tmp3 = this.cameraFrame.onb.v.scale(upperHeight);
    this.upperRightCorner = Vector3.add(tmp1,Vector3.add(tmp2,tmp3));


    tmp2 = this.cameraFrame.onb.u.scale(-leftWidth);
    tmp3 = this.cameraFrame.onb.v.scale(-lowerHeight);
    this.lowerLeftCorner = Vector3.add(tmp1,Vector3.add(tmp2,tmp3));

    this.imageWidth = rightWidth + leftWidth;
    this.imageHeight = upperHeight + lowerHeight;

    this.rightWidth = rightWidth;
    this.leftWidth = leftWidth;
    this.upperHeight = upperHeight;
    this.lowerHeight = lowerHeight;

    this.maxWidthHeight = Math.max(Math.max(rightWidth,leftWidth),Math.max(upperHeight,lowerHeight));

    // because image size is changing, we must set the Offset vectors again
    this.setResolution(this.xres,this.yres);

    this.setProjectionMatrix();
  }

  /** Set the viewing angles of the viewing window, as seen from the eyepoint.
   * @param horizontalAngle total horizontal viewing angle
   * @param verticalAngle total vertical viewing angle
   */
  public void setScreen(double horizontalAngle,double verticalAngle) {
    this.setScreen(1.0, Math.tan(horizontalAngle/2.0), Math.tan(horizontalAngle/2.0), Math.tan(verticalAngle/2.0), Math.tan(verticalAngle/2.0));
  }


  public void setProjectionType(int type) {
    if (type == PERSPECTIVE) this.type = PERSPECTIVE;
    else if (type == ORTHOGRAPHIC) this.type = ORTHOGRAPHIC;
    else if (type == FISHEYE) this.type = FISHEYE;
    else this.type = PERSPECTIVE;
  }

  public int getProjectionType() {
    return(type);
  }

  // only works for perspective cameras at this time
  private void setProjectionMatrix() {
    TransformationMatrix m;

    projectionMatrix = TransformationMatrix.identity();
    // first the translation from camera to origin and rotate
    m = TransformationMatrix.coordinateTransformOXYZtoPUVW(this.cameraFrame);
    projectionMatrix = m.multiply(projectionMatrix);
    // now the perspective transform
    m = TransformationMatrix.perspective(this.distanceToScreen);
    projectionMatrix = m.multiply(projectionMatrix);
    // now the translation in the image plane
    m = TransformationMatrix.translation(new Vector3(this.leftWidth,this.lowerHeight,0.0));
    projectionMatrix = m.multiply(projectionMatrix);
    // now scaling to resolution coordinates
    m = TransformationMatrix.scale(new Vector3((double)this.xres/this.imageWidth,(double)this.yres/this.imageHeight,1.0));
    projectionMatrix = m.multiply(projectionMatrix);
  }

  /** This function takes two image continuous coordinates, bounded by
   * [0,xres), [0,yres). (0,0) is the lowerleftcorner of the screen,
   * and (xres,yres) is the upperrightcorner of the screen.
   * @param x Continuous image coordinate, belongs to [0,xres)
   * @param y Continuous image coordinate, belongs to [0,yres)
   * @return ray which starts at the eyepoint and direction through the requested image location (new object)
   */
  public Ray rayAtImageLocation(double x,double y) {
    Ray ray = new Ray();
    Vector3 direction, tmp2, tmp3, VectorInScreen, rayOrigin, rayDir;
    Vector3 q;
    double radius2;

    tmp2 = this.widthOffset.scale(x);
    tmp3 = this.heightOffset.scale(y);
    VectorInScreen = Vector3.add(tmp2,tmp3); //this is a vector from the lowerleftcorner of the screen

    if (this.type == PERSPECTIVE) {
      direction = Vector3.add(this.lowerLeftCorner,VectorInScreen);
      ray = new Ray(this.eyePoint, direction);
    }
    else if (this.type == ORTHOGRAPHIC) {
      rayOrigin = Vector3.add(this.eyePoint, this.lowerLeftCorner , Vector3.subtract(VectorInScreen,this.lookDirection.scale(distanceToScreen)));
      ray = new Ray(rayOrigin,this.lookDirection);
    }
    else if (this.type == FISHEYE) {
      q = Vector3.linearCombination(this.lowerLeftCorner , 1.0,VectorInScreen,-this.distanceToScreen, this.lookDirection);
      radius2 = q.norm2();
      if (this.maxWidthHeight*this.maxWidthHeight-radius2 >= 0.0) {
        rayDir = Vector3.linearCombination(q, Math.sqrt(this.maxWidthHeight*this.maxWidthHeight-radius2), this.lookDirection);
        ray = new Ray(this.eyePoint, rayDir);
      } else {
        ray = null;
      }
    }
    else System.out.println("Error in Camera.rayAtImageLocation");

    return(ray);
  }

  /** generates a ray from the eyepoint through the center of the requested pixel.
   * @param i horizontal pixel coordinate, [0,xres-1]
   * @param j vertical pixel coordinate, [0,yres-1]
   * @return generated ray
   */
  public Ray rayAtPixelCenter(int i,int j) {
    Ray ray;
    ray = this.rayAtImageLocation((double)i+0.5,(double)j+0.5);
    return(ray);
  }

  /** Generates a ray at the requested pixel location, but with a certain offset within the pixel.
   * This is useful for generating a pattern of rays within a pixel.
   * @param i horizontal pixel coordinate [0,xres-1]
   * @param j vertical pixel coordinate [0,yres-1]
   * @param x horizontal fractional distance within the pixel
   * @param y vertical fractional distance within the pixel
   * @return Ray (new object)
   */
  public Ray rayAtPixelLocation(int i,int j,double x,double y) {
    Ray ray = new Ray();
    ray = this.rayAtImageLocation((double)i+x,(double)j+y);
    return(ray);
  }

  public Vector3 project(Vector3 p) {
    Vector3 tmp;
    tmp = this.projectionMatrix.transformAsLocation(p);
    return(tmp);
  }

  public Vector4 project(Vector4 p) {
    Vector4  tmp;
    tmp = this.projectionMatrix.transformAsLocation(p);
    return(tmp);
  }

  public int getXres() {
    return(this.xres);
  }

  public int getYres() {
    return(this.yres);
  }

  public Vector3 getEyePoint() {
    return(this.eyePoint.makeCopy());
  }

  public Vector3 getLookDirection() {
    return(this.lookDirection.makeCopy());
  }

  public Vector3 getLowerLeftCorner() {
    return(this.lowerLeftCorner.makeCopy());
  }

  public Vector3 getWidthOffset() {
    return(this.widthOffset.makeCopy());
  }

  public Vector3 getHeightOffset() {
    return(this.heightOffset.makeCopy());
  }

  public void printCamera() {
    System.out.println("============================================");
    System.out.println("Camera System");
    System.out.print("Eyepoint ");eyePoint.printlnVector3();
    System.out.print("LookAtPoint ");lookAtPoint.printlnVector3();
    System.out.print("Lookdirection ");lookDirection.printlnVector3();
    System.out.print("Upvector ");upDirection.printlnVector3();
    System.out.print("Frame origin ");cameraFrame.origin.printlnVector3();
    System.out.print("Frame onb ");cameraFrame.onb.printOrthoNormalBasis();
    System.out.print("Lowerleft ");lowerLeftCorner.printlnVector3();
    System.out.print("Upperright ");upperRightCorner.printlnVector3();
    System.out.println("Xres = " + xres + " Yres = " + yres);
    System.out.println("Imagewidth= " + imageWidth + " Imageheight = " + imageHeight);
    System.out.print("Width Offset ");widthOffset.printlnVector3();
    System.out.print("Height Offset ");heightOffset.printlnVector3();
    System.out.println("Projection type " + type);
    System.out.println("============================================");

  }



  // rotate current camera 90 degrees to the right around the center
  // of attention, in the plane of xz camera frame
  public Camera rotateRight() {
    Camera newCamera;

    newCamera = new Camera(Vector3.linearCombination(this.lookAtPoint,Vector3.subtract(this.eyePoint,this.lookAtPoint).norm(),this.cameraFrame.onb.u) , this.lookAtPoint, this.cameraFrame.onb.v);
    newCamera.setResolution(this.getXres(), this.getYres());
    newCamera.setScreen(this.distanceToScreen, this.leftWidth, this.rightWidth, this.lowerHeight, this.upperHeight);
    newCamera.setProjectionType(this.getProjectionType());
    return(newCamera);
  }


  public Camera rotateUp() {
    Camera newCamera;

    newCamera = new Camera(Vector3.linearCombination(this.lookAtPoint,Vector3.subtract(this.eyePoint,this.lookAtPoint).norm(),this.cameraFrame.onb.v) , this.lookAtPoint, this.cameraFrame.onb.w.scale(-1.0));
    newCamera.setResolution(this.getXres(), this.getYres());
    newCamera.setScreen(this.distanceToScreen, this.leftWidth, this.rightWidth, this.lowerHeight, this.upperHeight);
    newCamera.setProjectionType(this.getProjectionType());
    return(newCamera);
  }
  
  public double getSurface() {
    
    return( (this.leftWidth+this.rightWidth)*(this.upperHeight+this.lowerHeight));
    
  }


  public static void main(String[] args) {
    Vector3 a,b;
    Camera cam,cam2,cam3;

    cam = new Camera(new Vector3(5.0, 0.0, 3.0),new Vector3(0.0,0.0,-2.0),new Vector3(0.0,1.0,0.0));
    cam.setResolution(101,101);
    cam.printCamera();

    cam2 = cam.rotateRight();
    cam2.printCamera();

    cam3 =  cam.rotateUp();
    cam3.printCamera();

  }


}